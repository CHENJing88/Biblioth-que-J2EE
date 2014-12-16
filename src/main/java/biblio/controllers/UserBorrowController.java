
package biblio.controllers;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import biblio.models.Book;
import biblio.models.Borrow;
import biblio.models.SessionInfo;
import biblio.models.User;
import biblio.services.BorrowService;

/**
 * The Class UserBorrowController.
 */
@Named
@SessionScoped
public class UserBorrowController implements Serializable {

  private static final long serialVersionUID = 0;

  /** The session info. */
  @Inject
  private SessionInfo sessionInfo;

  /** The borrow service. */
  @Inject
  private BorrowService borrowService;
  /** The faces context. */
  @Inject
  private FacesContext facesContext;

  /** The  managedBorrow. */
  private Borrow managedBorrow;
  
  /** The userLogin. */
  private String userLogin;
  
  /** The bookName. */
  private String bookName;
 
  /**
   * Gets managedBorrow.
   * 
   * @return managedBorrow
   */
  public Borrow getManagedBorrow(){
	  return managedBorrow;
  }
  /**
   * Gets userLogin.
   * 
   * @return userLogin
   */
  public String getUserLogin(){
	  return userLogin;
  }
  
  /**
   * Gets bookName.
   * 
   * @return bookName
   */
  public String  getBookName(){
	  return bookName;
  }
  
  /**
   * Sets userLogin.
   * 
   * @param login is userLogin
   */
  public void setUserLogin(String login){
	  this.userLogin=login;
  }
  
  /**
   * Sets bookName.
   * 
   * @param bookName is book
   */
  public void  setBookName(String book){
	  this.bookName=book;
  }
  
  /**
   * Gets the borrow number of book.
   * 
   * @return the borrow number
   */
  public int getBorwNbByBook(long bookId) {
	  return borrowService.getBorwNbByBook(bookId);
  }

  /**
   * Gets the number of remaining books
   * 
   * @return the borrow number
   */
  public int getBookRestNb(Book book) {
    return borrowService.getBookRestNb(book.getId());
  }
  
  /**
   * Gets the borrow number of user.
   * 
   * @return the borrow number
   */
  public int getBorwNbByUser(long userId) {
	  return borrowService.getBorwNbByBook(userId);
  }

  /**
   * Gets the borrows.
   * 
   * @return the borrows
   */
  public List<Borrow> getBorrows() {
    return borrowService.getBorrowByUserId(sessionInfo.getUser().getId());
  }

  /**
   * Gets all borrows.
   * 
   * @return all borrows
   */
  @Produces
  @Named
  @RequestScoped
  public List<Borrow> getAllBorrows() {
   return borrowService.getAllBorrows();
  }
  
  /**
   * Gets book by name.
   * 
   * @return book
   */
  public Book getBookByName(String bookName){
	return borrowService.getBookByName(bookName);
  }
	
  /**
   * Gets user by name.
   * 
   * @return user
   */
  public User getUserByName(String userName){
	return borrowService.getUserByName(userName);
  }
  
  /**
   * Gets current date.
   * 
   * @return current date.
   */
  public Date getCurDate(){
	return  borrowService.getCurDate();
  }
  
  /**
   * Gets return date.
   * 
   * @return return date.
   */
  public Date getRetnDate(){
	 return borrowService.getRetnDate();
  }
  
  /**
 * Prepare new borrow record.
 * 
 * @return the string
 */
  public String prepareNewBorrow() {
	   managedBorrow = new Borrow();
	  return "newborrow";
  }
  
  /**
   * Prepare delet borrow record.
   * 
   * @return the string
   */
  public String prepareDeleteBorrow(final Borrow borrow) {
	  managedBorrow = borrow;
	  return "deleteborrow";
  }
  
	 
  /**
   * Do new borrow record.
   * 
   * @return the string
   */	 
  public String doNewBorrow() {
	  
	 Book book= borrowService.getBookByName(bookName);
	 User user=borrowService.getUserByName(userLogin);
	 int rest=borrowService.getBookRestNb(book.getId());
	
    if( user == null) {
    	facesContext.addMessage("User not exist", new FacesMessage("User login not exist !"));
        return null;
    }
    if( rest<managedBorrow.getBorrowNb()) {
    	facesContext.addMessage("Borrow numbre not unavailable", new FacesMessage("Only "+rest+" books are Available !"));
        return null;
    }
    if(managedBorrow.getDateDebut().after(managedBorrow.getDateFin())){
    	facesContext.addMessage("Date Error", new FacesMessage("Return Date must after borrow date !"));
        return null;
    }
    managedBorrow.setBook(book);
    managedBorrow.setUser(user);
    
    borrowService.saveBorrow(user.getId(),book.getId(), managedBorrow.getBorrowNb(), managedBorrow.getDateDebut(), managedBorrow.getDateFin());
    return "borrowmanagement";
    
  }
  /**
   * Do delete borrow.
   * 
   * @param delete the delete
   * @return the string
   */
  public String doDeleteBorrow(final boolean delete) {
	
    if (delete) {
      borrowService.deleteBorrow(managedBorrow);
    }
    managedBorrow = null;
    return "borrowmanagement";
  }
  
  /**
   * change the date to string.
   * 
   * @param Date
   * @return String
   */
  public String dateToString(Date myDate) { 
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd"); 
	    String strDate = formatter.format(myDate); 
	    return strDate; 
}
}