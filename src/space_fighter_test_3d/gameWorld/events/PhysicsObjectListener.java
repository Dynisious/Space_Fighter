package space_fighter_test_3d.gameWorld.events;

import java.util.EventListener;
/**
 * <p>
 * Listens for events to fire on a PhysicsObjec.</p>
 *
 * @author Dynisious 16/10/2015
 * @version 0.0.1
 */
public interface PhysicsObjectListener extends EventListener {

    public void handleCollisionEvent();

    public void handlePhysicsObjectUpdateEvent();

}
