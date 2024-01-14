package BackgroundObjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class CloudMaker
{
    private static final float CLOUD_WIDTH = 200;
    private static final float CLOUD_HEIGHT = 50;
    private static final Color CLOUD_COLOR = Color.WHITE;
    private static GameObject cloud;

    public static GameObject create(Vector2 position, GameObjectCollection gameObjects, Vector2 windowDimensions)
    {
        cloud = new Cloud(Vector2.ZERO, new Vector2(CLOUD_WIDTH, CLOUD_HEIGHT), new RectangleRenderable(CLOUD_COLOR), windowDimensions);
        cloud.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        //cloud.setCenter(new Vector2(cloud.getDimensions().x() + 100, cloud.getDimensions().y() + 25));
        cloud.setCenter(new Vector2(position));
        gameObjects.addGameObject(cloud, Layer.BACKGROUND + 3);
        return cloud;
    }

    public static Vector2 getDimensions()
    {
        return new Vector2(CLOUD_WIDTH, CLOUD_HEIGHT);
    }
}
