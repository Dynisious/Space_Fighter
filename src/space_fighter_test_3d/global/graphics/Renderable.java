package space_fighter_test_3d.global.graphics;

import java.awt.Graphics2D;
import dynutils.linkedlist.sorted.TaggedByID;
/**
 * <p>
 * A Renderable is an object which is able to be rendered by a
 * GraphicsThread.</p>
 *
 * @author Dynisious 06/10/2015
 * @version 0.0.1
 */
public abstract class Renderable implements TaggedByID {
    private static long nextID = 0;
    private static synchronized long getNextID() {
        return nextID++;
    }
    private final long ID = getNextID(); //The specific ID for this Renderable.
    @Override
    public final long getID() {
        return ID;
    }

    /**
     * <p>
     * Renders to the passed Graphics 2D.</p>
     *
     * @param g The Graphics2D to render to.
     */
    public abstract void render(final Graphics2D g);

}
