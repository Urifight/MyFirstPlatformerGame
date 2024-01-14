import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class Enemy extends WalkingEnemy
{

    private static Random rnd = new Random();
    private static final float VELOCITY_X = 200;
    private float groundXDimension;
    private boolean isFlippedHorizontally;
    private float positionX;
    private float groundXPos;
    private float posY;
    private float currentgroundXPos;
    private float previousX;
    private Vector2 groundDimensions;
    private float positionX2;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Enemy(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Vector2 position) {

        super(topLeftCorner, dimensions, renderable, position);
        setTag("enemy");
        physics().preventIntersectionsFromDirection(Vector2.ZERO);

    }

















}
