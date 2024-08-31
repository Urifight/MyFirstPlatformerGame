import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class EnemiesFactory {
    private static final float ENEMY_WIDTH = 75;
    private static final float ENEMY_HEIGHT = 75;

    private static final float SPIKE_BALL_SIZE = 200;

    private ImageReader imageReader;
    private Vector2 windowDimenions;
    private GameObjectCollection gameObjects;
    private GameObject player;

    public EnemiesFactory(ImageReader imageReader, Vector2 windowDimenions, GameObjectCollection gameObjects, GameObject player) {

        this.imageReader = imageReader;
        this.windowDimenions = windowDimenions;
        this.gameObjects = gameObjects;
        this.player = player;
    }

    public void createEnemy(String enemyType, Vector2 position, float lengthOfJump) {

        NinjaFacade ninjaFacade = new NinjaFacade(windowDimenions);

        Renderable spikeImg = imageReader.readImage("Avatar/Spike4.png", false);

        Renderable enemy1Image = imageReader.readImage("Avatar/Enemy1.png", false);
        Renderable enemy2Image = imageReader.readImage("Avatar/Enemy2.png", false);
        Renderable enemy3Image = imageReader.readImage("Avatar/Chaser.png", false);
        Renderable enemyBlobImage = imageReader.readImage("Avatar/Blob.png", false);
        Renderable ninjaImg = imageReader.readImage("Avatar/Ninja.png", false);

        Renderable shurikenImg = imageReader.readImage("Avatar/Shuriken.png", false);

        Renderable spikeBallImg = imageReader.readImage("Avatar/spikeBall.png", false);

        GameObject enemy = null;
        switch (enemyType) {
            case "enemy1":
                //enemy = new Enemy(Vector2.ZERO, new Vector2(ENEMY_WIDTH, ENEMY_HEIGHT), enemy1Image, new Vector2(3200, windowDimenions.y() * 0.2f));
                enemy = new Enemy(Vector2.ZERO, new Vector2(ENEMY_WIDTH, ENEMY_HEIGHT), enemy1Image, position);
                break;

            case "enemy2":
                //enemy = new Enemy2(Vector2.ZERO, new Vector2(ENEMY_WIDTH, ENEMY_HEIGHT), enemy2Image, new Vector2(2875, windowDimenions.y() * 1.5f), 3f);
                enemy = new Enemy2(Vector2.ZERO, new Vector2(ENEMY_WIDTH, ENEMY_HEIGHT), enemy2Image, position, 2.0f, lengthOfJump);
                break;

            case "enemy3":
                enemy = new Enemy3(Vector2.ZERO, new Vector2(ENEMY_WIDTH, ENEMY_HEIGHT), enemy3Image, position, player);
                break;

            case "blob":
                enemy = new Blob(Vector2.ZERO, new Vector2(ENEMY_WIDTH, ENEMY_HEIGHT), enemyBlobImage, position, gameObjects);
                break;

            case "ninja":
                enemy = new Ninja(Vector2.ZERO, new Vector2(ENEMY_WIDTH, ENEMY_HEIGHT), ninjaImg, gameObjects, shurikenImg, ninjaFacade.getNinjaPosition(), player);
                break;

            case "spike":
                enemy = new Spike(Vector2.ZERO, new Vector2(ENEMY_WIDTH, ENEMY_HEIGHT), spikeImg, position);
                break;

            case "spikeBall":
                enemy = new SpikeBall(Vector2.ZERO, new Vector2(SPIKE_BALL_SIZE, SPIKE_BALL_SIZE), spikeBallImg, gameObjects, position, player);
                break;

        }
        gameObjects.addGameObject(enemy);

    }
}