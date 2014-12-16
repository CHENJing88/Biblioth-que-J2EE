
package biblio.utils;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class used by the servlet container to redirect unauthenticated user to the
 * login page
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

  /** The logger. */
  private final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

  /** The Constant unsecuredPaths. */
  private static final String[] unsecuredPaths = { "/javax.faces.resource/", "/login.jsf" };

  /**
   * Checks if is unsecured path.
   * 
   * @param path the path
   * @return true, if is unsecured path
   */
  private static boolean isUnsecuredPath(final String path) {
    for (final String unsecuredPath : LoginFilter.unsecuredPaths) {
      if (path.startsWith(unsecuredPath)) {
        return true;
      }
    }
    return false;
  }

  /*
   * (non-Javadoc)
   * @see javax.servlet.Filter#destroy()
   */
  @Override
  public void destroy() {
  }

  /*
   * (non-Javadoc)
   * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
   * javax.servlet.ServletResponse, javax.servlet.FilterChain)
   */
  @Override
  public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
      throws ServletException, IOException {
    final HttpServletRequest req = (HttpServletRequest) request;
    final Object loggedInObject = req.getSession().getAttribute("loggedin");
    final boolean loggedIn = loggedInObject != null && loggedInObject instanceof Boolean && (Boolean) loggedInObject;

    if (loggedIn || LoginFilter.isUnsecuredPath(req.getServletPath())) {
      // just continue the request if logged or for unsecured paths
      chain.doFilter(request, response);
    } else {
      logger.info("Not logged in. Redirecting {} to /login.jsf", req.getServletPath());
      // User is not logged in, so redirect to index.
      final HttpServletResponse res = (HttpServletResponse) response;
      res.sendRedirect(req.getContextPath() + "/login.jsf");
    }
  }

  /*
   * (non-Javadoc)
   * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
   */
  @Override
  public void init(final FilterConfig arg0) throws ServletException {
  }

}
