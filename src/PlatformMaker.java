import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.components.GameObjectPhysics;
import danogl.gui.ImageReader;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.ArrayList;

public class PlatformMaker
{
    //private static final Color NORMAL_PLATFORM_COLOR = new Color(212, 123, 74);
    private static final Color NORMAL_PLATFORM_COLOR = Color.decode("#74B72E");



    private static final Color LAVA_PLATFORM_COLOR = Color.decode("#2d5069");
    private static final Color TRAMPOLINE_PLATFORM_COLOR = Color.GREEN;
     private static ImageReader imageReader;


    public static void placePlatform(Vector2 pos, Vector2 size, GameObjectCollection gameObjectCollection, String platformType)
    {
        PlatformMaker.imageReader = imageReader;
        GameObject platform = null;
        switch (platformType)
        {
            case "normal":
                platform = new GameObject(pos, size, new RectangleRenderable(NORMAL_PLATFORM_COLOR));
                platform.physics().preventIntersectionsFromDirection(Vector2.ZERO);
                platform.setTag("normal");
                break;

            case "lava":
                platform = new GameObject(pos, size, new RectangleRenderable(LAVA_PLATFORM_COLOR));
                platform.setTag("lava");
                break;

            case "trampoline":
                platform = new GameObject(pos, size, new RectangleRenderable(TRAMPOLINE_PLATFORM_COLOR));
                platform.setTag("trampoline");

                break;
        }

        assert platform != null;
        if (platform.getTag().equals("normal"))
            PlatformInformation.platforms.add(platform);

        platform.physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        gameObjectCollection.addGameObject(platform);

    }
}
