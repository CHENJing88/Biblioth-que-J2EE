
package biblio.models;

/**
 * The Enum Role.
 */
public enum Role {

  /** The student. */
  STUDENT("Student"),
  /** The teacher. */
  TEACHER("Teacher"),
  /** The administrator. */
  ADMINISTRATOR("Administrator");

  /** The name. */
  private String name;

  /**
   * Instantiates a new role.
   * 
   * @param name the name
   */
  Role(final String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Enum#toString()
   */
  @Override
  public String toString() {
    return name;
  }
}
