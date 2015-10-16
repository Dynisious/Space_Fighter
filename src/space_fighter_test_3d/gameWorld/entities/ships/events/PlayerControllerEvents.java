package space_fighter_test_3d.gameWorld.entities.ships.events;

import java.awt.Component;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.EventListener;
import space_fighter_test_3d.gameWorld.entities.ships.shipControls.PlayerController;
import space_fighter_test_3d.global.MainForm;
import dynutils.events.EventObject;
/**
 * <p>
 * An object which fires events for all PlayerController objects.</p>
 *
 * @author Dynisious 15/10/2015
 * @version 0.0.1
 */
public final class PlayerControllerEvents extends EventObject<PlayerController> {
    private static final Object mainFormLock = new Object(); //Locks access to mainForm.
    private static Component mainForm; //All KeyEvents come from here.
    public static void removeListenerFromMainForm(final KeyListener listener) {
        synchronized (mainFormLock) {
            if (mainForm != null) {
                mainForm.removeKeyListener(listener);
            }
        }
    }
    private static PlayerControllerEvents instance;
    public static PlayerControllerEvents getInstance() {
        if (instance == null) {
            instance = new PlayerControllerEvents();
        }
        return instance;
    }
    /**
     * <p>
     * Sets the MainForm to get KeyEvents from.</p>
     *
     * @param mainForm The new MainForm to provide KeyEvents.
     */
    public static void setMainForm(final MainForm mainForm) {
        synchronized (mainFormLock) {
            if (PlayerControllerEvents.mainForm != null) {
                for (final KeyListener listener : PlayerControllerEvents.mainForm.getKeyListeners()) {
                    PlayerControllerEvents.mainForm.removeKeyListener(listener);
                }
            }
            PlayerControllerEvents.mainForm = mainForm;
        }
        fireMainFormSetEvent();
    }
    /**
     * <p>
     * Adds the passed PlayerController as a KeyListener on the MainForm if it
     * is not null and is not already a KeyListener on the MainForm.</p>
     *
     * @param playerController The PlayerController to add to the MainForm.
     */
    public static void setAsKeyListener(final PlayerController playerController) {
        if (playerController != null) {
            synchronized (mainFormLock) {
                if (mainForm != null) {
                    if (!Arrays.asList(mainForm.getKeyListeners())
                            .contains(playerController)) { //This PlayerController
                        //is not already listening on the mainForm.
                        mainForm.addKeyListener(playerController);
                    }
                }
            }
        }
    }

    public static void fireMainFormSetEvent() {
        for (final EventListener p : getInstance().getListeners()) {
            ((PlayerController) p).handleMainFormSetEvent();
        }
    }

}
