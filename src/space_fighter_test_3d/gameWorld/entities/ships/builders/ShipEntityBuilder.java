package space_fighter_test_3d.gameWorld.entities.ships.builders;

import softEngine3D.matrixes.FPoint3D;
import space_fighter_test_3d.gameWorld.entities.builders.EntityObjectBuilder;
import space_fighter_test_3d.gameWorld.entities.ships.shipControls.ShipController;
import space_fighter_test_3d.gameWorld.entities.ships.ShipEntity;
import space_fighter_test_3d.gameWorld.physics.geometry.CollisionMesh;
/**
 * <p>
 * A builder responsible for building ShipEntity object.</p>
 *
 * @author Dynisious 06/10/2015
 * @version 0.0.1
 * @param <Ship>       The type of ShipEntity this ShipEntityBuilder produces.
 * @param <Controller> The Type of ShipControllers passed to the ShipEntitys
 *                     built by
 *                     this Builder.
 */
public abstract class ShipEntityBuilder<Ship extends ShipEntity, Controller extends ShipController>
        extends EntityObjectBuilder<Ship, Controller> {
    protected FPoint3D linearForcesIncrement; //The linear forces of this ShipEntity along
    //each axis relative to the ships orientaion.
    public void setLinearForcesIncrement(FPoint3D linearForcesIncrement) {
        this.linearForcesIncrement = linearForcesIncrement;
    }
    protected FPoint3D maxLinearForcesIncrement; //The maximum values for linearForcesIncrement.
    public void setMaxLinearForcesIncrement(FPoint3D maxLinearForcesIncrement) {
        this.maxLinearForcesIncrement = maxLinearForcesIncrement;
    }
    protected FPoint3D torquesIncrement; //The torquesIncrement of this
    //ShipEntity along each axis relative to the ships orientation.
    public void setTorquesIncrement(FPoint3D torquesIncrement) {
        this.torquesIncrement = torquesIncrement;
    }
    protected FPoint3D maxTorquesIncrement; //The maximum values for torquesIncrement.
    public void setMaxTorquesIncrement(FPoint3D maxTorquesIncrement) {
        this.maxTorquesIncrement = maxTorquesIncrement;
    }

    /**
     * <p>
     * Creates a new ShipEntityBuilder with the passed values.</p>
     *
     * @param mass                     The mass of produced ShipEntitys.
     * @param collisionMesh            The CollisionMesh which make up produced
     *                                 ShipEntitys.
     * @param location                 The location of produced ShipEntitys in
     *                                 3D space.
     * @param rotation                 The rotation around each axis of produced
     *                                 ShipEntitys in 3D space.
     * @param velocity                 The velocity of produced ShipEntitys.
     * @param typeName                 The name of this type of ShipEntity.
     * @param rotationalSpeed          The rotational speed of produced
     *                                 ShipEntity around each of their axis.
     * @param linearForces             The linear forces acting on produced
     *                                 ShipEntitys relative to produced
     *                                 ShipEntitys.
     * @param maxMagnituidLinearForces The maximum values for linearForces.
     * @param torques                  The torque forces acting on produced
     *                                 ShipEntitys relative to produced
     *                                 ShipEntitys.
     * @param maxMagnituidTorques      The maximum values for torques.
     * @param linearForcesIncrement    The increment for linear forces per
     *                                 update.
     * @param maxLinearForcesIncrement The maximum values for
     *                                 linearForcesIncrement.
     * @param torquesIncrement         The increment for torques per update.
     * @param maxTorquesIncrement      The maximum values for torquesIncrement.
     */
    protected ShipEntityBuilder(final String typeName, final double mass,
                                final CollisionMesh collisionMesh,
                                final FPoint3D location, final FPoint3D rotation,
                                final FPoint3D velocity,
                                final FPoint3D rotationalSpeed,
                                final FPoint3D linearForces,
                                final FPoint3D maxMagnituidLinearForces,
                                final FPoint3D torques,
                                final FPoint3D maxMagnituidTorques,
                                final FPoint3D linearForcesIncrement,
                                final FPoint3D maxLinearForcesIncrement,
                                final FPoint3D torquesIncrement,
                                final FPoint3D maxTorquesIncrement) {
        super(typeName, mass, collisionMesh, location, rotation, velocity,
                rotationalSpeed, linearForces, maxMagnituidLinearForces, torques,
                maxMagnituidTorques);
        this.linearForcesIncrement = linearForcesIncrement;
        this.maxLinearForcesIncrement = maxLinearForcesIncrement;
        this.torquesIncrement = torquesIncrement;
        this.maxTorquesIncrement = maxTorquesIncrement;
    }

}
