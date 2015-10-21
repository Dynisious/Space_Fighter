package space_fighter_test_3d.gameWorld.physics;

import dynutils.events.EventObject;
import java.util.EventListener;
import softEngine3D.matrixes.FPoint3D;
import space_fighter_test_3d.gameWorld.Environment;
import space_fighter_test_3d.gameWorld.events.PhysicsObjectListener;
import space_fighter_test_3d.gameWorld.physics.builders.PhysicsObjectBuilder;
import space_fighter_test_3d.gameWorld.physics.geometry.CollisionMesh;
import space_fighter_test_3d.global.Application;
/**
 * <p>
 * A PhysicsObject has a physical presence and is affected by the game world's
 * physics physics.</p>
 *
 * @author Dynisious 16/10/2015
 * @version 0.1.1
 */
public abstract class PhysicsObject {
    public final Object valuesLock = new Object(); //An Object to lock on when
    //editing the values of this PhysicsObject.
    private final EventObject<PhysicsObjectListener> events = new EventObject<>();
    public <T extends PhysicsObjectListener> void addListener(final T listener) {
        events.addListener(listener);
    }
    public <T extends PhysicsObjectListener> boolean removeListener(
            final T listener) {
        return events.removeListener(listener);
    }
    public EventListener[] getListeners() {
        return events.getListeners();
    }
    private final PhysicsObjectBuilder builder; //The PhysicsObjectBuilder which
    //produced this PhysicsObject.
    public <T extends PhysicsObjectBuilder> T getBuilder() {
        return (T) builder;
    }
    protected double mass; //The mass of this PhysicsObject.
    public double getMass() {
        return mass;
    }
    protected CollisionMesh collisionMesh; //The CollisionMesh that represents the phyiscal object.
    public CollisionMesh getCollisionMesh() {
        return collisionMesh;
    }
    protected FPoint3D location; //The location of the PhysicsObject in 3D space.
    public FPoint3D getLocation() {
        return location;
    }
    protected FPoint3D rotation; //The orientation of the PhysicsObject around each axis in world space.
    public FPoint3D getRotation() {
        return rotation;
    }
    protected FPoint3D velocity; //The velocity of this PhysicsObject along each axis.
    public FPoint3D getVelocity() {
        return velocity;
    }
    protected FPoint3D rotationalSpeed; //The rotational speed of the PhysicsObject around each of it's axis.
    public FPoint3D getRotationalSpeed() {
        return rotationalSpeed;
    }

    /**
     * <p>
     * Creates a new PhysicsObject with the passed values.</p>
     *
     * @param builder         The PhysicsObjectBuilder which produced this
     *                        PhysicsObject.
     * @param mass            The mass of this PhysicsObject.
     * @param collisionMesh   The CollisionMesh which represents this physical
     *                        object.
     * @param location        The location of this PhysicsObject.
     * @param rotation        The rotation of this PhysicsObject around each
     *                        axis.
     * @param velocity        the velocity of this PhysicsObject.
     * @param rotationalSpeed The rotational speed of the PhysicsObject around
     *                        each of it's axis.
     */
    protected PhysicsObject(final PhysicsObjectBuilder builder,
                            final double mass, final CollisionMesh collisionMesh,
                            final FPoint3D location, final FPoint3D rotation,
                            final FPoint3D velocity,
                            final FPoint3D rotationalSpeed) {
        this.builder = builder;
        this.mass = mass;
        this.collisionMesh = collisionMesh;
        this.location = location;
        this.rotation = rotation;
        this.velocity = velocity;
        this.rotationalSpeed = rotationalSpeed;
    }

    /**
     * <p>
     * Checks whether this PhysicsObject is colliding with any other
     * PhysicsObjects in the passed environment.</p>
     *
     * @param <T>         The type of Object stored in the Environment.
     * @param environment The current environment.
     */
    private void checkCollisions(final Environment environment) {
        for (final PhysicsObject physicsObject : environment.objectList)
            if (physicsObject.location.subtraction(location).getMagnituid()
                    < (collisionMesh.getMaxCollisionDistance()
                    + physicsObject.getCollisionMesh().getMaxCollisionDistance())) //It is possible for the two meshes to be colliding.
                if (collisionMesh.checkCollision(physicsObject
                        .getCollisionMesh())) //The two meshes are colliding.
                    fireCollisionEvent();
    }

    /**
     * <p>
     * Fired when this PhysicsObject collides with another.</p>
     */
    public void fireCollisionEvent() {
        for (final EventListener listener : events.getListeners()) {
            ((PhysicsObjectListener) listener).handleCollisionEvent();
        }
    }

    /**
     * <p>
     * Handles the updating of an EntityObject.</p>
     *
     * @param environment The current environment.
     */
    public void firePhysicsObjectUpdateEvent(final Environment environment) {
        synchronized (valuesLock) {
            checkCollisions(environment);
            location = location.addition(velocity);
            rotation = rotation.addition(rotationalSpeed);
            for (final EventListener l : events.getListeners()) {
                ((PhysicsObjectListener) l).handlePhysicsObjectUpdateEvent();
            }
            if (Application.debug) {
                System.out.println(this);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("%-18s : ", Thread.currentThread().getName())
                + getBuilder().getClass().getName() + ":" + getBuilder()
                .getTypeName() + ":-----"
                + "\r\n  " + String.format("%-24s", "mass") + "=" + mass
                + "\r\n  " + String.format("%-24s", "location") + "=" + location
                + "\r\n  " + String.format("%-24s", "rotation") + "=" + rotation
                + "\r\n  " + String.format("%-24s", "velocity") + "=" + velocity
                + "\r\n  " + String.format("%-24s", "rotaional_speed") + "=" + rotationalSpeed;
    }

}
