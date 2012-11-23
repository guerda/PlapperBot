package de.guerda;
/**
 * 
 */


/**
 * This class represents a simple top level {@link Exception} for an
 * application. The goal of this exception is that the application only catches
 * this type of Exceptions and all functions only throw exceptions of this type.
 * So every other type of exception can be re-thrown and encapsulated into an
 * {@link ApplicationException}. The GUI may display the last message including
 * an optional error code.
 * 
 * @author philip
 * 
 */
public class ApplicationException extends Exception {

  private static final long serialVersionUID = 1L;

  public ApplicationException() {
  }

  /**
   * Creates an {@link ApplicationException} with a message to be displayed to
   * the user.
   * 
   * @param aMessage
   *          - the message to be displayed in the GUI.
   */
  public ApplicationException(String aMessage) {
    super(aMessage);
  }

  /**
   * Creates an {@link ApplicationException} with a message to be displayed to
   * the user and an inner Exception that caused the problem.
   * 
   * @param aMessage
   *          - the message to be displayed in the GUI.
   * @param aCause
   *          - the triggering {@link Exception}.
   */
  public ApplicationException(String aMessage, Throwable aCause) {
    super(aMessage, aCause);
  }

}
