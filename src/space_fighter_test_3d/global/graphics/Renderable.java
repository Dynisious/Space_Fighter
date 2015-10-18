package space_fighter_test_3d.global.graphics;

import java.awt.Graphics2D;
import java.util.Comparator;
/**
 * <p>
 * A Renderable is an object which is able to be rendered by a
 * GraphicsThread.</p>
 *
 * @author Dynisious 06/10/2015
 * @version 0.0.1
 */
public abstract class Renderable implements Comparator<Renderable> {
    private static long nextID = 0;
    private static synchronized long getNextID() {
        return nextID++;
    }
    public final long ID = getNextID();

    /**
     * <p>
     * Renders to the passed Graphics 2D.</p>
     *
     * @param g The Graphics2D to render to.
     */
    public abstract void render(final Graphics2D g);

    @Override
    public int compare(Renderable o1, Renderable o2) {
        return o1.ID > o2.ID ? -1 : 0;
    }

}
