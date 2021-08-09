package creatures;

import huglife.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class Clorus extends Creature {

    private final Color color =  new Color(34, 0, 231);

    public Clorus() {
        this(1.0);
    }

    public Clorus(double energy) {
        super(Type.CLORUS);
        if (energy < 0.0) {
            throw new IllegalArgumentException("Energy should be positive: " + energy);
        }
        this.energy = energy;
    }

    @Override
    public void move() {
        energy -= 0.03;
        if (energy < 0) { energy = 0; }
    }

    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    @Override
    public Clorus replicate() {
        energy /= 2;
        return new Clorus(energy);
    }

    @Override
    public void stay() {
        energy -= 0.01;
        if (energy < 0) { energy = 0; }
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {

        List<Direction> empties = getNeighborsOfType(neighbors, Type.EMPTY);
        if (empties.isEmpty()) {
            return new Action(Action.Type.STAY);
        }

        List<Direction> plips = getNeighborsOfType(neighbors, Type.PLIP);
        if (!plips.isEmpty()) {
            Direction toDir = HugLifeUtils.randomEntry(plips);
            return new Action(Action.Type.ATTACK, toDir);
        }

        if (energy >= 1.0){
            Direction toDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.Type.REPLICATE, toDir);
        }

        Direction toDir = HugLifeUtils.randomEntry(empties);
        return new Action(Action.Type.MOVE, toDir);
    }

    @Override
    public Color color() {
        return color;
    }
}