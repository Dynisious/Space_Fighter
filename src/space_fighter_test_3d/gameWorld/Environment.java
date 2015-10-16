package space_fighter_test_3d.gameWorld;

import dynutils.linkedlist.sorted.LinkedIDListNode;
import dynutils.linkedlist.sorted.WeakLinkedIDListNode;
import space_fighter_test_3d.gameWorld.physics.PhysicsObject;
/**
 * <p>
 * An Environment object contains information relating to a game space at a
 * particular instance.</p>
 *
 * @author Dynisious 16/10/2015
 * @version 0.0.1
 * @param <ObjectType> The type of objects stored inside this Environment.
 */
public class Environment<ObjectType extends PhysicsObject> {
    private final WeakLinkedIDListNode<ObjectType> objectList;
    public WeakLinkedIDListNode<ObjectType> getObjectList() {
        return (WeakLinkedIDListNode<ObjectType>) objectList;
    }

    /**
     * <p>
     * Creates a new Environment instance with the passed values.</p>
     *
     * @param objectList The linked list of objects which are a part in this
     *                   Environment.
     */
    public Environment(final LinkedIDListNode<ObjectType> objectList) {
        LinkedIDListNode<ObjectType> head;
        head = this.objectList = new WeakLinkedIDListNode<>(
                objectList.getValue());
        for (LinkedIDListNode<ObjectType> node = objectList.getNextNode();
                node != null; node = objectList.getNextNode()) {
            head = new WeakLinkedIDListNode<ObjectType>(node.getValue())
                    .insertAhead(head);
        }
    }

}
