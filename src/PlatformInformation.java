import danogl.GameObject;
import danogl.util.Vector2;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class PlatformInformation
{
    public static ArrayList<GameObject> platforms = new ArrayList<>();

    public static GameObject findThePlatform(Vector2 positionOfCharacter)
    {
        AtomicReference<GameObject> returnedPlatform = new AtomicReference<>(null);
        platforms.forEach(platform -> {
            float x1 = platform.getCenter().x() - platform.getDimensions().x() / 2;
            float x2 = platform.getCenter().x() + platform.getDimensions().x() / 2;
            if (x1 < positionOfCharacter.x() && positionOfCharacter.x() < x2) {
                returnedPlatform.set(platform);
            }
        });

        return returnedPlatform.get();
    }

    //UNUSED FUNCTION
    public static GameObject findNearestPlatform(Vector2 positionOfCharacter, String direction)
    {
        GameObject currentPlatform = findThePlatform(positionOfCharacter);
        GameObject newPlatform = null;
        if (direction.equals("right"))
        {
            newPlatform = platforms.get(platforms.indexOf(currentPlatform)+1);
        }
        if (direction.equals("left"))
        {
            newPlatform = platforms.get(platforms.indexOf(currentPlatform)-1);
        }
        return newPlatform;
    }


    // UNUSED FUNCTION FOR NOW
    public static boolean isThereAPlatform(Vector2 positionOfCharacter)
    {
        AtomicBoolean isPlatformExist = new AtomicBoolean(false);
        platforms.forEach(platform -> {
            float x1 = platform.getCenter().x() - platform.getDimensions().x() / 2;
            float x2 = platform.getCenter().x() + platform.getDimensions().x() / 2;
            float y1 = platform.getCenter().y() - platform.getDimensions().y() / 2;
            float y2 = platform.getCenter().y() + platform.getDimensions().y() / 2;




            if (x1 < positionOfCharacter.x() && positionOfCharacter.x() < x2 && y1 < positionOfCharacter.y() && positionOfCharacter.y() < y2) {
                isPlatformExist.set(true);
            }
        });
        return isPlatformExist.get();
    }
}
