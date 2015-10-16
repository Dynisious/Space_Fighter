package space_fighter_test_3d.gameWorld.entities.builders;

import softEngine3D.matrixes.FPoint3D;
import softEngine3D.matrixes.Point3D;
import space_fighter_test_3d.gameWorld.entities.EntityObject;
import space_fighter_test_3d.gameWorld.physics.builders.PhysicsObjectBuilder;
/**
 * <p>
 * Base class for EntityBuilders which are capable of producing different types
 * of EntityObjects.</p>
 *
 * @author Dynisious 06/10/2015
 * @version 0.0.1
 * @param <Entity> The type of EntityObject being built from this
 *                 EntityObjectBuilder.
 * @param <Arg>    The argument type passed to the builders build function.
 */
public abstract class EntityObjectBuilder<Entity extends EntityObject, Arg>
        extends PhysicsObjectBuilder<Entity, Arg> {
    protected FPoint3D linearForces; //The current linear forces on this
    //EntityObject along each axis.
    public void setLinearForces(final FPoint3D linearForces) {
        this.linearForces = linearForces;
    }
    protected FPoint3D maxMagnituidLinearForces; //The maximum magnituid of
    //linearForces.
    public void setMaxMagnituidLinearForces(
            final FPoint3D maxMagnituidLinearForces) {
        this.maxMagnituidLinearForces = maxMagnituidLinearForces;
    }
    protected FPoint3D torques; //The torques on this EntityObject relative to the ship.
    public void setTorques(final FPoint3D torques) {
        this.torques = torques;
    }
    protected FPoint3D maxMagnituidTorques; //The maximum magnituid of torques.
    public void setMaxMagnituidTorques(final FPoint3D maxMagnituidTorques) {
        this.maxMagnituidTorques = maxMagnituidTorques;
    }

    /**
     * <p>
     * Creates a new EntityObjectBuilder with the passed values.</p>
     *
     * @param mass                     The mass of produced EntityObjects.
     * @param vertexes                 The vertexes which make up produced
     *                                 EntityObjects.
     * @param location                 The location of produced EntiyObject in
     *                                 3D space.
     * @param rotation                 The rotation around each axis of produced
     *                                 EvntityObject in 3D space.
     * @param velocity                 The velocity of produced EntityObjects.
     * @param typeName                 The name of this type of EntityObject.
     * @param rotationalSpeed          The rotational speed of produced
     *                                 EntityObject around each of their axis.
     * @param linearForces             The linear forces acting on produced
     *                                 EntityObjects relative to produced
     *                                 EntityObjects.
     * @param maxMagnituidLinearForces The maximum values for linearForces.
     * @param torques                  The torque forces acting on produced
     *                                 EntityObjects relative to produced
     *                                 EntityObjects.
     * @param maxMagnituidTorques      The maximum values for torques.
     */
    protected EntityObjectBuilder(final String typeName, final double mass,
                                  final Point3D[] vertexes,
                                  final FPoint3D location,
                                  final FPoint3D rotation,
                                  final FPoint3D velocity,
                                  final FPoint3D rotationalSpeed,
                                  final FPoint3D linearForces,
                                  final FPoint3D maxMagnituidLinearForces,
                                  final FPoint3D torques,
                                  final FPoint3D maxMagnituidTorques) {
        super(typeName, mass, vertexes, location, rotation, velocity,
                rotationalSpeed);
        this.linearForces = linearForces;
        this.maxMagnituidLinearForces = maxMagnituidLinearForces;
        this.torques = torques;
        this.maxMagnituidTorques = maxMagnituidTorques;
    }

}
