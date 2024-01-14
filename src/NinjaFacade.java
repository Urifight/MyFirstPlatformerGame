import danogl.util.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NinjaFacade
{

    private static List <Float> takenSpots = new ArrayList<>();
    private static Vector2 windowDimensions;

    public NinjaFacade(Vector2 windowDimensions) {

        NinjaFacade.windowDimensions = windowDimensions;
    }

    static Random rnd = new Random();
    static float[] possiblePositionsX = {283.63f, 482.52f, 689.98f, 1099.62f, 1300.74f, 1576.06f, 1874.46f, 2246.74f, 2658.26f, 3341.73f, 3564.80f, 3784.49f, 4275.51f, 4667.77f, 5059.39f, 5546.50f, 6053.97f, 6404.94f, 6410.66f, 6415.36f, 6684.96f, 6881.58f, 7185.89f, 7189.59f, 7195.20f, 7199.68f, 7203.59f, 7207.52f, 7214.09f, 7218.78f, 8119.49f, 8123.15f, 8127.59f, 8132.21f, 8136.98f, 8141.67f, 8145.46f, 8149.37f, 8153.12f, 8156.93f, 8160.64f, 9030.54f, 9877.25f, 9881.17f, 9888.25f, 9892.99f, 9897.04f, 9902.43f, 9906.76f, 9911.53f, 9916.12f};
    public Vector2 getNinjaPosition()
    {
        int randomNumber = roll();


        while (takenSpots.contains(possiblePositionsX[randomNumber]))
        {
            randomNumber = roll();

        }
        float chosenXPosition = possiblePositionsX[randomNumber];
        float yPosition = windowDimensions.y() * 0.2f;
        return new Vector2(chosenXPosition, yPosition);
    }

    private int roll()
    {
        return rnd.nextInt(possiblePositionsX.length);
    }
}
