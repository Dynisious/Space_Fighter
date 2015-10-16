package space_fighter_test_3d.gameWorld;

import dynutils.linkedlist.sorted.TaggedByID;
import dynutils.linkedlist.sorted.StrongLinkedIDListNode;
/**
 * <p>
 * A base class for all builders.</p>
 *
 * @author Dynisious 09/10/2015
 * @version 0.0.1
 * @param <T>   The type of Object built by this Builder.
 * @param <Arg> The type of arguments passed into the Build function.
 */
public abstract class Builder<T, Arg> implements TaggedByID {
    protected static StrongLinkedIDListNode allBuilders;
    public static final void addNewBuilder(final Builder builder) {
        if (allBuilders == null) {
            allBuilders = new StrongLinkedIDListNode<>(builder);
        }
        new StrongLinkedIDListNode<>(builder).insertAhead(allBuilders);
    }
    public static final <T extends Builder> T getBuilderByID(final int ID) {
        StrongLinkedIDListNode<Builder> node = allBuilders;
        while (node.getValueID() != ID && node.getNextNode() != null) {
            node = node.getNextNode();
        }
        if (node.getValueID() == ID) {
            return node.getValue();
        }
        return null;
    }

    /**
     * @param arguments The arguments for this Builders build function.
     *
     * @return A new object built with this Builder.
     */
    public abstract T build(final Arg... arguments);

    /**
     * @param argument The argument for this Builders build function.
     *
     * @return A new object built with this Builder.
     */
    public abstract T build(final Arg argument);

    /**
     * @return A new object built with this Builder.
     */
    public abstract T build();

}
