import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Flag extends GameObject
{

    private WindowController windowController;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Flag(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, WindowController windowController) {
        super(topLeftCorner, dimensions, renderable);
        this.windowController = windowController;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals("player"))
        {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    windowController.showMessageBox("YOU WIN!");
                    windowController.closeWindow();
                }
            };
            new ScheduledTask(this, 0.25f, false, runnable);

        }
    }
}
