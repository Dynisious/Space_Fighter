package space_fighter_test_3d.gameWorld.entities.ships.events;

import space_fighter_test_3d.gameWorld.events.PhysicsObjectListener;
/**
 * <p>
 * Handles events fired by an EntityObject.</p>
 *
 * @author Dynisious 06/10/2015
 * @version 0.0.1
 */
public interface EntityEventListener extends PhysicsObjectListener {

    public void handleEntityDestroyEvent();

}
