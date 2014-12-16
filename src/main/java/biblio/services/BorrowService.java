
package biblio.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biblio.models.Book;
import biblio.models.Borrow;
import biblio.models.User;

/**
 * The Class BorrowService.
 */
@Stateless
public class BorrowService {

  /** The logger. */
  private final Logger logger = LoggerFactory.getLogger(BorrowService.class);

  /** The user service. */
  @Inject
  UserService userService;

  /** The book service. */
  @Inject
  BookService bookService;

  /** The entity managerm. */
  @Inject
  EntityManager entityManager;

  /** The borrowRecord service. */
  
  private List<Borrow> borrowRecord=new ArrayList<Borrow>();
  /**
   * Gets the borrow number of user
   * 
   * @param bookId book id
   * @return borrowNb
   */
  public int getBorwNbByUser(final long userId) {
	  List<Borrow> borrows=getBorrowByUserId(userId);
    if (borrows == null || borrows.isEmpty()) {
      return 0;
    }

    int sum = 0;
    for (final Borrow borrow : borrows) {
      sum += borrow.getBorrowNb();
    }
	  return sum;
  }

  /**
   * Gets the borrow number of book
   * 
   * @param bookId book id
   * @return borrowNb
   */
  public int getBorwNbByBook(final long bookId) {
	  List<Borrow> borrows=getBorrowByBookId(bookId);
    if (borrows == null || borrows.isEmpty()) {
      return 0;
    }

    int sum = 0;
    for (final Borrow borrow : borrows) {
      sum += borrow.getBorrowNb();
    }
	  return sum;
  }
  
  /**
   * Gets the number of remaining book
   * 
   * @param bookId book id
   * @return borrowNb
   */
  public int getBookRestNb(final long bookId) {
	  int sumBorrow=getBorwNbByBook(bookId);
	  final Book book = bookService.getBookById(bookId);
	    if (sumBorrow<=book.getBookNb()) 
	    	return book.getBookNb()-sumBorrow;
	    else
	    	return -1;
  }
  
  /**
   * Gets the bool of a book Isborrow.
   * 
   * @param bookId book id
   * @return bool
   */
  public boolean checkBorrow(final long bookId) {
	int sumBorrow=getBorwNbByBook(bookId);
	final Book book = bookService.getBookById(bookId);
    if (sumBorrow<=book.getBookNb()) 
    	return Boolean.TRUE;
    else
    	return Boolean.FALSE;

  }

  /**
   * Gets all borrow.
   * 
   * @return the list borrow
   */
  public List<Borrow> getAllBorrows() {
	    return entityManager.createQuery("select b from Borrow b", Borrow.class)
	        .getResultList();
	  }
  
  
  /**
   * Gets the borrow by book id.
   * 
   * @param bookId the book id
   * @return the borrow by book id
   */
  public List<Borrow> getBorrowByBookId(final long bookId) {
    return entityManager.createQuery("select b from Borrow b where b.bookId = :bookId", Borrow.class)
        .setParameter("bookId", bookId).getResultList();
  }

  /**
   * Gets the borrow by user id.
   * 
   * @param userId the user id
   * @return the borrow by user id
   */
  public List<Borrow> getBorrowByUserId(final long userId) {
    return entityManager.createQuery("select b from Borrow b where b.userId = :userId", Borrow.class)
        .setParameter("userId", userId).getResultList();
  }

  /**
   * Gets the borrow by user id and book id.
   * 
   * @param userId the user id
   * @param bookId the book id
   * @return the borrow by user id and book id
   */
  public Borrow getBorrowByUserIdAndBookId(final long userId, final long bookId) {
    try {
      return entityManager
          .createQuery("select b from Borrow b where b.userId = :userId and b.bookId = :bookId", Borrow.class)
          .setParameter("userId", userId).setParameter("bookId", bookId).getSingleResult();
    } catch (final PersistenceException ex) {
      return null;
    }
  }

  /**
   * Gets the all students borrows by book id.
   * 
   * @param bookId the book id
   * @return the student borrows by book id
   */
  public List<Borrow> getStudentBorrowsByBookId(final long bookId) {
    final List<Borrow> borrows = new ArrayList<Borrow>();
    for (final Borrow borrow : borrowRecord) {
    	if(borrow.getBook().getId()==bookId)
    		borrows.add(borrow);
        
      }
    return borrows;
  }
  
  /**
   * Gets the book by book name .
   * 
   * @param bookName the book name
   * @return the book by book name
   */
  public Book getBookByName(String bookName){
	  return bookService.getBookByName(bookName);
  }
  
  /**
   * Gets the user by user name .
   * 
   * @param bookUser the user name
   * @return the user by user name
   */
  public User getUserByName(String userName){
	  return userService.getUserByLogin(userName);
  }
  
  /**
   * Gets the current date .
   * 
   * @return current date
   */
  public Date getCurDate(){
		 return new Date();
	 }
  /**
   * another method
   * Gets the current date .
   * 
   * @return current date
   */
  public Date getCurDate2(){
	  	Calendar cal=Calendar.getInstance(); 
		 int year=cal.get(Calendar.YEAR);
		 int month=cal.get(Calendar.MONTH)+1;
		 int day=cal.get(Calendar.DAY_OF_MONTH);
		   
		 String time="";
		 time+=day+"/";
		 time+=month+"/";
		 time+=year;
		 
		 SimpleDateFormat sdf =   new SimpleDateFormat( "dd/MM/yyyy" ); 
		 java.util.Date timeDate = null;
		 try {
			timeDate = sdf.parse(time);
		 } catch (java.text.ParseException e) {
			
			e.printStackTrace();
		 }
		 System.out.println(timeDate);
		 //java.sql.Date dateTime = new java.sql.Date(timeDate.getTime());
		 return timeDate;
	 }
  
  /**
   * Gets the return book date .
   * 
   * @return return date
   */
	 public Date getRetnDate(){
		 Calendar cal=Calendar.getInstance(); 
		 cal.add(Calendar.MONTH, 1);;
		 Date RetnDate=cal.getTime();
		 return RetnDate;
	 }
	 
  /**
   * Save borrow.
   * 
   * @param userId the user id
   * @param bookId the book id
   * @param  the borrowNb
   */
  public void saveBorrow(final long userId, final long bookId, final int borrowNb, final Date dateDebut, final Date dateFin) {
    
    Borrow borrow = getBorrowByUserIdAndBookId(userId, bookId);
    if ( borrowNb<0) {
      if (borrow != null) {
        entityManager.remove(borrow);
      }
    } else {
      if (borrow != null) {
        borrow.setBorrowNb(borrowNb);
    	borrow.setDateDebut(dateDebut);
        borrow.setDateFin(dateFin);
        borrowRecord.add(borrow);
        entityManager.persist(borrow);
        
      } else {
        borrow = new Borrow();
        borrow.setUser(userService.getUserById(userId));
        borrow.setBook(bookService.getBookById(bookId));
        borrow.setBorrowNb(borrowNb);
        
        borrow.setDateDebut(dateDebut);
        borrow.setDateFin(dateFin);
        borrowRecord.add(borrow);
        entityManager.persist(borrow);
        
      }
    }
  }
  
  /**
   * Delete borrow.
   * 
   * @param borrow the borrow
   */
  public void deleteBorrow(final Borrow borrow) {
	    final Borrow managedBorrow = getBorrowByUserIdAndBookId( borrow.getUser().getId(), borrow.getBook().getId());
	    if (managedBorrow  != null) {
	    	entityManager.createQuery("delete from Borrow b where b.userId = :userId and b.bookId= :bookId")
	       .setParameter("userId", managedBorrow.getUser().getId()).setParameter("bookId", managedBorrow.getBook().getId()).executeUpdate();
	      entityManager.remove(managedBorrow);
	    } else {
	      logger.warn("Can not delete unknown borrow {}", borrow);
	    }
 }
  
}
