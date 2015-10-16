package space_fighter_test_3d.gameWorld.physics;

import dynutils.events.EventObject;
import dynutils.linkedlist.StrongLinkedListNode;
import dynutils.linkedlist.sorted.TaggedByID;
import dynutils.linkedlist.sorted.WeakLinkedIDListNode;
import java.util.EventListener;
import softEngine3D.matrixes.FPoint3D;
import softEngine3D.matrixes.Point3D;
import softEngine3D.matrixes.TransformationMatrix;
import space_fighter_test_3d.gameWorld.Environment;
import space_fighter_test_3d.gameWorld.events.PhysicsObjectListener;
import space_fighter_test_3d.gameWorld.physics.builders.PhysicsObjectBuilder;
import space_fighter_test_3d.global.Application;
/**
 * <p>
 * A PhysicsObject has a physical presence and is affected by the game world's
 * physics physics.</p>
 *
 * @author Dynisious 16/10/2015
 * @version 0.0.1
 * @param <PhysicsListener> The type of PhysicsListener which listens on this
 *                          PhysicsObject.
 * @param <Enviro>          The type of Environment object passed to this
 *                          PhysicsObject.
 */
public abstract class PhysicsObject<PhysicsListener extends PhysicsObjectListener, Enviro extends Environment>
        extends EventObject<PhysicsListener> implements TaggedByID {
    public final Object valuesLock = new Object(); //An Object to lock on when
    //editing the values of this PhysicsObject.
    private static long nextID = 0; //The next ID for the next PhysicsObject created.
    private static synchronized long getNextID() {
        return nextID++;
    }
    private final long ID = getNextID();
    @Override
    public final long getID() {
        return ID;
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
    protected Point3D[] vertexes; //The vertexes that make up this PhysicsObject.
    public Point3D[] getVertexes() {
        return vertexes;
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
     * @param vertexes        The vertexes that make up this PhysicsObject.
     * @param location        The location of this PhysicsObject.
     * @param rotation        The rotation of this PhysicsObject around each
     *                        axis.
     * @param velocity        the velocity of this PhysicsObject.
     * @param rotationalSpeed The rotational speed of the PhysicsObject around
     *                        each of it's axis.
     */
    protected PhysicsObject(final PhysicsObjectBuilder builder,
                            final double mass, final Point3D[] vertexes,
                            final FPoint3D location, final FPoint3D rotation,
                            final FPoint3D velocity,
                            final FPoint3D rotationalSpeed) {
        this.builder = builder;
        this.mass = mass;
        this.vertexes = vertexes;
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
    private void checkCollisions(final Enviro environment) {
        synchronized (valuesLock) {
            Objects:
            for (WeakLinkedIDListNode<PhysicsObject> node = environment.getObjectList(); node != null; node = node.getNextNode()) {
                final PhysicsObject obj = node.getValue();
                final TransformationMatrix objRot = TransformationMatrix.produceTransMatrix(
                        obj.getRotation(), obj.getLocation().subtraction(
                                location));
                final TransformationMatrix myRot = TransformationMatrix.produceTransMatrix(
                        rotation, new FPoint3D());
                final Point3D[] objVertexes = obj.getVertexes();
                final StrongLinkedListNode<Point3D>[] collectedVertexes = new StrongLinkedListNode[objVertexes.length];
                final Point3D[] myVertexes = new Point3D[vertexes.length];
                for (int i = 0; i < vertexes.length; i++) {
                    myVertexes[i] = myRot.multiplication(vertexes[i]);
                }
                for (int i = 0; i < objVertexes.length; i++) {
                    collectedVertexes[i] = new StrongLinkedListNode<>(
                            objRot.multiplication(objVertexes[i]));
                    new StrongLinkedListNode<>(myVertexes[i])
                            .insertAhead(collectedVertexes[i]);
                    for (int e = 1; e < myVertexes.length; e++) {
                        StrongLinkedListNode<Point3D> vertexNode = collectedVertexes[i].getNextNode();
                        final StrongLinkedListNode<Point3D> vert = new StrongLinkedListNode<>(
                                myVertexes[e]);
                        final double magnituid = vert.getValue().getMagnituid();
                        while (vertexNode != null) {
                            if (vertexNode.getValue().getMagnituid() > magnituid) {
                                vert.insertAhead(vertexNode.getPreviousNode());
                                break;
                            } else if (vertexNode.getNextNode().getValue() == null) {
                                vert.insertAhead(vertexNode);
                            }
                            vertexNode = vertexNode.getNextNode();
                        }
                    }
                }
                for (final StrongLinkedListNode<Point3D> vertex : collectedVertexes) {
                    final Point3D p1 = vertex.getNextNode().getValue();
                    final Point3D p2 = vertex.getNextNode().getNextNode().getValue();
                    final Point3D p3 = vertex.getNextNode().getNextNode().getNextNode().getValue();
                    final double p1MaxAngle = Math.max(p1.angleBetween(p2),
                            p1.angleBetween(p3));
                    final double p2MaxAngle = Math.max(p2.angleBetween(p1),
                            p2.angleBetween(p3));
                    final double p3MaxAngle = Math.max(p3.angleBetween(p1),
                            p3.angleBetween(p2));
                    if (vertex.getValue().angleBetween(p1) < p1MaxAngle /*Within the range of the first point*/
                            && vertex.getValue().angleBetween(p2) < p2MaxAngle /*Within the range of the second point.*/
                            && vertex.getValue().angleBetween(p3) < p3MaxAngle /*Within the range of the third point.*/
                            && p1.addition(p2.addition(p3)).multiplication( /*Within the distance through the center.*/
                                    1 / 3f).getMagnituid() < vertex.getValue().getMagnituid()) { //A collision has occoured.
                        final double combinedMass = mass + obj.getMass();
                        velocity = new FPoint3D(
                                (mass * velocity.x) + (obj.getMass() * obj.getVelocity().x),
                                (mass * velocity.y) + (obj.getMass() * obj.getVelocity().y),
                                (mass * velocity.z) + (obj.getMass() * obj.getVelocity().z)
                        ).multiplication(1 / combinedMass);
                        synchronized (obj.valuesLock) {
                            obj.velocity = velocity.getCopy();
                        }
                        fireCollisionEvent();
                        continue Objects;
                    }
                }
            }
        }
    }

    /**
     * <p>
     * Fired when this PhysicsObject collides with another.</p>
     */
    public void fireCollisionEvent() {
        for (final EventListener listener : getListeners()) {
            ((PhysicsListener) listener).handleCollisionEvent();
        }
    }

    /**
     * <p>
     * Handles the updating of an EntityObject.</p>
     *
     * @param environment The current environment.
     */
    public void firePhysicsObjectUpdateEvent(final Enviro environment) {
        synchronized (valuesLock) {
            checkCollisions(environment);
            location = location.addition(velocity);
            rotation = rotation.addition(rotationalSpeed);
            for (final EventListener l : getListeners()) {
                ((PhysicsListener) l).handlePhysicsObjectUpdateEvent();
            }
            if (Application.debug) {
                System.out.println(this);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("%-18s : ", Thread.currentThread().getName())
                + getBuilder().getTypeName() + getID() + ":-----"
                + "\r\n  " + String.format("%-24s", "mass") + "=" + mass
                + "\r\n  " + String.format("%-24s", "location") + "=" + location
                + "\r\n  " + String.format("%-24s", "rotation") + "=" + rotation
                + "\r\n  " + String.format("%-24s", "velocity") + "=" + velocity
                + "\r\n  " + String.format("%-24s", "rotaional_speed") + "=" + rotationalSpeed;
    }

}
