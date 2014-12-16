
package biblio.controllers;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
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
import biblio.models.Role;
import biblio.models.SessionInfo;
import biblio.services.BookService;
import biblio.services.BorrowService;

@Named
@SessionScoped
public class BookManagementController implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 0;

  /** The book service. */
  @Inject
  private BookService bookService;

  /** The managed book. */
  private Book managedBook;

  /** The borrow service. */
  @Inject
  private BorrowService borrowService;

  /** The student borrows. */
  private List<Borrow> studentBorrows;

  /** The session info. */
  @Inject
  private SessionInfo sessionInfo;

  /** The faces context. */
  @Inject
  private FacesContext facesContext;

  /**
   * Do delete book.
   * 
   * @param delete the delete
   * @return the string
   */
  public String doDeleteBook(final boolean delete) {
    if (delete) {
      bookService.deleteBook(managedBook);
    }
    managedBook = null;
    return "bookmanagement";
  }

  /**
   * Do new book.
   * 
   * @return the string
   */
  public String doNewBook() {
    if (bookService.getBookByName(managedBook.getName()) != null) {
      facesContext.addMessage("Book already exist", new FacesMessage("Book name already used"));
      return null;
    } else {
      bookService.saveBook(managedBook);
      return "bookmanagement";
    }
  }

  /**
   * Do save borrows.
   * 
   * @return the string
   */
  public String doSaveBorrows() {
    for (final Borrow borrow : studentBorrows) {
    	borrowService.saveBorrow(borrow.getUser().getId(), managedBook.getId(), borrow.getBorrowNb(),borrow.getDateDebut(),borrow.getDateFin());
    }
    return "bookmanagement"; // reload page
  }

  /**
   * Gets the all books.
   * 
   * @return the all books
   */
  @Produces
  @Named
  @RequestScoped
  public List<Book> getAllBooks() {
    if (sessionInfo.getUser().getRole() == Role.STUDENT) {
      return Collections.emptyList();
    } else {
      return bookService.getAllBooks();
    }
  }

  /**
   * Gets the managed book.
   * 
   * @return the managed book
   */
  public Book getManagedBook() {
    return managedBook;
  }

  /**
   * Gets the max borrow.
   * 
   * @param bookId the book id
   * @return the max borrow
   */
  public int getBorwNbByUser(final long userId) {
    return borrowService.getBorwNbByUser(userId);
  }

  /**
   * Gets the rest nb.
   * 
   * @param bookId the book id
   * @return the rest nb
   */
  public int getBorwNbByBook(final long bookId) {
    return borrowService.getBorwNbByBook(bookId);
  }

  /**
   * Gets the min borrow.
   * 
   * @param bookId the book id
   * @return the min borrow
   */
  public int getBookRestNb(final Book book) {
    return borrowService.getBookRestNb(book.getId());
  }

  /**
   * Gets the student borrows.
   * 
   * @return the student borrows
   */
  @Named
  @Produces
  @RequestScoped
  public List<Borrow> getStudentBorrows() {
    this.studentBorrows = borrowService.getStudentBorrowsByBookId(managedBook.getId());
    // sort in alphabetical order ignoring case
    Collections.sort(studentBorrows, new Comparator<Borrow>() {
      @Override
      public int compare(final Borrow arg0, final Borrow arg1) {
        return arg0.getUser().getName().compareToIgnoreCase(arg1.getUser().getName());
      }
    });

    return studentBorrows;
  }

  /**
   * Prepare delete book.
   * 
   * @param book the book
   * @return the string
   */
  public String prepareDeleteBook(final Book book) {
    managedBook = book;
    return "deletebook";
  }

  /**
   * Prepare edit book.
   * 
   * @param book the book
   * @return the string
   */
  public String prepareBorrowRecord(final Book book) {
    managedBook = book;
    return "borrowrecord";
  }

  /**
   * Prepare new book.
   * 
   * @return the string
   */
  public String prepareNewBook() {
    managedBook = new Book();
    return "newbook";
  }
}
