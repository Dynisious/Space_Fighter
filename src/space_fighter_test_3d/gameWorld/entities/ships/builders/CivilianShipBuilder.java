package space_fighter_test_3d.gameWorld.entities.ships.builders;

import softEngine3D.matrixes.FPoint3D;
import space_fighter_test_3d.gameWorld.entities.ships.CivilianShip;
import space_fighter_test_3d.gameWorld.entities.ships.shipControls.ShipController;
import space_fighter_test_3d.gameWorld.physics.geometry.CollisionMesh;
public class CivilianShipBuilder extends ShipEntityBuilder<CivilianShip, ShipController> {

    /**
     * <p>
     * Creates a new CivilianShipBuilder with the passed values.</p>
     *
     * @param mass                     The mass of produced CivilianShips.
     * @param collisionMesh            The CollisionMesh which make up produced
     *                                 CivilianShips.
     * @param location                 The location of produced CivilianShips in
     *                                 3D space.
     * @param rotation                 The rotation around each axis of produced
     *                                 CivilianShips in 3D space.
     * @param velocity                 The velocity of produced CivilianShips.
     * @param typeName                 The name of this type of CivilianShip.
     * @param rotaionalSpeed           The rotational speed of produced
     *                                 CivilianShip around each of their axis.
     * @param linearForces             The linear forces acting on produced
     *                                 CivilianShips relative to produced
     *                                 CivilianShips.
     * @param maxMagnituidLinearForces The maximum values for linearForces.
     * @param torques                  The torque forces acting on produced
     *                                 CivilianShips relative to produced
     *                                 CivilianShips.
     * @param maxMagnituidTorques      The maximum values for torques.
     * @param linearForcesIncrement    The increment for linear forces per
     *                                 update.
     * @param maxLinearForcesIncrement The maximum values for
     *                                 linearForcesIncrement.
     * @param torquesIncrement         The increment for torques per update.
     * @param maxTorquesIncrement      The maximum values for torquesIncrement.
     */
    public CivilianShipBuilder(final String typeName, final double mass,
                               final CollisionMesh collisionMesh,
                               final FPoint3D location,
                               final FPoint3D rotation, final FPoint3D velocity,
                               final FPoint3D rotaionalSpeed,
                               final FPoint3D linearForces,
                               final FPoint3D maxMagnituidLinearForces,
                               final FPoint3D torques,
                               final FPoint3D maxMagnituidTorques,
                               final FPoint3D linearForcesIncrement,
                               final FPoint3D maxLinearForcesIncrement,
                               final FPoint3D torquesIncrement,
                               final FPoint3D maxTorquesIncrement) {
        super(typeName, mass, collisionMesh, location, rotation, velocity,
                rotaionalSpeed, linearForces, maxMagnituidLinearForces, torques,
                maxMagnituidTorques, linearForcesIncrement,
                maxLinearForcesIncrement, torquesIncrement, maxTorquesIncrement);
    }

    @Override
    public CivilianShip build() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CivilianShip build(ShipController... arguments) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CivilianShip build(final ShipController controller) {
        return new CivilianShip(this, mass, collisionMesh.copy(), location
                .copy(), rotation.copy(), velocity.copy(), rotationalSpeed
                .copy(), linearForces.copy(), maxMagnituidLinearForces.copy(),
                torques.copy(), maxMagnituidTorques.copy(),
                linearForcesIncrement.copy(), maxLinearForcesIncrement.copy(),
                torquesIncrement.copy(), maxTorquesIncrement.copy(), controller);
    }

}
