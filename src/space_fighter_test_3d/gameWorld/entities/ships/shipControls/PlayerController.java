package space_fighter_test_3d.gameWorld.entities.ships.shipControls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import space_fighter_test_3d.gameWorld.entities.ships.ShipEntity;
import space_fighter_test_3d.gameWorld.entities.ships.events.PlayerControllerEvents;
/**
 * <p>
 * A PlayerController is a ShipController which behaves based on user input.</p>
 *
 * @author Dynisious 07/10/2015
 * @version 0.0.1
 * @param <ShipEntityType> The type of ShipEntity that this PlayerController
 *                         controls.
 */
public final class PlayerController<ShipEntityType extends ShipEntity> extends ShipController<ShipEntityType>
        implements KeyListener {

    /**
     * <p>
     * Creates a new PlayerControlled with the passed values.</p>
     *
     * @param <Type>
     *
     * @return
     */
    public static final <Type extends ShipEntity> PlayerController<Type> initInstance() {
        final PlayerController<Type> controller = new PlayerController<>();
        PlayerControllerEvents.getInstance().addListener(controller);
        PlayerControllerEvents.setAsKeyListener(controller);
        return controller;
    }

    private PlayerController() {
    }

    public static Object[][] keys = new Object[][]{
        {KeyEvent.VK_W, true},
        {KeyEvent.VK_S, true},
        {KeyEvent.VK_A, true},
        {KeyEvent.VK_D, true},
        {KeyEvent.VK_SHIFT, true},
        {KeyEvent.VK_CONTROL, true},
        {KeyEvent.VK_NUMPAD6, true},
        {KeyEvent.VK_NUMPAD4, true},
        {KeyEvent.VK_NUMPAD2, true},
        {KeyEvent.VK_NUMPAD8, true},
        {KeyEvent.VK_NUMPAD9, true},
        {KeyEvent.VK_NUMPAD7, true}
    };

    @Override
    public void keyPressed(final KeyEvent e) {
        if (getShip() != null) {
            e.consume();
            synchronized (getShip().valuesLock) {
                if ((e.getKeyCode() == (Integer) keys[0][0]) && (Boolean) keys[0][1]) {
                    keys[0][1] = false;
                    getShip().getLinearForcesIncrement().z += getShip().getMaxLinearForcesIncrement().z;
                } else if ((e.getKeyCode() == (Integer) keys[1][0]) && (Boolean) keys[1][1]) {
                    keys[1][1] = false;
                    getShip().getLinearForcesIncrement().z -= getShip().getMaxLinearForcesIncrement().z;
                } else if ((e.getKeyCode() == (Integer) keys[2][0]) && (Boolean) keys[2][1]) {
                    keys[2][1] = false;
                    getShip().getLinearForcesIncrement().x -= getShip().getMaxLinearForcesIncrement().x;
                } else if ((e.getKeyCode() == (Integer) keys[3][0]) && (Boolean) keys[3][1]) {
                    keys[3][1] = false;
                    getShip().getLinearForcesIncrement().x += getShip().getMaxLinearForcesIncrement().x;
                } else if ((e.getKeyCode() == (Integer) keys[4][0]) && (Boolean) keys[4][1]) {
                    keys[4][1] = false;
                    getShip().getLinearForcesIncrement().y += getShip().getMaxLinearForcesIncrement().y;
                } else if ((e.getKeyCode() == (Integer) keys[5][0]) && (Boolean) keys[5][1]) {
                    keys[5][1] = false;
                    getShip().getLinearForcesIncrement().y -= getShip().getMaxLinearForcesIncrement().y;
                } else if ((e.getKeyCode() == (Integer) keys[6][0]) && (Boolean) keys[6][1]) {
                    keys[6][1] = false;
                    getShip().getTorquesIncrement().y += getShip().getMaxTorquesIncrement().y;
                } else if ((e.getKeyCode() == (Integer) keys[7][0]) && (Boolean) keys[7][1]) {
                    keys[7][1] = false;
                    getShip().getTorquesIncrement().y -= getShip().getMaxTorquesIncrement().y;
                } else if ((e.getKeyCode() == (Integer) keys[8][0]) && (Boolean) keys[8][1]) {
                    keys[8][1] = false;
                    getShip().getTorquesIncrement().x -= getShip().getMaxTorquesIncrement().x;
                } else if ((e.getKeyCode() == (Integer) keys[9][0]) && (Boolean) keys[9][1]) {
                    keys[9][1] = false;
                    getShip().getTorquesIncrement().x += getShip().getMaxTorquesIncrement().x;
                } else if ((e.getKeyCode() == (Integer) keys[10][0]) && (Boolean) keys[10][1]) {
                    keys[10][1] = false;
                    getShip().getTorquesIncrement().z += getShip().getMaxTorquesIncrement().z;
                } else if ((e.getKeyCode() == (Integer) keys[11][0]) && (Boolean) keys[11][1]) {
                    keys[11][1] = false;
                    getShip().getTorquesIncrement().z -= getShip().getMaxTorquesIncrement().z;
                }
            }
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        if (getShip() != null) {
            e.consume();
            synchronized (getShip().valuesLock) {
                if (e.getKeyCode() == (Integer) keys[0][0]) {
                    keys[0][1] = true;
                    getShip().getLinearForcesIncrement().z -= getShip().getMaxLinearForcesIncrement().z;
                } else if (e.getKeyCode() == (Integer) keys[1][0]) {
                    keys[1][1] = true;
                    getShip().getLinearForcesIncrement().z += getShip().getMaxLinearForcesIncrement().z;
                } else if (e.getKeyCode() == (Integer) keys[2][0]) {
                    keys[2][1] = true;
                    getShip().getLinearForcesIncrement().x += getShip().getMaxLinearForcesIncrement().x;
                } else if (e.getKeyCode() == (Integer) keys[3][0]) {
                    keys[3][1] = true;
                    getShip().getLinearForcesIncrement().x -= getShip().getMaxLinearForcesIncrement().x;
                } else if (e.getKeyCode() == (Integer) keys[4][0]) {
                    keys[4][1] = true;
                    getShip().getLinearForcesIncrement().y -= getShip().getMaxLinearForcesIncrement().y;
                } else if (e.getKeyCode() == (Integer) keys[5][0]) {
                    keys[5][1] = true;
                    getShip().getLinearForcesIncrement().y += getShip().getMaxLinearForcesIncrement().y;
                } else if (e.getKeyCode() == (Integer) keys[6][0]) {
                    keys[6][1] = true;
                    getShip().getTorquesIncrement().y -= getShip().getMaxTorquesIncrement().y;
                } else if (e.getKeyCode() == (Integer) keys[7][0]) {
                    keys[7][1] = true;
                    getShip().getTorquesIncrement().y += getShip().getMaxTorquesIncrement().y;
                } else if (e.getKeyCode() == (Integer) keys[8][0]) {
                    keys[8][1] = true;
                    getShip().getTorquesIncrement().x += getShip().getMaxTorquesIncrement().x;
                } else if (e.getKeyCode() == (Integer) keys[9][0]) {
                    keys[9][1] = true;
                    getShip().getTorquesIncrement().x -= getShip().getMaxTorquesIncrement().x;
                } else if (e.getKeyCode() == (Integer) keys[10][0]) {
                    keys[10][1] = true;
                    getShip().getTorquesIncrement().z -= getShip().getMaxTorquesIncrement().z;
                } else if (e.getKeyCode() == (Integer) keys[11][0]) {
                    keys[11][1] = true;
                    getShip().getTorquesIncrement().z += getShip().getMaxTorquesIncrement().z;
                }
            }
        }
    }

    public void handleMainFormSetEvent() {
        PlayerControllerEvents.setAsKeyListener(this);
    }

    @Override
    public void keyTyped(final KeyEvent e) {

    }

    @Override
    public void handleCollisionEvent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handlePhysicsObjectUpdateEvent() {
        {
            if (getShip() != null) {
                synchronized (getShip().valuesLock) {
                    if ((Boolean) keys[0][1] && (Boolean) keys[1][1]) {
                        if (Math.abs(getShip().getLinearForces().z) > Math.abs(
                                getShip().getLinearForcesIncrement().z)) {
                            getShip().getLinearForcesIncrement().z = getShip().getLinearForces().z > 0
                                    ? -getShip().getMaxLinearForcesIncrement().z
                                    : getShip().getMaxLinearForcesIncrement().z;
                        } else {
                            getShip().getLinearForcesIncrement().z = getShip().getLinearForces().z = 0;
                        }
                    }
                    if ((Boolean) keys[2][1] && (Boolean) keys[3][1]) {
                        if (Math.abs(getShip().getLinearForces().x) > Math.abs(
                                getShip().getLinearForcesIncrement().x)) {
                            getShip().getLinearForcesIncrement().x = getShip().getLinearForces().x > 0
                                    ? -getShip().getMaxLinearForcesIncrement().x
                                    : getShip().getMaxLinearForcesIncrement().x;
                        } else {
                            getShip().getLinearForcesIncrement().x = getShip().getLinearForces().x = 0;
                        }
                    }
                    if ((Boolean) keys[4][1] && (Boolean) keys[5][1]) {
                        if (Math.abs(getShip().getLinearForces().y) > Math.abs(
                                getShip().getLinearForcesIncrement().y)) {
                            getShip().getLinearForcesIncrement().y = getShip().getLinearForces().y > 0
                                    ? -getShip().getMaxLinearForcesIncrement().y
                                    : getShip().getMaxLinearForcesIncrement().y;
                        } else {
                            getShip().getLinearForcesIncrement().y = getShip().getLinearForces().y = 0;
                        }
                    }
                    if ((Boolean) keys[6][1] && (Boolean) keys[7][1]) {
                        if (Math.abs(getShip().getTorques().y) > Math.abs(
                                getShip().getTorquesIncrement().y)) {
                            getShip().getTorquesIncrement().y = getShip().getTorques().y > 0
                                    ? -getShip().getMaxTorquesIncrement().y
                                    : getShip().getMaxTorquesIncrement().y;
                        } else {
                            getShip().getTorquesIncrement().y = getShip().getTorques().y = 0;
                        }
                    }
                    if ((Boolean) keys[8][1] && (Boolean) keys[9][1]) {
                        if (Math.abs(getShip().getTorques().x) > Math.abs(
                                getShip().getTorquesIncrement().x)) {
                            getShip().getTorquesIncrement().x = getShip().getTorques().x > 0
                                    ? -getShip().getMaxTorquesIncrement().x
                                    : getShip().getMaxTorquesIncrement().x;
                        } else {
                            getShip().getTorquesIncrement().x = getShip().getTorques().x = 0;
                        }
                    }
                    if ((Boolean) keys[10][1] && (Boolean) keys[11][1]) {
                        if (Math.abs(getShip().getTorques().z) > Math.abs(
                                getShip().getTorquesIncrement().z)) {
                            getShip().getTorquesIncrement().z = getShip().getTorques().z > 0
                                    ? -getShip().getMaxTorquesIncrement().z
                                    : getShip().getMaxTorquesIncrement().z;
                        } else {
                            getShip().getTorquesIncrement().z = getShip().getTorques().z = 0;
                        }
                    }
                }
            }
        }
    }

}
