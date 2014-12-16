
package biblio.utils;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import biblio.models.Book;
import biblio.models.Borrow;
import biblio.models.Role;
import biblio.models.User;
import biblio.services.BookService;
import biblio.services.BorrowService;
import biblio.services.UserService;

/**
 * The Class PopulateDatabase.
 */
@Startup
@Singleton
public class PopulateDatabase {

  @Inject
  private EntityManager entityManager;

  /** The user service. */
  @Inject
  private UserService userService;

  /** The book service. */
  @Inject
  private BookService bookService;

  @Inject
  private BorrowService borrowService;

  /**
   * Inits the database.
   */
  @PostConstruct
 
  void init() {

    entityManager.persist(newUser("admin", "Administrator", "admin", Role.ADMINISTRATOR));
    entityManager.persist(newUser("essai", "Essai", "essai", Role.ADMINISTRATOR));

    entityManager.persist(newUser("maxime", "Professor maxime", "maxime", Role.TEACHER));

    entityManager.persist(newUser("vincent", "Vincent", "vincent", Role.STUDENT));
    entityManager.persist(newUser("joanna", "Joanna", "joanna", Role.STUDENT));
    entityManager.persist(newUser("simon", "Simon", "simon", Role.STUDENT));

  /* entityManager.persist(newBook("Le Petit Prince", "Antoine de Saint Exupery"));
    entityManager.persist(newBook("Les Miserables", "Victor Hugo"));
    entityManager.persist(newBook("The Three Musketeers", "Alexandre Dumas"));
    
    entityManager.persist(newBook("The Three Musketeers"));
    newBorrow("jean", "Geometry", 10);
    newBorrow("paul", "Geometry", 15);
    newBorrow("albert", "Geometry", 11);
    newBorrow("jean", "Computer science", 18);
    newBorrow("albert", "Computer science", 17);*/
	   /* entityManager.persist(newUser("admin", "Administrator", "admin", Role.ADMINISTRATOR));
	    entityManager.persist(newUser("essai", "Essai", "essai", Role.ADMINISTRATOR));
	    entityManager.persist(newUser("x", "Professor X", "x", Role.TEACHER));
	    entityManager.persist(newUser("jean", "Jean", "jean", Role.STUDENT));
	    entityManager.persist(newUser("paul", "Paul", "paul", Role.STUDENT));
	    entityManager.persist(newUser("albert", "Albert", "albert", Role.STUDENT));*/

	   /*entityManager.persist(newBook("Geometry","Adin"));
	    entityManager.persist(newBook("Computer science","Ave"));*/
	    entityManager.persist(newBook("Le Petit Prince", "Antoine de Saint Exupery", 10));
	    entityManager.persist(newBook("Les Miserables", "Victor Hugo", 10));
	    entityManager.persist(newBook("The Three Musketeers", "Alexandre Dumas", 10));
	    //Date begin=borrowService.getCurDate();
	    //Date end=borrowService.getRetnDate();
	    newBorrow("vincent", "Le Petit Prince", 3, new Date(),new Date());
	    newBorrow("vincent", "Les Miserables", 3, new Date(),new Date());
	    newBorrow("vincent", "The Three Musketeers", 3, new Date(),new Date());
	    /*newBorrow("joanna", "Le Petit Prince", 3, new Date(),new Date());
	    newBorrow("joanna", "Les Miserables", 3, new Date(),new Date());
	    newBorrow("joanna", "The Three Musketeers", 3, new Date(),new Date());
	    newBorrow("simon", "Le Petit Prince", 3, new Date(),new Date());
	    newBorrow("simon", "Les Miserables", 3, new Date(),new Date());
	    newBorrow("simon", "The Three Musketeers", 3, new Date(),new Date());*/
	   /* newBorrow("vincent", "Le Petit Prince", 18, new Date(),new Date());
	    newBorrow("joanna", "Le Petit Prince", 17, new Date(),new Date());*/
	  

  }

  /**
   * New book.
   * 
   * @param name the name
   * @return the book
   */
  private Book newBook(final String name, final String auther, final int bookNb) {
    final Book book = new Book();
    book.setName(name);
    book.setAuther(auther);
    book.setBookNb(bookNb);
    return book;
  }

  /*private Book newBook(final String name) {
	    final Book book = new Book();
	    book.setName(name);
	   
	    return book;
	  }*/

  /**
   * New borrow.
   * 
   * @param loginName the login name
   * @param bookName the book name
   * @param borrowNb the borrowNb
   */
  private void newBorrow(final String loginName, final String bookName,final int borrowNb, final Date dateDebut, final Date dateFin) {
  //private void newBorrow(final String loginName, final String bookName, final int rate) {  
    final User user = userService.getUserByLogin(loginName);
    final Book book = bookService.getBookByName(bookName);
    final Borrow borrow = new Borrow();
    borrow.setBook(book);
    borrow.setUser(user);
    borrow.setBorrowNb(borrowNb);
    borrow.setDateDebut(dateDebut);
    borrow.setDateFin(dateFin);
    borrowService.saveBorrow(user.getId(), book.getId(), borrowNb, dateDebut, dateFin);
    //borrowService.saveBorrow(user.getId(), book.getId(), rate);
   // System.out.println(loginName+" borrowNB "+borrow.getBorrowNb());
  }

  /**
   * New user.
   * 
   * @param login the login
   * @param name the name
   * @param password the password
   * @param role the role
   * @return the user
   */
  private User newUser(final String login, final String name, final String password, final Role role) {
    final User user = new User();
    user.setLogin(login);
    user.setName(name);
    user.setPassword(password);
    user.setRole(role);
    return user;
  }

}
