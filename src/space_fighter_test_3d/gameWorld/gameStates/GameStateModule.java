package space_fighter_test_3d.gameWorld.gameStates;

import dynutils.linkedlist.sorted.TaggedByID;
import dynutils.linkedlist.sorted.StrongLinkedIDListNode;
/**
 * <p>
 * A GameEnivronment is an object which is a state in the GameModule's state
 * machine.</p>
 *
 * @author Dynisious 06/10/2015
 * @version 0.0.1
 */
public abstract class GameStateModule implements TaggedByID {
    private static int nextID = 0;
    protected static final synchronized int getNextID() {
        return nextID++;
    }
    private static StrongLinkedIDListNode<GameStateModule> allGameStateModules;
    private static StrongLinkedIDListNode<GameStateModule> lastModule;
    public static StrongLinkedIDListNode<GameStateModule> getAllGameStateModules() {
        return allGameStateModules;
    }
    public static void addNewGameStateModule(
            final GameStateModule gameStateModule) {
        if (allGameStateModules != null) {
            new StrongLinkedIDListNode<>(gameStateModule).insertAhead(
                    lastModule);
        } else {
            lastModule = allGameStateModules = new StrongLinkedIDListNode<>(
                    gameStateModule);
        }
    }
    public static final GameStateModule getGameState(final int stateID) {
        if (allGameStateModules != null) {
            StrongLinkedIDListNode<GameStateModule> node = allGameStateModules;
            while (node.getValueID() != stateID && node.getNextNode() != null) {
                node = node.getNextNode();
            }
            if (node.getValueID() == stateID) {
                return node.getValue();
            }
        }
        return null;
    }

    /**
     * <p>
     * Updates this GameStateModule.</p>
     *
     * @return The code of the next GameStateModule to use.
     */
    public abstract int update();

}
