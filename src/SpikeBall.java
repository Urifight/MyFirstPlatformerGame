import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class SpikeBall extends GameObject
{

    private static final float FADEIN_TIME = 0.7f;
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */

    static Random rnd = new Random();
    private final GameObjectCollection gameObjects;
    private final GameObject player;

    private float xVel = 320f;
    private final float distanceOfAppearance;
    private boolean used;
    private int direction = -1;
    private boolean rolled = false;
    private boolean isFadedIn = false;

    public SpikeBall(Vector2 topLeftCorner,
                     Vector2 dimensions,
                     Renderable renderable,
                     GameObjectCollection gameObjects,
                     Vector2 position,
                     GameObject player) {
        super(topLeftCorner, dimensions, renderable);


        renderer().fadeOut(0);
        physics().preventIntersectionsFromDirection(Vector2.DOWN);

        setCenter(position);
        this.gameObjects = gameObjects;
        this.player = player;

        setCenter(position);

//        if (rnd.nextBoolean())
//            distanceOfAppearance = rnd.nextFloat(400, 600);
//
//        else
//            distanceOfAppearance = rnd.nextFloat(-300, -100);

        distanceOfAppearance = -300;
        //transform().setAccelerationY(3600);


        used = false;
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        /*
        the used variable checks that we did not activate the spike ball

         */


        if (!used) {

            if (distanceOfAppearance <= 0) {
                if (player.getCenter().x() >= getCenter().x() - distanceOfAppearance) {
                    activate();
                    used = true;
                }
            }

            else {
                if (player.getCenter().x() >= getCenter().x() + distanceOfAppearance) {
                    activate();
                    used = true;
                }
            }
        }
    }

    private void activate()
    {

        renderer().fadeIn(FADEIN_TIME);

        setTag("enemy");

        new ScheduledTask(this, FADEIN_TIME, false, () ->
        {
            transform().setAccelerationY(1500);
            isFadedIn = true;
         });

    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals("normal") && !rolled) {
            rolled = true;
            roll();
        }
        if (other.getTag().equals("lava"))
        {
            gameObjects.removeGameObject(this);
        }
    }

    private void roll()
    {

        if (player.getCenter().x() < getCenter().x())
        {
            xVel *= -1;
            direction = 1;
        }

        new Transition<Float>(this,
                (angle) -> renderer().setRenderableAngle(angle),
                0f * direction,
                360f * direction,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                0.5f,
                Transition.TransitionType.TRANSITION_LOOP,
                null
        );

        transform().setVelocityX(xVel);

    }
}
