package space_fighter_test_3d.global.graphics;

import java.awt.Graphics2D;
import java.awt.Toolkit;
import softEngine3D.objects.Camera;
import softEngine3D.objects.Object3D;
/**
 * <p>
 * A GraphicsModel implements Renderable and it used to provide a Camera with
 * Object3D's to render.</p>
 *
 * @author Dynisious 19/10/2015
 * @version 0.0.1
 */
public class GraphicsModel extends Renderable {
    private final Object3D[] models;
    public Object3D[] getModels() {
        return models;
    }

    /**
     * <p>
     * Creates a new GraphicsModel with the passed values.</p>
     *
     * @param models The Object3Ds that this GraphicsModel represents.
     */
    public GraphicsModel(final Object3D[] models) {
        this.models = models;
    }

    @Override
    public void render(final Graphics2D g, final Camera camera) {
        camera.rotation = models[0].rotaion;
        camera.location = models[0].location;
        camera.render(g,
                Toolkit.getDefaultToolkit().getScreenSize().width >>> 1,
                Toolkit.getDefaultToolkit().getScreenSize().height >>> 1,
                models);
    }

}
