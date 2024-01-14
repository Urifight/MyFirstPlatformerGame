import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Enemy3 extends GameObject
{

    private float groundXPos;
    private float groundXDimension;

    private final float NORMAL_VELOCITY = 200;
    private final float CHASE_VELOCITY = 400;

    private final float allowedDistanceEnemyToPlayer = 200f;
    private GameObject platform;
    private boolean used;
    private GameObject player;

    private float waitingTimeForMoving = 2f;

    private float VELOCITY_Y = -450;

    private boolean avatarFlip = true;
    private boolean movementDirection;


    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Enemy3(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Vector2 position, GameObject player) {
        super(topLeftCorner, dimensions, renderable);
        this.player = player;
        setCenter(position);
        platform = PlatformInformation.findThePlatform(position);
        setTag("enemy");
        groundXPos = platform.getCenter().x();
        groundXDimension = platform.getDimensions().x();

        transform().setVelocityX(NORMAL_VELOCITY);
        transform().setAccelerationY(700);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        used = false;
    }

    private boolean isPlayerClose()
    {
        if (getCenter().x() - player.getCenter().x() <= allowedDistanceEnemyToPlayer)
            return true;
        return false;
    }

    private void jump()
    {
        transform().setVelocityY(VELOCITY_Y);
    }

    private void normalBehavior(float speedToDoAfter)
    {
        transform().setVelocityX(0);

        new ScheduledTask(this, waitingTimeForMoving, false, () -> {
            transform().setAccelerationY(600);
            renderer().setIsFlippedHorizontally(avatarFlip);
            avatarFlip = !avatarFlip;
        });
        movementDirection = false;

    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals("lava"))
        {
            renderer().fadeOut(0.02f);
        }
    }

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);

        platform = PlatformInformation.findThePlatform(getCenter());

        if (PlatformInformation.findThePlatform(new Vector2(getCenter().x()  +60, getCenter().y())) == null)
        {
            transform().setVelocityX(-getVelocity().x());
            movementDirection = !movementDirection;
            setCenter(new Vector2(getCenter().x() - 10, getCenter().y()));
        }
        if (!movementDirection) {
            if (PlatformInformation.isThereAPlatform(new Vector2(getCenter().x() + 100, getCenter().y()))) {

                jump();
            }
        }

        if (movementDirection)
        {
            if (PlatformInformation.isThereAPlatform(new Vector2(getCenter().x() -100, getCenter().y()))) {

                jump();
            }
        }



    }

}
