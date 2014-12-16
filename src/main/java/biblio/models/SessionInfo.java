
package biblio.models;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

//TODO: Auto-generated Javadoc
/**
 * The Class SessionInfo.
 */
@Named
@SessionScoped
public class SessionInfo implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 0;

  /** The faces context. */
  @Inject
  FacesContext facesContext;

  /** The user. */
  private User user;

  /**
   * Gets the user.
   * 
   * @return the user
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets the user.
   * 
   * @param user the new user
   */
  public void setUser(final User user) {
    this.user = user;
    // set a flag in the session (at servlet level) for the access restriction
    // web filter
    facesContext.getExternalContext().getSessionMap().put("loggedin", user == null ? false : true);
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "SessionInfo [user=" + user + "]";
  }
}
