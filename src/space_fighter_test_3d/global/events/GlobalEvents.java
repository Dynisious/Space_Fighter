package space_fighter_test_3d.global.events;

import dynutils.events.EventObject;
import java.util.EventListener;
import space_fighter_test_3d.global.Application;
import space_fighter_test_3d.logging.EventLogger;
import space_fighter_test_3d.logging.MessageLogger;
/**
 * <p>
 * This singleton class which fires events that need to be handled by all parts
 * of the application.</p>
 *
 * @author Dynisious 05/10/2015
 * @version 0.0.1
 */
public final class GlobalEvents extends EventObject<GlobalEventListener> {
    public static final GlobalEvents instance = new GlobalEvents();
    /**
     * <p>
     * The circumstances for the close are not specified.</p>
     */
    public static final int AppClose_Unspecified_Circumstances = -1;
    /**
     * <p>
     * A standard closing operation.</p>
     */
    public static final int AppClose_Standard_Operation = 0;
    /**
     * <p>
     * All GraphicsThreads have died.</p>
     */
    public static final int AppClose_Critical_Graphics_Death = 1;
    /**
     * <p>
     * The main GraphicsModule Thread has died.</p>
     */
    public static final int AppClose_Main_Graphics_Death = 2;
    /**
     * <p>
     * The main Thread has died.</p>
     */
    public static final int AppClose_Main_Thread_Death = 3;
    /**
     * <p>
     * There was an error while initialising the application.</p>
     */
    public static final int AppClose_Initialisation_Error = 4;
    /**
     * <p>
     * There was an error while executing the game loop.</p>
     */
    public static final int AppClose_Break_In_Game_Loop = 5;

    /**
     * <p>
     * Signals all GlobalEventListeners that have attached themselves to the
     * GlobalEvents object before closing the application.</p>
     *
     * @param reason The reason that the application is closing.
     */
    public synchronized static void fireApplicationClosingEvent(final int reason) {
        final String message = "Application is now closing...";
        MessageLogger.write(message, 1, true);
        EventLogger.write(message, 1, false, "reason=" + reason);
        Application.applicationAlive = false;
        synchronized (instance) {
            for (final EventListener l : instance.getListeners()) {
                ((GlobalEventListener) l).handleApplicationClosingEvent(reason);
            }
        }
        if (reason == AppClose_Critical_Graphics_Death) {
            System.out.println(
                    "Application has closed from Critical_Graphics_Death.");
        } else if (reason == AppClose_Standard_Operation) {
            System.out.println(
                    "Application has completed it's standard close opperation.");
        } else if (reason == AppClose_Unspecified_Circumstances) {
            System.out.println(
                    "Application has closed due to unspecified surcumstances.");
        }
        System.exit(reason);
    }

}
