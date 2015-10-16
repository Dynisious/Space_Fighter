package space_fighter_test_3d.exceptions;

/**
 * <p>
 * Thrown when a properties field is not found.</p>
 *
 * @author Dynisious 14/10/2015
 * @version 0.0.1
 */
public class NoPropertiesFieldException extends Exception {

    public NoPropertiesFieldException(final String message,
                                      final Throwable cause) {
        super(message, cause);
    }

}
