package space_fighter_test_3d.gameWorld;

import space_fighter_test_3d.global.events.ApplicationEventListener;
import space_fighter_test_3d.global.events.GlobalEventListener;
import space_fighter_test_3d.gameWorld.gameStates.FreeFlightStateModule;
import space_fighter_test_3d.gameWorld.gameStates.GameStateModule;
import space_fighter_test_3d.global.events.GlobalEvents;
import space_fighter_test_3d.global.graphics.GraphicsModule;
import space_fighter_test_3d.logging.ErrorLogger;
import space_fighter_test_3d.logging.EventLogger;
import space_fighter_test_3d.logging.MessageLogger;
/**
 * <p>
 * This Module handles the management of game environments and Entities.</p>
 *
 * @author Dynisious 05/10/2015
 * @version 0.0.1
 */
public final class GameModule
        implements GlobalEventListener, ApplicationEventListener {
    private GraphicsModule graphicsModule;
    private GameStateModule currentState = FreeFlightStateModule.instance; //The current GameStateModule being run.

    /**
     * <p>
     * Creates a GameModule with the passed values.</p>
     *
     * @param graphicsModule The GraphicsModule for the game.
     */
    public GameModule(final GraphicsModule graphicsModule) {
        this.graphicsModule = graphicsModule;
    }

    @Override
    public void handleApplicationClosingEvent(final int reason) {

    }

    @Override
    public void handleGameTickEvent() {
        try {
            graphicsModule.queueRenderable(currentState.getRenderable());
            currentState = currentState.update();
        } catch (final Exception ex) {
            final String message = "ERROR : There was a catastrophic error in the game loop.";
            ErrorLogger.write(message, 1, ex, true);
            EventLogger.write(message, 1, false,
                    "Current state=" + currentState);
            GlobalEvents.fireApplicationClosingEvent(
                    GlobalEvents.AppClose_Break_In_Game_Loop);
        } finally {
            final String message = "GameModule has closed.";
            MessageLogger.write(message, 7, false);
            EventLogger.write(message, 7, false);
        }
    }

}
