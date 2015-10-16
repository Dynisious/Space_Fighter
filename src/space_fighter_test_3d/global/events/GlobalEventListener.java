package space_fighter_test_3d.global.events;

import java.util.EventListener;
/**
 * <p>
 * Handles for events fired by the GlobalEvents instance.</p>
 *
 * @author Dynisious 05/10/2015
 * @version 0.0.1
 */
public interface GlobalEventListener extends EventListener {

    /**
     * <p>
     * This event fires when the application is about to close. This is a last
     * chance for objects to finalise.
     *
     * @param reason The reason for the application's closing.
     */
    public void handleApplicationClosingEvent(final int reason);

}
