import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Enemy2 extends GameObject
{

    private final Vector2 endingPosition;
    private float cycle;
    private Vector2 startingPosition;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param lengthOfJump
     */
    public Enemy2(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Vector2 startingPosition, float cycle, float lengthOfJump) {
        super(topLeftCorner, dimensions, renderable);
        this.startingPosition = startingPosition;
        endingPosition = new Vector2(startingPosition.x(), startingPosition.y() - lengthOfJump);
        this.cycle = cycle;

        setCenter(startingPosition);

        this.renderer().setIsFlippedHorizontally(true);
        setTag("enemy");
        movement();

    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals("lava"))
            renderer().fadeOut(0.05f);
    }

    @Override
    public void onCollisionExit(GameObject other) {
        super.onCollisionExit(other);
        renderer().fadeIn(0.05f);
    }

    public void movement()
    {
        new Transition<Float>(
                this,
                (y) -> setCenter(new Vector2(getCenter().x(), y)),
                startingPosition.y(),
                endingPosition.y(),
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                cycle,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null


                
        );
    }
}
