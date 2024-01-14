import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
import java.security.Key;
import java.time.ZonedDateTime;

public class Player extends GameObject
{


    private static final float ORIGINAL_VELOCITY_X = 300;
    private static float velocityX = ORIGINAL_VELOCITY_X;
    private static final float GRAVITY = 1100;

    private static final float VELOCITY_Y = -650;
    private final Renderable leftAnimation;
    private final Renderable rightAnimation;
    private final Renderable middleAnimation;

    private Vector2 startingPoint;
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */

    UserInputListener inputListener;
    private WindowController windowController;

    public Player(Vector2 startingPoint, Vector2 dimensions, Renderable renderable, UserInputListener inputListener, ImageReader imageReader, WindowController windowController) {
        super(startingPoint, dimensions, renderable);
        this.startingPoint = startingPoint;//data.getPlayerStartingPoint();
        this.inputListener = inputListener;
        this.windowController = windowController;
        transform().setAccelerationY(GRAVITY);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        leftAnimation = imageReader.readImage("Avatar/AvatarAnimationLeft.png", true);
        rightAnimation = imageReader.readImage("C:\\Users\\uriya\\IdeaProjects\\MyFirstPlatformerGame\\src\\Avatar\\\u200F\u200FAvatarAnimationRight.png", true);
        middleAnimation = imageReader.readImage("Avatar/AvatarAnimationMiddle.png", true);

        setTag("player");


    }

    private void respawn()
    {
        renderer().fadeOut(0.1f);
        new ScheduledTask(this, 0.1f, false, () -> windowController.resetGame());
        new ScheduledTask(this, 2f, false, () -> setCenter(startingPoint));

    }
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        if (other.getTag().equals("trampoline"))
        {
            new ScheduledTask(this, 0.04f, false, () -> transform().setVelocityY(VELOCITY_Y * 1.5f));

        }
        if (other.getTag().equals("lava") || other.getTag().equals("enemy") )
        {

            new ScheduledTask(this, 0.02f, false, this::respawn);
        }

        if (other.getTag().equals("blob"))
        {
            velocityX = velocityX / 3;
            new ScheduledTask(this, 5f, false, () -> velocityX = ORIGINAL_VELOCITY_X);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = 0;

        if (inputListener.isKeyPressed(KeyEvent.VK_F))
            transform().setVelocityY(0);
        if (inputListener.isKeyPressed(KeyEvent.VK_R))
            setCenter(new Vector2(getCenter().x() + 200, getCenter().y()));

        if (inputListener.isKeyPressed(KeyEvent.VK_Q))
            System.out.println(getCenter());

        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT))
        {
            xVel -= velocityX;
            renderer().setIsFlippedHorizontally(true);
//            new ScheduledTask(this, 0.1f, false, () -> renderer().setRenderable(middleAnimation));
//            new ScheduledTask(this, 0.2f, true, () -> renderer().setRenderable(rightAnimation));
//            new ScheduledTask(this, 0.3f, true, () -> renderer().setRenderable(leftAnimation));



        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            xVel += velocityX;
            renderer().setIsFlippedHorizontally(false);

//            new ScheduledTask(this, 0.1f, false, () -> renderer().setRenderable(rightAnimation));
//            new ScheduledTask(this, 0.2f, false, () -> renderer().setRenderable(leftAnimation));
//            new ScheduledTask(this, 0.3f, false, () -> renderer().setRenderable(middleAnimation));

        }
        transform().setVelocityX(xVel);
//        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT) || inputListener.isKeyPressed(KeyEvent.VK_LEFT))
//        {
//            new ScheduledTask(
//                    this,
//                    0.1f,
//                    true,
//                    () -> renderer().setRenderable(rightAnimation));
//            //renderer().setRenderable(rightAnimation);
//            new ScheduledTask(
//                    this,
//                    0.3f,
//                    true,
//                    () -> renderer().setRenderable(leftAnimation));
//        }


        if (!(inputListener.isKeyPressed(KeyEvent.VK_RIGHT) || inputListener.isKeyPressed(KeyEvent.VK_LEFT)))
        {
            renderer().setRenderable(middleAnimation);

        }
        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0)
        {
            transform().setVelocityY(VELOCITY_Y);
        }
    }
}
