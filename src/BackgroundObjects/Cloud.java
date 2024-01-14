package BackgroundObjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.function.Consumer;

public class Cloud extends GameObject
{


    private Vector2 windowDimensions;
    private Camera camera;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Cloud(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.windowDimensions = windowDimensions;

        transform().setVelocityX(100);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (getVelocity().x() < 0f) {
            if (getCenter().x() < 0 - getDimensions().x())
                setCenter(new Vector2(windowDimensions.x() + getDimensions().x(), getDimensions().y() + 25));
        }
        else
        {
            if (getCenter().x() > windowDimensions.x() + getDimensions().x())
                setCenter(new Vector2(0 - getDimensions().x(), getDimensions().y() + 25));
        }

    }
}
