package space_fighter_test_3d.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import space_fighter_test_3d.global.events.GlobalEventListener;
import space_fighter_test_3d.exceptions.SetLogException;
/**
 * <p>
 * The EventLogger singleton writes out events to a .log file and optionally
 * to the output.</p>
 *
 * @author Dynisious 05/10/2015
 * @version 0.0.1
 */
public final class EventLogger implements GlobalEventListener {
    private static EventLogger instance;
    public static EventLogger getInstance() {
        if (instance == null) {
            instance = new EventLogger();
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
    public synchronized static void setOutputFile(final String path)
            throws SetLogException {
        synchronized (instance) {
            try {
                instance.out = new FileWriter(new File(path));
            } catch (final IOException ex) {
                throw new SetLogException(
                        "ERROR : There was an error setting a new Event log file. Old file still in effect.",
                        3, ex);
            }
        }
    }
    private EventLogger() {
        try (final FileWriter fw = new FileWriter(new File(
                System.getProperty("app.logs.events")), false)) {
            final LocalDateTime now = LocalDateTime.now();
            int val;
            fw.write(String.format("Initialised: %1s-%1s-%4s T ",
                    now.getDayOfMonth(), now.getMonth().toString(),
                    now.getYear()) + (((val = now.getHour() / 2) == 0) ? 12 : val)
                    + String.format(":%02d:%02d\r\n", now.getMinute(),
                            now.getSecond()));
            fw.flush();
            fw.close();
            out = new FileWriter(System.getProperty("app.logs.events"), true);
        } catch (final IOException | SecurityException ex) {
            System.out.println(ErrorLogger.formatMessage(
                    "ERROR : The Event log failed during initialisation. Events will not be logged.",
                    2, ex));
        }
    }

    /**
     * <p>
     * Formates the passed message for writing.</p>
     *
     * @param message  The message to format.
     * @param priority The message's priority.
     * @param items    The events items to write out.
     *
     * @return The formated message.
     */
    public static String formatMessage(String message, final int priority,
                                       final String[] items) {
        message = String.format("%-18s : ", Thread.currentThread().getName()
                + "[" + priority + "]") + message;
        for (final String s : items) {
            message += "\r\n  " + s;
        }
        return message + "\r\n";
    }

    /**
     * <p>
     * Writes the passed message to the log file and optionally the output.</p>
     *
     * @param message  The message to write.
     * @param priority The message's priority.
     * @param print    Whether to print the message.
     * @param items    The events items to write.
     */
    public static void write(String message, final int priority,
                             final boolean print, final String... items) {
        message = formatMessage(message, priority, items);
        if (instance == null) {
            instance = new EventLogger();
        }
        synchronized (instance) {
            if (instance.out != null) {
                try {
                    instance.out.write(message);
                    instance.out.flush();
                } catch (final IOException ex) {
                    ErrorLogger.write(
                            "ERROR : There was an error writting a event to the log file.",
                            2, ex, false);
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
                ErrorLogger.write(
                        "ERROR : There was an error closing the Event log.", 1,
                        ex, true);
            }
            out = null;
        }
        MessageLogger.write("Event log now closed.", 1, true);
    }

}
