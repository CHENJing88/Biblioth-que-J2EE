
package biblio.models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * The Class book.
 */
@Entity
public class Book implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 0L;

  /** The id. */
  @Id
  @GeneratedValue
  private long id;

  /** The name. */
  @Column(unique = true)
  private String name;
  
  /** The auther. */
  private String auther;
  
  /** The bookNb. */
  private int bookNb;
  
  /** The borrows. */
  @OneToMany(mappedBy = "book", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
  private List<Borrow> borrows;

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
    final Book other = (Book) obj;
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }

  /**
   * Gets the id.
   * 
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * Gets the name.
   * 
   * @return the name
   */
  public String getName() {
    return name;
  }
  public String getAuther() {
	    return auther;
	  }
  public int getBookNb() {
	    return bookNb;
	  }

  /**
   * Gets the borrows.
   * 
   * @return the borrows
   */
  public List<Borrow> getBorrows() {
    return borrows;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (name == null ? 0 : name.hashCode());
    return result;
  }

  /**
   * Sets the id.
   * 
   * @param id the new id
   */
  public void setId(final long id) {
    this.id = id;
  }

  /**
   * Sets the name.
   * 
   * @param name the new name
   */
  public void setName(final String name) {
    this.name = name;
  }

  public void setAuther(final String auther) {
	    this.auther = auther;
	  }
  public void setBookNb(final int bookNb) {
	    //return bookNb;
	  this.bookNb = bookNb;
	  }
  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Book [id=" + id + ", name=" + name + ", auther=" + auther + ", bookNb=" + bookNb + "]";
  }
 
}
