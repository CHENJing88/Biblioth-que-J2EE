
package biblio.controllers;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import biblio.models.SessionInfo;
import biblio.models.User;
import biblio.services.UserService;

/**
 * The Class UserManagementController.
 */
@Named
@SessionScoped
public class UserManagementController implements Serializable {

  private static final long serialVersionUID = 0;

  /** The session info. */
  @Inject
  private SessionInfo sessionInfo;

  /** The user service. */
  @Inject
  UserService userService;

  /** The managed user. */
  private User managedUser;

  /** The faces context. */
  @Inject
  private FacesContext facesContext;

  /**
   * Do delete user.
   * 
   * @param delete the delete
   * @return the string
   */
  public String doDeleteUser(final boolean delete) {
    if (delete) {
      userService.deleteUser(managedUser);
    }
    managedUser = null;
    return "usermanagement";
  }

  /**
   * Do new user.
   * 
   * @return the string
   */
  public String doNewUser() {
    if (userService.getUserByLogin(managedUser.getLogin()) != null) {
      facesContext.addMessage("User already exist", new FacesMessage("Login already used"));
      return null;
    } else {
      userService.saveUser(managedUser);
      return "usermanagement";
    }
  }

  /**
   * Do save user.
   * 
   * @param save the save
   * @return the string
   */
  public String doSaveUser(final boolean save) {
    if (save) {
      userService.saveUser(managedUser);
      if (sessionInfo.getUser().getLogin().equals(managedUser.getLogin())) {
        // reload logged user from database
        sessionInfo.setUser(userService.getUserByLogin(managedUser.getLogin()));
      }
    }
    managedUser = null;
    return "usermanagement";
  }

  /**
   * Gets the all users.
   * 
   * @return the all users
   */
  @Produces
  @Named
  @RequestScoped
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  /**
   * Gets the managed user.
   * 
   * @return the managed user
   */
  public User getManagedUser() {
    return managedUser;
  }

  /**
   * Prepare delete user.
   * 
   * @param user the user
   * @return the string
   */
  public String prepareDeleteUser(final User user) {
    managedUser = user;
    return "deleteuser";
  }

  /**
   * Prepare edit user.
   * 
   * @param user the user
   * @return the string
   */
  public String prepareEditUser(final User user) {
    managedUser = user;
    return "edituser";
  }

  /**
   * Prepare new user.
   * 
   * @return the string
   */
  public String prepareNewUser() {
    managedUser = new User();
    return "newuser";
  }
}
