package space_fighter_test_3d.gameWorld.entities.ships.shipControls;

import space_fighter_test_3d.gameWorld.entities.ships.ShipEntity;
/**
 * <p>
 * A ShipUpdateable is a ShipController that is intended to be run in a
 * ShipEntitys updateValues method.</p>
 *
 * @author Dynisious 06/10/2015
 * @version 0.0.1
 * @param <E>
 */
public class ShipUpdateable<E extends ShipEntity> extends ShipController<E> {
    public int state; //The current state of this ShipUpdateable.

    /**
     * <p>
     * Creates new ShipUpdateable with the passed values.</p>
     *
     * @param state The initial ShipControl to execute in the array of
     *              ShipControls.
     */
    public ShipUpdateable(final int state) {
        this.state = state;
    }

    @Override
    public void handleCollisionEvent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handlePhysicsObjectUpdateEvent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
