import BackgroundObjects.CloudMaker;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.*;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class PlatformerGameManager extends GameManager
{
    private static final Color BACKGROUND_COLOR = Color.decode("#80C6E5");
    private static final Color NORMAL_PLATFORM_COLOR = new Color(212, 123, 74);

    private static final Color LAVA_PLATFORM_COLOR = new Color(255, 0, 0);

    private static final Color PLAYER_COLOR = Color.DARK_GRAY;



    private static final float StartingPointX = -100;
    private static final float SUN_CYCLE = 15;
    private static final float FLAG_SIZE = 100;
    private static final Color CHECKPOINT_COLOR = Color.cyan;
    private static final float CHECKPOINT_SIZE = 100f;
    private static Vector2 playerStartingPosition;

    private static Random rnd = new Random();
    private static float LavaYPos;

    private static final float PLAYER_HEIGHT = 60;
    private static final float PLAYER_WIDTH = 60;
    private ImageReader imageReader;

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        this.imageReader = imageReader;
        super.initializeGame(imageReader, soundReader, inputListener, windowController);


        windowController.setExitButton(KeyEvent.VK_Y);


        //background
        Renderable backgroundImg = imageReader.readImage("Blocks/natureBackground.png", false);
        GameObject background = new GameObject(
                Vector2.ZERO,
                windowController.getWindowDimensions(),
                backgroundImg

        ); // new RectangleRenderable(BACKGROUND_COLOR)
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);

        Vector2 windowDimensions = windowController.getWindowDimensions();
        // platform
        LavaYPos = windowDimensions.y();




        // Player

        playerStartingPosition = new Vector2(100, windowDimensions.y() - 500);


        Renderable avatarAnimation = imageReader.readImage("Avatar/AvatarAnimationMiddle.png", true);
        GameObject player = new Player(Vector2.ZERO, new Vector2(PLAYER_WIDTH, PLAYER_HEIGHT), avatarAnimation, inputListener, imageReader, windowController);
        player.setCenter(playerStartingPosition);
        gameObjects().addGameObject(player);
        
        // Flag
        Renderable flagImg = imageReader.readImage("Objects/Flag.png", true);
        GameObject flag = new Flag(Vector2.ZERO, new Vector2(FLAG_SIZE, FLAG_SIZE), flagImg, windowController);
        flag.setCenter(new Vector2(10625, windowDimensions.y() * 0.2f));
        gameObjects().addGameObject(flag);

        // checkpoints
        Renderable checkpointImg = new RectangleRenderable(CHECKPOINT_COLOR);
        createCheckpoints(checkpointImg, windowDimensions);




        Camera camera = new Camera(player, new Vector2(0, -50),
                windowController.getWindowDimensions(), windowController.getWindowDimensions());
        setCamera(camera);
        // cloud

        //createClouds(windowDimensions);


        createPlatforms(windowDimensions);
        createEnemies(windowDimensions, player);

        windowController.setTargetFramerate(80);


    }

    private void createClouds(Vector2 windowDimensions)
    {
        float cloudDimensionY = CloudMaker.getDimensions().y();
        float cloudDimensionX = CloudMaker.getDimensions().x();
        CloudMaker.create(new Vector2(rnd.nextFloat(-3, -1) * cloudDimensionX, cloudDimensionY + rnd.nextFloat(0, 50)), gameObjects(), windowDimensions);
        CloudMaker.create(new Vector2(rnd.nextFloat(-6, -3) * cloudDimensionX, cloudDimensionY + rnd.nextFloat(0, 50)), gameObjects(), windowDimensions);
        CloudMaker.create(new Vector2(rnd.nextFloat(-9, -6) * cloudDimensionX, cloudDimensionY + rnd.nextFloat(0, 50)), gameObjects(), windowDimensions);
        CloudMaker.create(new Vector2(rnd.nextFloat(-12, -9) * cloudDimensionX, cloudDimensionY + rnd.nextFloat(0, 50)), gameObjects(), windowDimensions);
        CloudMaker.create(new Vector2(rnd.nextFloat(-15, -12) * cloudDimensionX, cloudDimensionY + rnd.nextFloat(0, 50)), gameObjects(), windowDimensions);

    }

    private void createEnemies(Vector2 windowDimensions, GameObject player)
    {
        EnemiesFactory enemiesFactory = new EnemiesFactory(imageReader, windowDimensions, gameObjects(), player);


        enemiesFactory.createEnemy("enemy1", new Vector2(3200, windowDimensions.y() * 0.4f), 0);

        enemiesFactory.createEnemy("ninja", new Vector2(Vector2.ZERO), 0);
        enemiesFactory.createEnemy("ninja", new Vector2(Vector2.ZERO), 0);
        enemiesFactory.createEnemy("ninja", new Vector2(Vector2.ZERO), 0);


        enemiesFactory.createEnemy("blob", new Vector2(9000, windowDimensions.y() * 0.2f), 0);
        enemiesFactory.createEnemy("enemy1", new Vector2(9500, windowDimensions.y() * 0.2f), 0);
        enemiesFactory.createEnemy("enemy1", new Vector2(10000, windowDimensions.y() * 0.2f), 0);

        enemiesFactory.createEnemy("enemy2", new Vector2(2075, windowDimensions.y() * 1.5f), 850);
        enemiesFactory.createEnemy("enemy2", new Vector2(2475, windowDimensions.y() * 1.5f), 850);
        enemiesFactory.createEnemy("enemy2", new Vector2(2875, windowDimensions.y() * 1.5f), 850);
        enemiesFactory.createEnemy("enemy2", new Vector2(4075, windowDimensions.y() * 1.5f), 1100);
        enemiesFactory.createEnemy("enemy2", new Vector2(4475, windowDimensions.y() * 1.5f), 1100);
        enemiesFactory.createEnemy("enemy2", new Vector2(4875, windowDimensions.y() * 1.5f), 1100);
        enemiesFactory.createEnemy("enemy2", new Vector2(5275, windowDimensions.y() * 1.5f), 1100);

        //enemiesFactory.createEnemy("enemy3", new Vector2(500, windowDimensions.y() * 0.2f), 0);


        enemiesFactory.createEnemy("spike", new Vector2(900, 227.1f), 0);
        enemiesFactory.createEnemy("spike", new Vector2(5707, 394.5f), 0);
        enemiesFactory.createEnemy("spike", new Vector2(5907, 394.5f), 0);
        enemiesFactory.createEnemy("spike", new Vector2(6207, 394.5f), 0);
        enemiesFactory.createEnemy("spike", new Vector2(6282, 394.5f), 0);
        enemiesFactory.createEnemy("spike", new Vector2(6587, 394.5f), 0);
        enemiesFactory.createEnemy("spike", new Vector2(6787, 394.5f), 0);
        enemiesFactory.createEnemy("spike", new Vector2(6987, 394.5f), 0);

        enemiesFactory.createEnemy("enemy1", new Vector2(7700, 350f), 0);
        enemiesFactory.createEnemy("enemy1", new Vector2(8700, 350f), 0);

    }
    private void createPlatforms(Vector2 windowDimensions)
    {
        PlatformMaker.placePlatform(Vector2.of(StartingPointX, windowDimensions.y() - 300), new Vector2(500, 300), gameObjects(), "normal");
        PlatformMaker.placePlatform(Vector2.of(400, windowDimensions.y() - 400), new Vector2(200, 400), gameObjects(), "normal");
        PlatformMaker.placePlatform(Vector2.of(600, windowDimensions.y() - 500), new Vector2(200, 500), gameObjects(), "normal");
        PlatformMaker.placePlatform(Vector2.of(800, windowDimensions.y() - 600), new Vector2(200, 600), gameObjects(), "normal");



        PlatformMaker.placePlatform(Vector2.of(StartingPointX + 1400, windowDimensions.y() - 300), new Vector2(500, 300), gameObjects(), "normal");
        PlatformMaker.placePlatform(Vector2.of(800 + 400, windowDimensions.y() - 400), new Vector2(200, 400), gameObjects(), "normal");
        PlatformMaker.placePlatform(Vector2.of(600 + 400, windowDimensions.y() - 500), new Vector2(200, 500), gameObjects(), "normal");
        PlatformMaker.placePlatform(Vector2.of(400 + 400, windowDimensions.y() - 600), new Vector2(200, 600), gameObjects(), "normal");

        PlatformMaker.placePlatform(Vector2.of(1800, windowDimensions.y() * 0.9f), new Vector2(150, 100), gameObjects(), "normal");
        PlatformMaker.placePlatform(Vector2.of(2200, windowDimensions.y() * 0.9f), new Vector2(150, 100), gameObjects(), "normal");
        PlatformMaker.placePlatform(Vector2.of(2600, windowDimensions.y() * 0.9f), new Vector2(150, 100), gameObjects(), "normal");
        PlatformMaker.placePlatform(Vector2.of(3000, windowDimensions.y() * 0.9f), new Vector2(150, 100), gameObjects(), "normal");
        PlatformMaker.placePlatform(Vector2.of(3000, windowDimensions.y() * 0.85f), new Vector2(150, 50), gameObjects(), "trampoline");
        PlatformMaker.placePlatform(Vector2.of(3150, windowDimensions.y() * 0.5f), new Vector2(800, 500), gameObjects(), "normal");

        PlatformMaker.placePlatform(Vector2.of(4200, windowDimensions.y() * 0.5f), new Vector2(150, 800), gameObjects(), "normal");
        PlatformMaker.placePlatform(Vector2.of(4600, windowDimensions.y() * 0.35f), new Vector2(150, 800), gameObjects(), "normal");
        PlatformMaker.placePlatform(Vector2.of(5000, windowDimensions.y() * 0.5f), new Vector2(150, 800), gameObjects(), "normal");
        PlatformMaker.placePlatform(Vector2.of(5400, windowDimensions.y() * 0.5f), new Vector2(1650, 800), gameObjects(), "normal");
        PlatformMaker.placePlatform(Vector2.of(7340, windowDimensions.y() * 0.5f), new Vector2(610, 800), gameObjects(), "normal");
        PlatformMaker.placePlatform(Vector2.of(8240, windowDimensions.y() * 0.5f), new Vector2(610, 800), gameObjects(), "normal");

        PlatformMaker.placePlatform(Vector2.of(7050, windowDimensions.y() * 0.4f), new Vector2(300, 800), gameObjects(), "normal");
        PlatformMaker.placePlatform(Vector2.of(7950, windowDimensions.y() * 0.4f), new Vector2(300, 800), gameObjects(), "normal");
        PlatformMaker.placePlatform(Vector2.of(8850, windowDimensions.y() * 0.4f), new Vector2(1500, 800), gameObjects(), "normal");

        PlatformMaker.placePlatform(Vector2.of(10550, windowDimensions.y() * 0.25f), new Vector2(150, 800), gameObjects(), "normal");


        // lava
        PlatformMaker.placePlatform(Vector2.of(windowDimensions.x()  * -2, windowDimensions.y()), new Vector2(windowDimensions.x() * 10, LavaYPos), gameObjects(), "lava");

    }

    public void createCheckpoints(Renderable checkpointImg, Vector2 windowDimensions)
    {
        CheckpointMaker checkpointMaker = new CheckpointMaker(gameObjects());

        checkpointMaker.create(new Vector2(4675, windowDimensions.y() * 0.35f), new Vector2(CHECKPOINT_SIZE, CHECKPOINT_SIZE), checkpointImg);
    }




    public static void main(String[] args) {
        SongPlayer songPlayer = new SongPlayer();
        songPlayer.playSong("C:\\Users\\uriya\\Desktop\\PlatformerGameMusic.wav");
        new PlatformerGameManager().run();

    }
}

