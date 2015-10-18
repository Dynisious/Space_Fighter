package space_fighter_test_3d.gameWorld.entities.ships;

import java.util.EventListener;
import softEngine3D.matrixes.FPoint3D;
import softEngine3D.objects.Triangle;
import space_fighter_test_3d.gameWorld.Environment;
import space_fighter_test_3d.gameWorld.entities.EntityObject;
import space_fighter_test_3d.gameWorld.entities.ships.builders.ShipEntityBuilder;
import space_fighter_test_3d.gameWorld.entities.ships.events.ShipEntityEventListener;
import space_fighter_test_3d.gameWorld.entities.ships.shipControls.ShipController;
/**
 * <p>
 * ShipEntities are craft which fly through 3D space.</p>
 *
 * @author Dynisious 06/10/2015
 * @version 0.0.1
 * @param <Controller> The Type of ShipController to be stored in this
 *                     ShipEntity.
 */
public abstract class ShipEntity extends EntityObject {
    protected FPoint3D linearForcesIncrement; //The linear forces of this ShipEntity along
    //each axis relative to the ships orientaion.
    public FPoint3D getLinearForcesIncrement() {
        return linearForcesIncrement;
    }
    protected FPoint3D maxLinearForcesIncrement; //The maximum values for linearForcesIncrement.
    public FPoint3D getMaxLinearForcesIncrement() {
        return maxLinearForcesIncrement;
    }
    protected FPoint3D torquesIncrement; //The torquesIncrement of this
    //ShipEntity along each axis relative to the ships orientation.
    public FPoint3D getTorquesIncrement() {
        return torquesIncrement;
    }
    protected FPoint3D maxTorquesIncrement; //The maximum values for torquesIncrement.
    public FPoint3D getMaxTorquesIncrement() {
        return maxTorquesIncrement;
    }

    /**
     * <p>
     * Creates a new ShipEntity with the passed values.</p>
     *
     * @param builder                  The ShipEntityBuilder which produced
     *                                 this ShipEntity.
     * @param mass                     The mass of this ShipEntity.
     * @param triangles                The vertexes which make up this
     *                                 ShipEntity.
     * @param location                 The location of this ShipEntity in 3D
     *                                 space.
     * @param rotation                 The rotation around each axis of this
     *                                 ShipEntity in 3D space.
     * @param velocity                 The velocity of this ShipEntity.
     * @param rotationalSpeed          The rotational speed of the ShipEntity
     *                                 around each of it's axis.
     * @param linearForces             The linear forces acting on this
     *                                 ShipEntity relative to this ShipEntity.
     * @param maxMagnituidLinearForces The maximum values for linearForces.
     * @param torques                  The torque forces acting on this
     *                                 ShipEntity relative to this ShipEntity.
     * @param maxMagnituidTorques      The maximum values for torques.
     * @param linearForcesIncrement    The increment for linear forces per
     *                                 update.
     * @param maxLinearForcesIncrement The maximum values for
     *                                 linearForcesIncrement.
     * @param torquesIncrement         The increment for torques per update.
     * @param maxTorquesIncrement      The maximum values for torquesIncrement.
     * @param controller               the value of controller
     */
    protected ShipEntity(final ShipEntityBuilder builder, final double mass,
                         final Triangle[] triangles, final FPoint3D location,
                         final FPoint3D rotation, final FPoint3D velocity,
                         final FPoint3D rotationalSpeed,
                         final FPoint3D linearForces,
                         final FPoint3D maxMagnituidLinearForces,
                         final FPoint3D linearForcesIncrement,
                         final FPoint3D maxLinearForcesIncrement,
                         final FPoint3D torques,
                         final FPoint3D maxMagnituidTorques,
                         final FPoint3D torquesIncrement,
                         final FPoint3D maxTorquesIncrement,
                         final ShipController controller) {
        super(builder, mass, triangles, location, rotation, velocity,
                rotationalSpeed, linearForces, maxMagnituidLinearForces, torques,
                maxMagnituidTorques);
        this.linearForcesIncrement = linearForcesIncrement;
        this.maxLinearForcesIncrement = maxLinearForcesIncrement;
        this.torquesIncrement = torquesIncrement;
        this.maxTorquesIncrement = maxTorquesIncrement;
        fireShipEntityControllerSetEvent(controller);
    }

    /**
     * <p>
     * Alerts all listeners that a new ShipController has been set for this
     * ShipEntity.</p>
     *
     * @param controller The new ShipController.
     */
    public final void fireShipEntityControllerSetEvent(
            final ShipController controller) {
        addListener(controller);
        for (final EventListener l : getListeners()) {
            ((ShipEntityEventListener) l).handleShipControllerSetEvent(
                    this, controller);
        }
    }

    @Override
    public void firePhysicsObjectUpdateEvent(final Environment environment) {
        synchronized (valuesLock) {
            linearForces = linearForces.addition(linearForcesIncrement);
            if (maxMagnituidLinearForces.x < Math.abs(linearForces.x)) {
                linearForces.x = linearForces.x > 0
                        ? maxMagnituidLinearForces.x
                        : -maxMagnituidLinearForces.x;
            }
            if (maxMagnituidLinearForces.y < Math.abs(linearForces.y)) {
                linearForces.y = linearForces.y > 0
                        ? maxMagnituidLinearForces.y
                        : -maxMagnituidLinearForces.y;
            }
            if (maxMagnituidLinearForces.z < Math.abs(linearForces.z)) {
                linearForces.z = linearForces.z > 0
                        ? maxMagnituidLinearForces.z
                        : -maxMagnituidLinearForces.z;
            }

            torques = torques.addition(torquesIncrement);
            if (maxMagnituidTorques.x < Math.abs(torques.x)) {
                torques.x = torques.x > 0
                        ? maxMagnituidTorques.x
                        : -maxMagnituidTorques.x;
            }
            if (maxMagnituidTorques.y < Math.abs(torques.y)) {
                torques.y = torques.y > 0
                        ? maxMagnituidTorques.y
                        : -maxMagnituidTorques.y;
            }
            if (maxMagnituidTorques.z < Math.abs(torques.z)) {
                torques.z = torques.z > 0
                        ? maxMagnituidTorques.z
                        : -maxMagnituidTorques.z;
            }
            super.firePhysicsObjectUpdateEvent(environment);
        }
    }

    @Override
    public String toString() {
        return super.toString()
                + "\r\n  " + String.format("%-24s", "linear_forces_increment") + "=" + linearForcesIncrement
                + "\r\n  " + String.format("%-24s", "torques_increment") + "=" + torquesIncrement;
    }

}
