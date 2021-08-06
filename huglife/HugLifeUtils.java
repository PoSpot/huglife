package huglife;

import java.util.List;
import java.util.Random;

/** Utilities for lab 5
 *  @author Josh Hug
 */
public interface HugLifeUtils {
    Random r = new Random();

    /** Returns a random number uniformly between 0 and 1 */
    static double random() {
        return r.nextDouble();
    }

    /** Returns a random number uniformly between min and max inclusive
        Stolen from: http://stackoverflow.com/questions/363681 */
    static int randomInt(int min, int max) {
        return r.nextInt((max - min) + 1) + min;
    }

    /** Returns a random number uniformly between 0 and max */
    static int randomInt(int max) {
        return randomInt(0, max);
    }

    /** Returns a random number uniformly between 0 and max */
    static Direction randomEntry(List<Direction> directions) {
        var dirIndex = randomInt(directions.size() - 1);
        return directions.get(dirIndex);
    }
}
