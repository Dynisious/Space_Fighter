package space_fighter_test_3d.gameWorld.entities.ships.events;

import space_fighter_test_3d.gameWorld.entities.ships.ShipEntity;
import space_fighter_test_3d.gameWorld.entities.ships.shipControls.ShipController;
/**
 * <p>
 * Listens for events thrown by a ShipEntity.</p>
 *
 * @author Dynisious 14/10/2015
 * @version 0.0.1
 */
public interface ShipEntityEventListener extends EntityEventListener {

    public void handleShipControllerSetEvent(final ShipEntity ship,
                                             final ShipController controller);

}
