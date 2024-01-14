import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Blob extends WalkingEnemy{
    private final float height;
    private GameObjectCollection gameObjects;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param position
     */
    public Blob(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Vector2 position, GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable, position);
        physics().preventIntersectionsFromDirection(Vector2.DOWN);

        this.gameObjects = gameObjects;
        setTag("blob");
        height = getDimensions().y();

    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals("player"))
        {
            physics().preventIntersectionsFromDirection(Vector2.DOWN);

            renderer().fadeOut(0.5f, () -> gameObjects.removeGameObject(this));
        }
        new Transition<Vector2>(
                this,
                this::setDimensions,
                new Vector2(getDimensions().x(), height),
                new Vector2(getDimensions().x(), height + 50),
                Transition.CUBIC_INTERPOLATOR_VECTOR,
                0.5f,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null



        );
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

    }
}
