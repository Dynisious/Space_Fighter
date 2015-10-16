package space_fighter_test_3d.gameWorld.entities.ships.events;

import space_fighter_test_3d.gameWorld.entities.ships.ShipEntity;
import space_fighter_test_3d.gameWorld.entities.ships.shipControls.ShipController;
/**
 * <p>
 * Listens for events thrown by a ShipEntity.</p>
 *
 * @author Dynisious 14/10/2015
 * @version 0.0.1
 * @param <E> The type of ShipEntity this Listener listens on.
 */
public interface ShipEntityEventListener<E extends ShipEntity>
        extends EntityEventListener {

    public void handleShipControllerSetEvent(final E ship,
                                             final ShipController<E> controller);

}
