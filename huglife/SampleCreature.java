package huglife;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * Example of a creature you might create for your world.
 * <p>
 * The SampleCreature is an immortal pacifist that wanders the
 * world eternally, never replicating or attacking.
 * <p>
 * SCs doesn't like crowds, and if surrounded on three sides, will
 * move into any available space.
 * <p>
 * Even if not surrounded, the SC will take a step with 20%
 * probability towards any empty space nearby.
 * <p>
 * If a SampleCreature stands still, its color will change slightly,
 * but only in the red portion of its color.
 *
 * @author Josh Hug
 */
public class SampleCreature extends Creature {
    /**
     * red color.
     */
    private int r = 155;
    /**
     * green color.
     */
    private int g = 61;
    /**
     * blue color.
     */
    private final int b = 76;
    /**
     * probability of taking a move when ample space available.
     */
    private final double moveProbability = 0.2;// 0.2; TODO see how is used
    /**
     * degree of color shift to allow.
     */
    private final int colorShift = 5;//5;
    /**
     * fraction of energy to retain when replicating.
     */
    private final double repEnergyRetained = 0.3;
    /**
     * fraction of energy to bestow upon offspring.
     */
    private final double repEnergyGiven = 0.65;
    private boolean sawWall = false;


    /**
     * Creates a sample creature with energy E. This
     * value isn't relevant to the life of a SampleCreature, since
     * its energy should never decrease.
     */
    public SampleCreature(double e) {
        super("samplecreature");
        energy = e;
    }

    /**
     * Default constructor: Creatures creature with energy 1.
     */
    public SampleCreature() {
        this(1);
    }

    /**
     * Uses method from Occupant to return a color based on personal.
     * r, g, b values
     */
    public Color color() {
        return color(r, g, b);
    }

    /**
     * Do nothing, SampleCreatures are pacifists and won't pick this
     * action anyway. C is safe, for now.
     */
    public void attack(Creature c) {
    }

    /**
     * Nothing special happens when a SampleCreature moves.
     */
    public void move() {
        if (sawWall) {
            swapGreenAndRed();
            sawWall = false;
        }
    }

    private void swapGreenAndRed() {
        int i = g;
        g = r;
        r = i;
    }

    /**
     * If a SampleCreature stands still, its red color shifts slightly, with
     * special code to keep it from going negative or above 255.
     * <p>
     * You will probably find the HugLifeUtils library useful for generating
     * random information.
     */
    public void stay() {
        r += HugLifeUtils.randomInt(-colorShift, colorShift);
        r = Math.min(r, 255);
        r = Math.max(r, 0);
    }

    /**
     * Sample Creatures take actions according to the following rules about
     * NEIGHBORS:
     * 1. If surrounded on three sides, move into the empty space.
     * 2. Otherwise, if there are any empty spaces, move into one of them with
     * probabiltiy given by moveProbability.
     * 3. Otherwise, stay.
     * <p>
     * Returns the action selected.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {

//        return chooseSimpleCreature(neighbors);

//        return chooseReplicateOnWall(neighbors);

        return chooseMoveAwayFromWall(neighbors);
    }

    private Action chooseSimpleCreature(Map<Direction, Occupant> neighbors) {

        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        if (empties.size() == 1) {
            Direction moveDir = empties.get(0);
            return new Action(Action.ActionType.MOVE, moveDir);
        }

        if (empties.size() > 1) {
            if (HugLifeUtils.random() < moveProbability) {
                Direction moveDir = HugLifeUtils.randomEntry(empties);
                return new Action(Action.ActionType.MOVE, moveDir);
            }
        }

        return new Action(Action.ActionType.STAY);
    }

    private Action chooseReplicateOnWall(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        if (empties.size() == 1) {
            Direction moveDir = empties.get(0);
            return new Action(Action.ActionType.MOVE, moveDir);
        }

        // Die in corner
        List<Direction> walls = getNeighborsOfType(neighbors, "impassible");
        if (walls.size() > 1) {
            return new Action(Action.ActionType.DIE);
        }

        // Replicate on wall
//        List<Direction> walls = getNeighborsOfType(neighbors, "impassible");
        if (!walls.isEmpty() && !empties.isEmpty()) {
            Direction moveDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.REPLICATE, moveDir);
        }

        if (empties.size() > 1) {
            if (HugLifeUtils.random() < moveProbability) {
                Direction moveDir = HugLifeUtils.randomEntry(empties);
                return new Action(Action.ActionType.MOVE, moveDir);
            }
        }

        return new Action(Action.ActionType.STAY);
    }

    private Action chooseMoveAwayFromWall(Map<Direction, Occupant> neighbors) {

        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        if (empties.size() == 1) {
            Direction moveDir = empties.get(0);
            return new Action(Action.ActionType.MOVE, moveDir);
        }

        // move away from wall and change color
        List<Direction> walls = getNeighborsOfType(neighbors, "impassible");
        if (walls.size() == 1) {
            Direction away = walls.get(0).reverse();
            if (empties.contains(away)) {
                sawWall = true;
                return new Action(Action.ActionType.MOVE, away);
            }
        }

        if (empties.size() > 1) {
            if (HugLifeUtils.random() < moveProbability) {
                Direction moveDir = HugLifeUtils.randomEntry(empties);
                return new Action(Action.ActionType.MOVE, moveDir);
            }
        }

        return new Action(Action.ActionType.STAY);
    }

    /**
     * If a SampleCreature were to replicate, it would keep only 30% of its
     * energy, and a new baby creature would be returned that possesses 65%,
     * with 5% lost to the universe.
     * <p>
     * However, as you'll see above, SampleCreatures never choose to
     * replicate, so this method should never be called. It is provided
     * because the Creature class insist we know how to replicate boo.
     * <p>
     * If somehow this method were called, it would return a new
     * SampleCreature.
     */
    public SampleCreature replicate() {
        energy = energy * repEnergyRetained;
        double babyEnergy = energy * repEnergyGiven;
        return new SampleCreature(babyEnergy);
    }

}
