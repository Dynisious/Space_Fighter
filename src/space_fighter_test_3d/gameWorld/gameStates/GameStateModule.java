package space_fighter_test_3d.gameWorld.gameStates;

import space_fighter_test_3d.global.graphics.Renderable;
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

    /**
     * @return The Renderable which represents this GameStateModule.
     */
    public abstract Renderable getRenderable();

}
