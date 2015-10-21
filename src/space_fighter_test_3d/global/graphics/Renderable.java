package space_fighter_test_3d.global.graphics;

import java.awt.Graphics2D;
import java.util.Comparator;
import softEngine3D.objects.Camera;
/**
 * <p>
 * A Renderable is an object which is able to be rendered by a
 * GraphicsThread.</p>
 *
 * @author Dynisious 06/10/2015
 * @version 0.1.1
 */
public abstract class Renderable implements Comparator<Renderable> {
    private static long nextID = 0;
    private static synchronized long getNextID() {
        return nextID++;
    }
    public final long ID = getNextID();

    /**
     * <p>
     * Renders this Renderable using the passed values.</p>
     *
     * @param g      The Graphics2D to render onto.
     * @param camera The Camera to render.
     */
    public abstract void render(final Graphics2D g, final Camera camera);

    @Override
    public int compare(Renderable o1, Renderable o2) {
        return o1.ID < o2.ID ? -1 : 0;
    }

}
