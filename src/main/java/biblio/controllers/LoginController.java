
package biblio.controllers;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import biblio.models.SessionInfo;
import biblio.models.User;
import biblio.services.UserService;

/**
 * The Class LoginController.
 */
@Named
@RequestScoped
public class LoginController {

  /** The user service. */
  @Inject
  private UserService userService;

  /** The faces context. */
  @Inject
  private FacesContext facesContext;

  /** The session info. */
  @Inject
  private SessionInfo sessionInfo;

  /** The login. */
  private String login;

  /** The password. */
  private String password;

  /**
   * Authenticate a user.
   * 
   * @return the string
   */
  public String authenticate() {
    final User user = userService.getUserByLogin(login);
    if (user == null) {
      facesContext.addMessage(null, new FacesMessage("Unknown login name"));
    } else {
      if (user.getPassword().equals(password)) {
        sessionInfo.setUser(user);
        return "index";
      } else {
        facesContext.addMessage(null, new FacesMessage("Invalid password"));
      }
    }
    return null;
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
   * Gets the password.
   * 
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Logout.
   * 
   * @return the string
   */
  public String logout() {
    sessionInfo.setUser(null);
    facesContext.getExternalContext().invalidateSession();
    return "index";
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
   * Sets the password.
   * 
   * @param password the new password
   */
  public void setPassword(final String password) {
    this.password = password;
  }
}
