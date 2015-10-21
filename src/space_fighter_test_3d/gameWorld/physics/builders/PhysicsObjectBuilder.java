package space_fighter_test_3d.gameWorld.physics.builders;

import java.util.LinkedList;
import softEngine3D.matrixes.FPoint3D;
import softEngine3D.objects.Object3D;
import softEngine3D.objects.Triangle;
import space_fighter_test_3d.gameWorld.Builder;
import space_fighter_test_3d.gameWorld.physics.PhysicsObject;
import space_fighter_test_3d.gameWorld.physics.geometry.CollisionMesh;
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
    public static final PhysicsObjectBuilder[] getPhysicsObjectBuildersByTypeName(
            final String typeName) {
        final LinkedList<PhysicsObjectBuilder> foundBuilders = new LinkedList<>();
        for (final Builder builder : getAllBuilders()) {
            if (builder instanceof PhysicsObjectBuilder)
                if (((PhysicsObjectBuilder) builder).typeName
                        .equalsIgnoreCase(typeName))
                    foundBuilders.add((PhysicsObjectBuilder) builder);
        }
        return foundBuilders.toArray(
                new PhysicsObjectBuilder[foundBuilders.size()]);
    }
    protected Object3D model = new Object3D(
            new FPoint3D(), new FPoint3D(), new Triangle[]{
                new Triangle(
                        new FPoint3D(0, 0, 50),
                        new FPoint3D(20, 0, 0),
                        new FPoint3D(0, 7.5, 0), false),
                new Triangle(
                        new FPoint3D(0, 0, 50),
                        new FPoint3D(-20, 0, 0),
                        new FPoint3D(0, 7.5, 0), false),
                new Triangle(
                        new FPoint3D(0, 0, 50),
                        new FPoint3D(20, 0, 0),
                        new FPoint3D(0, -7.5, 0), false),
                new Triangle(
                        new FPoint3D(20, 0, 0),
                        new FPoint3D(-20, 0, 0),
                        new FPoint3D(0, 7.5, 0), false),
                new Triangle(
                        new FPoint3D(20, 0, 0),
                        new FPoint3D(-20, 0, 0),
                        new FPoint3D(0, -7.5, 0), false),
                new Triangle(
                        new FPoint3D(0, 0, 50),
                        new FPoint3D(-20, 0, 0),
                        new FPoint3D(0, -7.5, 0), false)
            }, Integer.MAX_VALUE);
    public Object3D getModel() {
        return model;
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
    protected CollisionMesh collisionMesh; //The CollisionMesh which represents the phyiscal object.
    public void setCollisionMesh(final CollisionMesh collisionMesh) {
        this.collisionMesh = collisionMesh;
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
     * @param collisionMesh  The CollisionMesh that make up produced
     *                       PhysicsObjects.
     * @param location       The location of produced PhysicsObjects.
     * @param rotation       The rotation of produced PhysicsObjects around each
     *                       axis.
     * @param velocity       The velocity of produced PhysicsObjects.
     * @param rotaionalSpeed The rotational speed of produced PhysicsObject
     *                       around each of their axis.
     */
    protected PhysicsObjectBuilder(final String typeName, final double mass,
                                   final CollisionMesh collisionMesh,
                                   final FPoint3D location,
                                   final FPoint3D rotation,
                                   final FPoint3D velocity,
                                   final FPoint3D rotaionalSpeed) {
        this.typeName = typeName;
        this.mass = mass;
        this.collisionMesh = collisionMesh;
        this.location = location;
        this.rotation = rotation;
        this.velocity = velocity;
        this.rotationalSpeed = rotaionalSpeed;
    }

}
