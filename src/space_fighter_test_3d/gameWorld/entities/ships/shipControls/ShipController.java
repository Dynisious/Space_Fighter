package space_fighter_test_3d.gameWorld.entities.ships.shipControls;

import space_fighter_test_3d.gameWorld.entities.ships.ShipEntity;
import space_fighter_test_3d.gameWorld.entities.ships.events.ShipEntityEventListener;
/**
 * <p>
 * Used to control a ShipEntity.</p>
 *
 * @author Dynisious 06/10/2015
 * @version 0.0.1
 * @param <E> The type of EntityShip that this ShipController controls.
 */
public abstract class ShipController<E extends ShipEntity>
        implements ShipEntityEventListener<E> {
    private E ship;
    protected E getShip() {
        if (ship != null) {
            synchronized (ship) {
                return ship;
            }
        }
        return null;
    }
    private void clearShip() {
        if (this.ship != null) {
            synchronized (ship) {
                ship.removeListener(this);
                ship = null;
            }
        }
    }

    @Override
    public void handleEntityDestroyEvent() {
        clearShip();
    }

    @Override
    public void handleShipControllerSetEvent(final E ship,
                                             final ShipController<E> controller) {
        clearShip();
        this.ship = ship;
    }

}
