package space_fighter_test_3d.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import space_fighter_test_3d.global.events.GlobalEventListener;
import space_fighter_test_3d.exceptions.SetLogException;
/**
 * <p>
 * The ErrorLogger singleton writer out messages to a .log file and optionally
 * to the output.</p>
 *
 * @author Dynisious 05/10/2015
 * @version 0.0.1
 */
public final class ErrorLogger implements GlobalEventListener {
    private static ErrorLogger instance;
    public static ErrorLogger getInstance() {
        if (instance == null) {
            instance = new ErrorLogger();
        }
        return instance;
    }
    protected FileWriter out = null;
    /**
     * <p>
     * Sets the file to write to.</p>
     *
     * @param path The path to the file.
     *
     * @throws SetLogException Thrown if there is an exception while trying to
     *                         set the log file.
     */
    public static void setOutputFile(final String path)
            throws SetLogException {
        synchronized (instance) {
            try {
                instance.out = new FileWriter(new File(path), true);
            } catch (final IOException ex) {
                throw new SetLogException(
                        "ERROR : There was an error setting a new Error log file. Old file still in effect.",
                        3, ex);
            }
        }
    }
    private ErrorLogger() {
        String temp = System.getProperty("app.logs.errors");
        try (final FileWriter fw = new FileWriter(new File(
                System.getProperty("app.logs.errors")), false)) {
            final LocalDateTime now = LocalDateTime.now();
            int val;
            fw.write(String.format("Initialised: %1s-%1s-%4s T ",
                    now.getDayOfMonth(), now.getMonth().toString(),
                    now.getYear()) + (((val = now.getHour() / 2) == 0) ? 12 : val)
                    + String.format(":%02d:%02d\r\n", now.getMinute(),
                            now.getSecond()));
            fw.flush();
            fw.close();
            out = new FileWriter(System.getProperty("app.logs.errors"), true);
        } catch (final IOException | SecurityException ex) {
            System.out.println(ErrorLogger.formatMessage(
                    "ERROR : The Error log failed during initialisation. Errors will not be logged.",
                    2, ex));
        }
    }

    /**
     * <p>
     * Formates the passed message for writing.</p>
     *
     * @param message  The message to format.
     * @param priority The message's priority.
     * @param ex       The exception to write.
     *
     * @return The formated message.
     */
    public static String formatMessage(String message, final int priority,
                                       final Throwable ex) {
        message = String.format("%-18s : ", Thread.currentThread().getName()
                + "[" + priority + "]") + message + "\r\n  "
                + ex.getMessage();
        for (final StackTraceElement s : ex.getStackTrace()) {
            message += String.format("\r\n  %-6s: ", s.getLineNumber()) + s.toString();
        }
        return message + "\r\n";
    }

    /**
     * <p>
     * Writes the passed message to the log file and optionally the output.</p>
     *
     * @param message  The message to write.
     * @param priority The message's priority.
     * @param ex       The Throwable to write.
     * @param print    Whether to print the message.
     */
    public static void write(String message, final int priority,
                             final Throwable ex, final boolean print) {
        message = formatMessage(message, priority, ex);
        if (instance == null) {
            instance = new ErrorLogger();
        }
        synchronized (instance) {
            if (instance.out != null) {
                if (instance.out != null) {
                    try {
                        instance.out.write(message);
                        instance.out.flush();
                    } catch (final IOException ex1) {
                        EventLogger.write(
                                "ERROR : There was an error writing to the Error Log.",
                                1, true, message);
                    }
                }
            }
        }
        if (print) {
            System.out.print(message);
        }
    }

    @Override
    public synchronized void handleApplicationClosingEvent(final int reason) {
        synchronized (out) {
            try {
                out.close();
            } catch (final IOException ex) {
                //Nothing to do.
            }
            out = null;
        }
        MessageLogger.write("Error log now closed.", 1, true);
    }

}
