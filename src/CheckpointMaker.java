import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class CheckpointMaker
{
    private static GameObjectCollection gameObjects;

    public CheckpointMaker(GameObjectCollection gameObjects) {
        super();
        this.gameObjects = gameObjects;
    }

    public void create(Vector2 position, Vector2 size, Renderable checkpointImg)
    {
        GameObject checkpoint = new Checkpoint(Vector2.ZERO, size, checkpointImg);
        checkpoint.setCenter(position);
        gameObjects.addGameObject(checkpoint);

    }
}
