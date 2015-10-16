package space_fighter_test_3d.gameWorld.gameStates;

import space_fighter_test_3d.gameWorld.entities.EntityObject;
import dynutils.linkedlist.sorted.LinkedIDList;
import dynutils.linkedlist.sorted.StrongLinkedIDListNode;
import dynutils.linkedlist.sorted.WeakLinkedIDListNode;
import space_fighter_test_3d.gameWorld.Environment;
/**
 * <p>
 * Free flight mode with limited obstacles or other entities.</p>
 *
 * @author Dynisious 06/10/2015
 * @version 0.0.1
 */
public class FreeFlightStateModule extends GameStateModule {
    public static final int ID = getNextID();
    @Override
    public long getID() {
        return ID;
    }
    public final LinkedIDList<EntityObject> entityList;
    public WeakLinkedIDListNode<EntityObject> toUpdateCurrent; //The first item in
    //the linked list of EntityObjects which need to update.
    public WeakLinkedIDListNode<EntityObject> toUpdateEnd; //The last item in the
    //linked list of EntityObjects which need to update.

    public FreeFlightStateModule(final LinkedIDList<EntityObject> entityList) {
        this.entityList = entityList;
    }

    @Override
    public int update() {
        StrongLinkedIDListNode<EntityObject> node = entityList.getFirst();
        if (node != null) {
            toUpdateEnd = toUpdateCurrent = new WeakLinkedIDListNode<>(
                    node.getValue());
            node = node.getNextNode();
            while (node != null) {
                toUpdateEnd = new WeakLinkedIDListNode<EntityObject>(
                        node.getValue()).insertAhead(toUpdateEnd);
                node = node.getNextNode();
            }
            do {
                if (toUpdateCurrent.getValue() != null) {
                    toUpdateCurrent.getValue().firePhysicsObjectUpdateEvent(
                            new Environment(entityList.getFirst()));
                    toUpdateCurrent = toUpdateCurrent.getNextNode();
                }
            } while (toUpdateCurrent != null);
        }
        return ID;
    }

}
