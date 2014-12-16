
package biblio.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biblio.models.Role;
import biblio.models.User;

/**
 * The Class UserService.
 */
@Stateless
public class UserService {

  /** The logger. */
  private final Logger logger = LoggerFactory.getLogger(UserService.class);

  /** The entity manager. */
  @Inject
  private EntityManager entityManager;


  /**
   * Gets the all users.
   * 
   * @return the all users
   */
  public List<User> getAllUsers() {
    return entityManager.createQuery("select u from User u", User.class).getResultList();
  }

  /**
   * Gets the all users.
   * 
   * @param role the role
   * @return the all users
   */
  public List<User> getAllUsers(final Role role) {
    return entityManager.createQuery("select u from User u where u.role = :role", User.class)
        .setParameter("role", role).getResultList();
  }

  /**
   * Gets the user by id.
   * 
   * @param userId the user id
   * @return the user by id
   */
  public User getUserById(final long userId) {
    return entityManager.find(User.class, userId);
  }

  /**
   * Gets the user by login.
   * 
   * @param login the login
   * @return the user by login
   */
  public User getUserByLogin(final String login) {
    final TypedQuery<User> query = entityManager.createQuery("select u from User u where u.login like :login",
        User.class).setParameter("login", login);
    try {
      return query.getSingleResult();
    } catch (final PersistenceException ex) {
      return null;
    }
  }
  /**
   * Gets the user by name.
   * 
   * @param name the name
   * @return the user by name
   */
  public User getUserByName(final String name) {
	    final TypedQuery<User> query = entityManager.createQuery("select u from User u where u.name like :name",
	        User.class).setParameter("name", name);
	    try {
	      return query.getSingleResult();
	    } catch (final PersistenceException ex) {
	      return null;
	    }
	  }

  /**
   * Save user.
   * 
   * @param user the user
   */
  public void saveUser(final User user) {
    entityManager.merge(user);
  }
  
  /**
   * Delete user.
   * 
   * @param user the user
   */
  public void deleteUser(final User user) {
    final User managedUser = getUserByLogin(user.getLogin());
    if (managedUser != null) {
      // we need to delete borrow using the user first
      entityManager.createQuery("delete from Borrow b where b.userId = :userId")
          .setParameter("userId", managedUser.getId()).executeUpdate();
      entityManager.remove(managedUser);
    } else {
      logger.warn("Can not delete unknown user {}", user);
    }
  }
}
