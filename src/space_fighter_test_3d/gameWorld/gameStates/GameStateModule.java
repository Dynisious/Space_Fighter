package space_fighter_test_3d.gameWorld.gameStates;
/**
 * <p>
 * A GameEnivronment is an object which is a state in the GameModule's state
 * machine.</p>
 *
 * @author Dynisious 06/10/2015
 * @version 0.0.1
 */
public abstract class GameStateModule {

    protected GameStateModule() {
    }

    /**
     * <p>
     * Updates this GameStateModule.</p>
     *
     * @return The code of the next GameStateModule to use.
     */
    public abstract GameStateModule update();

}
