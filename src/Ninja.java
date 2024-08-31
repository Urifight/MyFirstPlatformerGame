import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class Ninja extends GameObject
{

    private static final float FADEIN_TIME = 0.7f;
    private final float distanceOfAppearance;
    Random rnd = new Random();
    private boolean used;
    private float shurikenXVel;
    private GameObjectCollection gameObjects;
    Renderable shurikenImg;
    private GameObject player;

    private final float SHURIKEN_SIZE = 60;
    private final float SHURIKEN_VELOCITY = 250f;
    private GameObject shuriken;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Ninja(Vector2 topLeftCorner,
                 Vector2 dimensions,
                 Renderable renderable,
                 GameObjectCollection gameObjects,
                 Renderable shurikenImg,
                 Vector2 position,
                 GameObject player) {


        super(topLeftCorner, dimensions, renderable);
        renderer().fadeOut(0);
        physics().preventIntersectionsFromDirection(Vector2.DOWN);
        this.gameObjects = gameObjects;
        this.shurikenImg = shurikenImg;
        this.player = player;

        shurikenXVel = SHURIKEN_VELOCITY;
        setCenter(position);

        if (rnd.nextBoolean())
            distanceOfAppearance = rnd.nextFloat(400, 600);

        else
            distanceOfAppearance = rnd.nextFloat(-300, -100);


        transform().setAccelerationY(3600);


        used = false;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        /*
        the used variable checks that we did not activate the ninja
        more than one time. so it fades in, throws the shuriken, and fades out
        only one time.
         */


        if (!used)
        {
            // if the ninja's location is from the left of the player
            // and the player has gotten into the position that is set for
            // the ninja to appear, it will activate the ninja.
            if (distanceOfAppearance <= 0) {
                if (player.getCenter().x() >= getCenter().x() + distanceOfAppearance) {
                    new ScheduledTask(this, 0.9f, false, this::activate);
                    used = true;
                }
            }
            // if the ninja's location is from the right of the player
            // and the player has gotten into the position that is set for
            // the ninja to appear, it will activate the ninja.
            else
            {
                if (player.getCenter().x() >= getCenter().x() - distanceOfAppearance) {
                    new ScheduledTask(this, 0.9f, false, this::activate);
                    used = true;
                }
            }
        }
    }

    private void activate()
    {
        // we create a new task: when the ninja appears, it would take 0.5 seconds until it can kill
        // it can kill when we set its tag to "enemy".
        new ScheduledTask(this, 0.5f, false, () -> setTag("enemy"));

        //
        renderer().fadeIn(FADEIN_TIME, this::throwShuriken);

        /*
        if the ninja is from the right of the player, it would
        turn the avatar to the left and the shuriken's speed
        to the left.
         */
        if (player.getCenter().x() < getCenter().x())
        {
            renderer().setIsFlippedHorizontally(true);
            shurikenXVel = -SHURIKEN_VELOCITY;

        }

        //after the ninja appears, it would take 1.5 seconds until it disappears and its object is removed.
        new ScheduledTask(this, 1.5f, false, () -> renderer().fadeOut(0.2f, () -> gameObjects.removeGameObject(this)));
    }



    private float calculateVelocityYShuriken()
    {
        float distanceX = player.getCenter().x() - shuriken.getCenter().x();
        float time = distanceX / shuriken.getVelocity().x();

        float distanceY = player.getCenter().y() - shuriken.getCenter().y();
        float velocityY = (distanceY - 0.5f * shuriken.transform().getAcceleration().y() * (float) Math.pow(time, 2)) / time;
        return velocityY;


    }

    private void throwShuriken()
    {
        // creating the shuriken and setting its center to the ninja's position
        shuriken = new Shuriken(Vector2.ZERO, new Vector2(SHURIKEN_SIZE, SHURIKEN_SIZE), shurikenImg);
        shuriken.setCenter(getCenter());
        gameObjects.addGameObject(shuriken);
        shuriken.transform().setVelocityX(shurikenXVel);
        float velocityY = calculateVelocityYShuriken();

        // setting the shuriken its speed (meaning the ninja is throwing the shuriken)
        shuriken.transform().setVelocityY(velocityY);

        // spinning the shuriken 360 degrees until it disappears
        new Transition<Float>(shuriken,
                (angle) -> shuriken.renderer().setRenderableAngle(angle),
                0f,
                360f,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                0.5f,
                Transition.TransitionType.TRANSITION_LOOP,
                null
        );
        new ScheduledTask(shuriken, 5f, false, () -> gameObjects.removeGameObject(shuriken));
    }
}
