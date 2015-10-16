package space_fighter_test_3d.exceptions;

import java.io.IOException;
/**
 * <p>
 * Indicates that there was an error setting a new .log file for one of the
 * Loggers.</p>
 *
 * @author Dynisious 05/10/2015
 * @version 0.0.1
 */
public class SetLogException extends IOException {
    /**
     * <p>
     * The priority of this SetLogException, 1 being the highest.</p>
     */
    public final int priority;

    /**
     * @param message  The message for this Exception.
     * @param priority The priority of this Exception.
     * @param cause    The Throwable that caused this Exception.
     */
    public SetLogException(final String message, final int priority,
                           final Throwable cause) {
        super(message, cause);
        this.priority = priority;
    }

}
