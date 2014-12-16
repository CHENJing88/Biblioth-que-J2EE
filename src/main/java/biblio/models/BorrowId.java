
package biblio.models;

import java.io.Serializable;

/**
 * The Class BorrowId.
 */
public class BorrowId implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 0;

  /** The user id. */
  private long userId;

  /** The book id. */
  private long bookId;

  /*
   * (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final BorrowId other = (BorrowId) obj;
    if (bookId != other.bookId) {
      return false;
    }
    if (userId != other.userId) {
      return false;
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (bookId ^ bookId >>> 32);
    result = prime * result + (int) (userId ^ userId >>> 32);
    return result;
  }

}
