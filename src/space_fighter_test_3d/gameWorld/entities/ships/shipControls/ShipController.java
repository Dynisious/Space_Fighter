package space_fighter_test_3d.gameWorld.entities.ships.shipControls;

import space_fighter_test_3d.gameWorld.entities.ships.ShipEntity;
import space_fighter_test_3d.gameWorld.entities.ships.events.ShipEntityEventListener;
/**
 * <p>
 * Used to control a ShipEntity.</p>
 *
 * @author Dynisious 06/10/2015
 * @version 0.0.1
 */
public abstract class ShipController implements ShipEntityEventListener {
    private ShipEntity ship;
    public ShipEntity getShip() {
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
    public void handleShipControllerSetEvent(final ShipEntity ship,
                                             final ShipController controller) {
        clearShip();
        this.ship = ship;
    }

}
