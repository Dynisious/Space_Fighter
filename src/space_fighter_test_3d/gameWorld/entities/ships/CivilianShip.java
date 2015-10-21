package space_fighter_test_3d.gameWorld.entities.ships;

import softEngine3D.matrixes.FPoint3D;
import space_fighter_test_3d.gameWorld.entities.ships.builders.ShipEntityBuilder;
import space_fighter_test_3d.gameWorld.entities.ships.shipControls.ShipController;
import space_fighter_test_3d.gameWorld.physics.geometry.CollisionMesh;
/**
 * <p>
 * A CivilianShip is a CivilianShip which is only capable of flying, nothing
 * more.</p>
 *
 * @author Dynisious 06/10/2015
 * @version 0.0.1
 */
public class CivilianShip extends ShipEntity {

    /**
     * <p>
     * Creates a new CivilianShip with the passed values.</p>
     *
     * @param builder                  The CivilianShipBuilder which produced
     *                                 this CivilianShip.
     * @param mass                     The mass of this CivilianShip.
     * @param collisionMesh            The CollisionMesh which makes up this
     *                                 CivilianShip.
     * @param location                 The location of this CivilianShip in 3D
     *                                 space.
     * @param rotation                 The rotation around each axis of this
     *                                 CivilianShip in 3D space.
     * @param velocity                 The velocity of this CivilianShip.
     * @param rotationalSpeed          The rotational speed of the CivilianShip
     *                                 around each of it's axis.
     * @param linearForces             The linear forces acting on this
     *                                 CivilianShip relative to this CivilianShip.
     * @param maxMagnituidLinearForces The maximum values for linearForces.
     * @param torques                  The torque forces acting on this
     *                                 CivilianShip relative to this CivilianShip.
     * @param maxMagnituidTorques      The maximum values for torques.
     * @param linearForcesIncrement    The increment for linear forces per
     *                                 update.
     * @param maxLinearForcesIncrement The maximum values for
     *                                 linearForcesIncrement.
     * @param torquesIncrement         The increment for torques per update.
     * @param maxTorquesIncrement      The maximum values for torquesIncrement.
     * @param controller               The ShipController for this CivilianShip.
     */
    public CivilianShip(final ShipEntityBuilder builder, final double mass,
                        final CollisionMesh collisionMesh,
                        final FPoint3D location, final FPoint3D rotation,
                        final FPoint3D velocity, final FPoint3D rotationalSpeed,
                        final FPoint3D linearForces,
                        final FPoint3D maxMagnituidLinearForces,
                        final FPoint3D torques,
                        final FPoint3D maxMagnituidTorques,
                        final FPoint3D linearForcesIncrement,
                        final FPoint3D maxLinearForcesIncrement,
                        final FPoint3D torquesIncrement,
                        final FPoint3D maxTorquesIncrement,
                        final ShipController controller) {
        super(builder, mass, collisionMesh, location, rotation, velocity,
                rotationalSpeed, linearForces, maxMagnituidLinearForces,
                linearForcesIncrement, maxLinearForcesIncrement, torques,
                maxMagnituidTorques, torquesIncrement, maxTorquesIncrement,
                controller);
    }

}
