import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public abstract class WalkingEnemy extends GameObject
{
    private static final float VELOCITY_X = 200;
    private float groundXPos;

    private final float groundXDimension;
    private boolean isFlippedHorizontally;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public WalkingEnemy(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Vector2 position) {
        super(topLeftCorner, dimensions, renderable);
        transform().setVelocityX(VELOCITY_X);
        transform().setAccelerationY(600);

        setCenter(position);

        // finding the platform that this character is standing on
        GameObject platform = PlatformInformation.findThePlatform(position);

        groundXPos = platform.getCenter().x();
        groundXDimension = platform.getDimensions().x();
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals("enemy") || other.getTag().equals("blob"))
        {
            transform().setVelocityX(-getVelocity().x());
            isFlippedHorizontally = !isFlippedHorizontally;

            renderer().setIsFlippedHorizontally(isFlippedHorizontally);

        }
    }

    public void update(float deltaTime) {

        super.update(deltaTime);


        if (getCenter().x() >= groundXPos + groundXDimension / 2 - 50) {
            transform().setVelocityX(-VELOCITY_X);
            renderer().setIsFlippedHorizontally(isFlippedHorizontally);
            isFlippedHorizontally = true;
        }
        else if (getCenter().x() <= groundXPos - groundXDimension / 2 + 50)
        {
            transform().setVelocityX(VELOCITY_X);
            renderer().setIsFlippedHorizontally(isFlippedHorizontally);
            isFlippedHorizontally = false;

        }











    }
}
