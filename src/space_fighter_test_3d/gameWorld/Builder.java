package space_fighter_test_3d.gameWorld;

import java.util.ArrayList;
/**
 * <p>
 * A base class for all builders.</p>
 *
 * @author Dynisious 09/10/2015
 * @version 0.0.1
 * @param <ObjectType> The type of Object built by this Builder.
 * @param <Arg>        The type of arguments passed into the Build function.
 */
public abstract class Builder<ObjectType, Arg> {
    private static ArrayList<Builder> allBuilders;
    protected static ArrayList<Builder> getAllBuilders() {
        return allBuilders;
    }
    public static final void addNewBuilder(final Builder builder) {
        if (allBuilders == null) {
            allBuilders = new ArrayList<>();
        }
        allBuilders.add(builder);
    }

    /**
     * @param arguments The arguments for this Builders build function.
     *
     * @return A new object built with this Builder.
     */
    public abstract ObjectType build(final Arg... arguments);

    /**
     * @param argument The argument for this Builders build function.
     *
     * @return A new object built with this Builder.
     */
    public abstract ObjectType build(final Arg argument);

    /**
     * @return A new object built with this Builder.
     */
    public abstract ObjectType build();

}
