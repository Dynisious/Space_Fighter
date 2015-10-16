package space_fighter_test_3d.gameWorld.entities;

import space_fighter_test_3d.gameWorld.entities.ships.events.EntityEventListener;
import softEngine3D.matrixes.FPoint3D;
import softEngine3D.matrixes.Point3D;
import softEngine3D.matrixes.TransformationMatrix;
import space_fighter_test_3d.gameWorld.Environment;
import space_fighter_test_3d.gameWorld.entities.builders.EntityObjectBuilder;
import space_fighter_test_3d.gameWorld.physics.PhysicsObject;
/**
 * <p>
 * An EntityObject is the base class of any object which is meant to exist in
 * the game world.</p>
 *
 * @author Dynisious 06/10/2015
 * @version 0.0.1
 * @param <EntityListener> The type of EntityEventListener on this EntityObject.
 * @param <Enviro>         The type of Environment which is used in update
 *                         events.
 */
public abstract class EntityObject<EntityListener extends EntityEventListener, Enviro extends Environment>
        extends PhysicsObject<EntityListener, Enviro> {
    protected FPoint3D linearForces; //The current linear forces on this
    //EntityObject along each axis.
    public FPoint3D getLinearForces() {
        return linearForces;
    }
    protected FPoint3D maxMagnituidLinearForces; //The maximum magnituid of
    //linearForces.
    public FPoint3D getMaxMagnituidLinearForces() {
        return maxMagnituidLinearForces;
    }
    protected FPoint3D torques; //The torques on this EntityObject relative to the ship.
    public FPoint3D getTorques() {
        return torques;
    }
    protected FPoint3D maxMagnituidTorques; //The maximum magnituid of torques.
    public FPoint3D getMaxMagnituidTorques() {
        return maxMagnituidTorques;
    }

    /**
     * <p>
     * Creates a new EntityObject with the passed values.</p>
     *
     * @param builder                  The EntityObjectBuilder which produced
     *                                 this EntityObject.
     * @param mass                     The mass of this EntityObject.
     * @param vertexes                 The vertexes which make up this
     *                                 EntityObject.
     * @param location                 The location of this EntiyObject in 3D
     *                                 space.
     * @param rotation                 The rotation around each axis of this
     *                                 EvntityObject in 3D space.
     * @param rotationalSpeed          The rotational speed of the EntityObject
     *                                 around each of it's axis.
     * @param velocity                 The velocity of this EntityObject.
     * @param linearForces             The linear forces acting on this
     *                                 EntityObject relative to this EntityObject.
     * @param maxMagnituidLinearForces The maximum values for linearForces.
     * @param torques                  The torque forces acting on this
     *                                 EntityObject relative to this
     *                                 EntityObject.
     * @param maxMagnituidTorques      The maximum values for torques.
     */
    protected EntityObject(final EntityObjectBuilder builder, final double mass,
                           final Point3D[] vertexes, final FPoint3D location,
                           final FPoint3D rotation,
                           final FPoint3D rotationalSpeed,
                           final FPoint3D velocity, final FPoint3D linearForces,
                           final FPoint3D maxMagnituidLinearForces,
                           final FPoint3D torques,
                           final FPoint3D maxMagnituidTorques) {
        super(builder, mass, vertexes, location, rotation, velocity,
                rotationalSpeed);
        this.linearForces = linearForces;
        this.maxMagnituidLinearForces = maxMagnituidLinearForces;
        this.torques = torques;
        this.maxMagnituidTorques = maxMagnituidTorques;
    }
    @Override
    public void firePhysicsObjectUpdateEvent(Enviro environment) {
        synchronized (valuesLock) {
            final TransformationMatrix rot = TransformationMatrix.produceTransMatrix(
                    rotation, new FPoint3D());
            velocity = velocity.addition(rot.multiplication(
                    linearForces.multiplication(1 / mass)));
            rotationalSpeed = rotationalSpeed.addition(
                    rot.multiplication(torques.multiplication(1 / mass)));
            super.firePhysicsObjectUpdateEvent(environment);
        }
    }

    @Override
    public String toString() {
        return super.toString()
                + "\r\n  " + String.format("%-24s", "linear_forces") + "=" + linearForces
                + "\r\n  " + String.format("%-24s", "torques") + "=" + torques;
    }

}
