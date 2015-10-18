package space_fighter_test_3d.gameWorld;

import space_fighter_test_3d.gameWorld.physics.PhysicsObject;
/**
 * <p>
 * An Environment object contains information relating to a game space at a
 * particular instance.</p>
 *
 * @author Dynisious 16/10/2015
 * @version 0.0.1
 * @param <ObjectType> The type of objects stored inside this Environment.
 */
public class Environment {
    public final PhysicsObject[] objectList;

    /**
     * <p>
     * Creates a new Environment instance with the passed values.</p>
     *
     * @param objectList The PhysicsObjects in this Environment.
     */
    public Environment(final PhysicsObject[] objectList) {
        this.objectList = objectList;
    }

}
