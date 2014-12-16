
package biblio.models;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The Class Borrow.
 */
@Entity
@IdClass(BorrowId.class)
public class Borrow {

	/** The user id. */
  @Id
  private long userId;

  /** The book id. */
  @Id
  private long bookId;

  /** The user. */
  @ManyToOne
  @PrimaryKeyJoinColumn(name = "USERID", referencedColumnName = "ID")
  private User user;

  /** The book. */
  @ManyToOne
  @PrimaryKeyJoinColumn(name = "BOOKID", referencedColumnName = "ID")
  private Book book;
  
  /** The dateDebut. */
  @Temporal(TemporalType.DATE) 
  private Date dateDebut;
  
  /** The dateFin. */
  @Temporal(TemporalType.DATE) 
  private Date dateFin;
  
  /** The borrowNb. */
  
  private int borrowNb;
  

  /**
   * Gets the book.
   * 
   * @return the book
   */
  public Book getBook() {
    return book;
  }

  /**
   * Gets the borrowNb.
   * 
   * @return the borrowNb
   */
 /* public int getRate() {
    return rate;
  }*/
    public int getBorrowNb() {
	    return borrowNb;
    }
    
  
  /**
   * Gets the user.
   * 
   * @return the user
   */
  public User getUser() {
    return user;
  }
  /**
   * Gets the dateDebut.
   * 
   * @return the dateDebut
   */
  public Date getDateDebut() {
	    return dateDebut;
  }
  /**
   * Gets the dateFin.
   * 
   * @return the dateFin
   */
  public Date getDateFin() {
	    return dateFin;
}
  /**
   * Sets the book.
   * 
   * @param book the new book
   */
  public void setBook(final Book book) {
    this.book = book;
    this.bookId = book.getId();
  }
  
  /**
   * Sets the borrowNb.
   * 
   * @param borrowNb the new borrowNb
   */
    public void setBorrowNb(final int borrowNb) {
	    this.borrowNb = borrowNb;
    }

  /**
   * Sets the user.
   * 
   * @param user the new user
   */
  public void setUser(final User user) {
    this.user = user;
    this.userId = user.getId();
  }
  /**
   * Sets the dateDebut.
   * 
   * @param dateDebut the new date
   */
  public void setDateDebut(Date date) {
	    dateDebut=date;
  }
 
  /**
   * Sets the dateFin.
   * 
   * @param dateFin the new date
   */

  public void setDateFin(Date date) {
	    dateFin=date;
  }
  
  /*
   * (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (user == null ? 0 : user.hashCode());
    return result;
  }

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
    final Borrow other = (Borrow) obj;
    if ((user == null)&&(book == null)) {
      if ((other.user != null)&&(other.book != null)) {
        return false;
      }
    } else if ( !( (user.equals(other.user)) && (book.equals(other.book)) ) ) {
      return false;
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Borrow [userId=" + userId + ", bookId=" + bookId +", borrowNb="+ borrowNb+", dateDebut=" + dateDebut + ", dateFin="+ dateFin +"]";
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
