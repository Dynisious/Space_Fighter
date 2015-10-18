package space_fighter_test_3d.gameWorld.gameStates;

import java.util.LinkedList;
import space_fighter_test_3d.gameWorld.Environment;
import space_fighter_test_3d.gameWorld.entities.EntityObject;
import space_fighter_test_3d.gameWorld.physics.PhysicsObject;
/**
 * <p>
 * Free flight mode with limited obstacles or other entities.</p>
 *
 * @author Dynisious 06/10/2015
 * @version 0.0.1
 */
public class FreeFlightStateModule extends GameStateModule {
    public static FreeFlightStateModule instance; //The instance of FreeFlightStateModule for this application.
    public final LinkedList<EntityObject> entityList;

    public FreeFlightStateModule(final LinkedList<EntityObject> entityList) {
        this.entityList = entityList;
    }

    public final LinkedList<EntityObject> updateList = new LinkedList<>();

    @Override
    public GameStateModule update() {
        updateList.addAll(entityList);
        while (!updateList.isEmpty()) {
            updateList.pop().firePhysicsObjectUpdateEvent(
                    new Environment(entityList.toArray(
                                    new PhysicsObject[entityList.size()])));
        }
        return instance;
    }

}
