package space_fighter_test_3d.gameWorld.gameStates;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import softEngine3D.objects.Object3D;
import space_fighter_test_3d.gameWorld.Environment;
import space_fighter_test_3d.gameWorld.entities.EntityObject;
import space_fighter_test_3d.gameWorld.entities.ships.shipControls.PlayerController;
import space_fighter_test_3d.gameWorld.physics.PhysicsObject;
import space_fighter_test_3d.global.graphics.GraphicsModel;
import space_fighter_test_3d.global.graphics.Renderable;
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

    @Override
    public Renderable getRenderable() {
        final LinkedHashSet<Object3D> object3Ds = new LinkedHashSet<>(
                entityList.size());
        object3Ds.add(null);
        Object3D target = null;
        for (final EntityObject entityObject : entityList) {
            final Object3D object3D = entityObject.getBuilder()
                    .getModel().copy();
            object3D.location = entityObject.getLocation().copy();
            object3D.rotaion = entityObject.getRotation().copy();
            if (entityObject == PlayerController.getInstance().getShip()) //This is the target EntityObject, controlled by the player.
                target = object3D;
            else
                object3Ds.add(object3D);
        }
        final Object3D[] objectArray = object3Ds.toArray(
                new Object3D[object3Ds.size()]);
        objectArray[0] = target;
        return new GraphicsModel(objectArray);
    }

}
