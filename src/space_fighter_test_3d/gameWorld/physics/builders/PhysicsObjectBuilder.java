package space_fighter_test_3d.gameWorld.physics.builders;

import java.util.ArrayList;
import softEngine3D.matrixes.FPoint3D;
import softEngine3D.objects.Triangle;
import space_fighter_test_3d.gameWorld.Builder;
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
    public static final PhysicsObjectBuilder[] getPhysicsObjectBuildersByTypeName(
            final String typeName) {
        final ArrayList<PhysicsObjectBuilder> foundBuilders = new ArrayList<>();
        for (final Builder builder : getAllBuilders()) {
            if (builder instanceof PhysicsObjectBuilder)
                foundBuilders.add((PhysicsObjectBuilder) builder);
        }
        return foundBuilders.toArray(
                new PhysicsObjectBuilder[foundBuilders.size()]);
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
    protected Triangle[] triangles; //The Triangles that make up this PhysicsObject.
    public void setTriangles(Triangle[] triangles) {
        this.triangles = triangles;
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
     * @param triangles      The triangles that make up produced PhysicsObjects.
     * @param location       The location of produced PhysicsObjects.
     * @param rotation       The rotation of produced PhysicsObjects around each
     *                       axis.
     * @param velocity       The velocity of produced PhysicsObjects.
     * @param rotaionalSpeed The rotational speed of produced PhysicsObject
     *                       around each of their axis.
     */
    protected PhysicsObjectBuilder(final String typeName, final double mass,
                                   final Triangle[] triangles,
                                   final FPoint3D location,
                                   final FPoint3D rotation,
                                   final FPoint3D velocity,
                                   final FPoint3D rotaionalSpeed) {
        this.typeName = typeName;
        this.mass = mass;
        this.triangles = triangles;
        this.location = location;
        this.rotation = rotation;
        this.velocity = velocity;
        this.rotationalSpeed = rotaionalSpeed;
    }

}
