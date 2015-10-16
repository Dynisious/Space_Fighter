package space_fighter_test_3d.global.events;

import java.util.EventListener;
/**
 * <p>
 * </p>
 *
 * @author Dynisious 07/10/2015
 * @version 0.0.1
 */
public interface ApplicationEventListener extends EventListener {

    /**
     * <p>
     * Handles a tick event fired by the application.</p>
     */
    public void handleGameTickEvent();

}
