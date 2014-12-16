
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
 * The Class User.
 */
@Entity
public class User implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 0;

  /** The id. */
  @Id
  @GeneratedValue
  private long id;

  /** The login. */
  @Column(unique = true)
  private String login;

  /** The name. */
  private String name;

  /** The password. */
  private String password;

  /** The role. */
  private Role role;

  /** The borrows. */
  @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
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
    final User other = (User) obj;
    if (login == null) {
      if (other.login != null) {
        return false;
      }
    } else if (!login.equals(other.login)) {
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
   * Gets the login.
   * 
   * @return the login
   */
  public String getLogin() {
    return login;
  }

  /**
   * Gets the name.
   * 
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the password.
   * 
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Gets the borrows.
   * 
   * @return the borrows
   */
  public List<Borrow> getBorrows() {
    return borrows;
  }

  /**
   * Gets the role.
   * 
   * @return the role
   */
  public Role getRole() {
    return role;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (login == null ? 0 : login.hashCode());
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
   * Sets the login.
   * 
   * @param login the new login
   */
  public void setLogin(final String login) {
    this.login = login;
  }

  /**
   * Sets the name.
   * 
   * @param name the new name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Sets the password.
   * 
   * @param password the new password
   */
  public void setPassword(final String password) {
    this.password = password;
  }

  /**
   * Sets the role.
   * 
   * @param role the new role
   */
  public void setRole(final Role role) {
    this.role = role;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "User [id=" + id + ", login=" + login + ", name=" + name + /*
                                                                       * ", password="
                                                                       * +
                                                                       * password
                                                                       * +
                                                                       */", role=" + role + "]";
  }

}
