package space_fighter_test_3d.gameWorld.physics.builders;

import dynutils.linkedlist.sorted.StrongLinkedIDListNode;
import java.util.ArrayList;
import softEngine3D.matrixes.FPoint3D;
import softEngine3D.matrixes.Point3D;
import space_fighter_test_3d.gameWorld.Builder;
import space_fighter_test_3d.gameWorld.entities.builders.EntityObjectBuilder;
import space_fighter_test_3d.gameWorld.physics.PhysicsObject;
/**
 * <p>
 * A Builder which constructs PhysicsObjects.</p>
 *
 * @author Dynisious 16/10/2015
 * @version 0.0.1
 * @param <Physics> The type of PhysicsObject built by this
 *                  PhysicsObjectBuilder.
 * @param <Arg>     The type of Arguments passed to this Builders build
 *                  function.
 */
public abstract class PhysicsObjectBuilder<Physics extends PhysicsObject, Arg>
        extends Builder<Physics, Arg> {
    private static int nextID = 0;
    private static synchronized int getNextID() {
        return nextID++;
    }
    private final int ID = getNextID(); //The individual identifying code of
    @Override
    public final long getID() {
        return ID;
    }
    public static final PhysicsObjectBuilder[] getPhysicsObjectBuildersByTypeName(
            final String typeName) {
        final ArrayList<EntityObjectBuilder> foundBuilders = new ArrayList<>();
        StrongLinkedIDListNode node = allBuilders;
        while (node != null) {
            if (EntityObjectBuilder.class.isInstance(node.getValue())) {
                if (((EntityObjectBuilder) node.getValue()).getTypeName()
                        .equalsIgnoreCase(typeName)) {
                    foundBuilders.add((EntityObjectBuilder) node.getValue());
                }
            }
            node = (StrongLinkedIDListNode) node.getNextNode();
        }
        return foundBuilders.toArray(
                new EntityObjectBuilder[foundBuilders.size()]);
    }
    protected String typeName;
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }
    protected double mass; //The mass of this PhysicsObject.
    public void setMass(final double mass) {
        this.mass = mass;
    }
    protected Point3D[] vertexes; //The vertexes that make up this PhysicsObject.
    public void setVertexes(final Point3D[] vertexes) {
        this.vertexes = vertexes;
    }
    protected FPoint3D location; //The location of the PhysicsObject in 3D space.
    public void setLocation(final FPoint3D location) {
        this.location = location;
    }
    protected FPoint3D rotation; //The orientation of the PhysicsObject around each axis in world space.
    public void setRotation(final FPoint3D rotation) {
        this.rotation = rotation;
    }
    protected FPoint3D velocity; //The velocity of this PhysicsObject along each axis.
    public void setVelocity(final FPoint3D velocity) {
        this.velocity = velocity;
    }
    protected FPoint3D rotationalSpeed; //The rotational speed of the PhysicsObject around each of it's axis.
    public void setRotationalSpeed(final FPoint3D rotationalSpeed) {
        this.rotationalSpeed = rotationalSpeed;
    }

    /**
     * <p>
     * Creates a new PhysicsObjectBuilder with the passed values.</p>
     *
     * @param typeName       The name of this type of PhysicsObject.
     * @param mass           The mass of produced PhysicsObjects.
     * @param vertexes       The vertexes that make up produced PhysicsObjects.
     * @param location       The location of produced PhysicsObjects.
     * @param rotation       The rotation of produced PhysicsObjects around each
     *                       axis.
     * @param velocity       The velocity of produced PhysicsObjects.
     * @param rotaionalSpeed The rotational speed of produced PhysicsObject
     *                       around each of their axis.
     */
    protected PhysicsObjectBuilder(final String typeName, final double mass,
                                   final Point3D[] vertexes,
                                   final FPoint3D location,
                                   final FPoint3D rotation,
                                   final FPoint3D velocity,
                                   final FPoint3D rotaionalSpeed) {
        this.typeName = typeName;
        this.mass = mass;
        this.vertexes = vertexes;
        this.location = location;
        this.rotation = rotation;
        this.velocity = velocity;
        this.rotationalSpeed = rotaionalSpeed;
    }

}
