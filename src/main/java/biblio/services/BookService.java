
package biblio.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biblio.models.Book;

/**
 * The Class BookService.
 */
@Stateless
public class BookService {

  /** The logger. */
  private final Logger logger = LoggerFactory.getLogger(BookService.class);

  /** The entity manager. */
  @Inject
  private EntityManager entityManager;

  /**
   * Delete book.
   * 
   * @param book the book
   */
  public void deleteBook(final Book book) {
    final Book managerBook = getBookById(book.getId());
    if (managerBook != null) {
      // we need to delete borrow using the book first
      entityManager.createQuery("delete from Borrow b where b.bookId = :bookId")
          .setParameter("bookId", managerBook.getId()).executeUpdate();
      entityManager.remove(managerBook);
    } else {
      logger.warn("Can not delete unknown book {}", book);
    }
  }

  /**
   * Gets the all books.
   * 
   * @return the all books
   */
  public List<Book> getAllBooks() {
    return entityManager.createQuery("select b from Book b", Book.class).getResultList();
  }

  /**
   * Gets the book by id.
   * 
   * @param id the id
   * @return the book by id
   */
  public Book getBookById(final long id) {
    return entityManager.find(Book.class, id);
  }

  /**
   * Gets the book by name.
   * 
   * @param name the name
   * @return the book by name
   */
  public Book getBookByName(final String name) {
    final TypedQuery<Book> query = entityManager.createQuery("select b from Book b where b.name like :name",
        Book.class).setParameter("name", name);
    try {
      return query.getSingleResult();
    } catch (final PersistenceException ex) {
      return null;
    }
  }

  /**
   * Save book.
   * 
   * @param book the book
   */
  public void saveBook(final Book book) {
    entityManager.merge(book);
  }

}
