
package biblio.utils;

import javax.annotation.sql.DataSourceDefinition;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * The Class ApplicationResources.
 */
@DataSourceDefinition(name = "java:app/jdbc/tpnote", className = "com.mysql.jdbc.jdbc2.optional.MysqlDataSource",
    user = "tpnote", password = "tpnote", databaseName = "tpnote", serverName = "localhost", portNumber = 3306)
public class ApplicationResources {
  /**
   * Query String to allow clean redirect by JSF.
   */
  public static final String FACES_REDIRECT = "?faces-redirect=true";

  /** The em. */
  @Produces
  @PersistenceContext
  private EntityManager em;

  /**
   * Gets the faces context.
   * 
   * @return the current faces context
   */
  @Produces
  @RequestScoped
  public FacesContext getFacesContext() {
    return FacesContext.getCurrentInstance();
  }
}
