package creatures;

import huglife.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

import static huglife.HugLifeUtils.randomEntry;

public class Plip extends Creature {

    public static final int GREEN_MAX = 255;
    public static final int GREEN_MIN = 63;
    public static final double ENERGY_MAX = 2d;
    public static final double ENERGY_MIN = 0d;
    public static final double MOVE_PROBABILITY = 0.25;
    // colors
    private final int r = 99;
    private final int b = 76;
    private int g;

    public Plip(double energy) {
        super(Type.PLIP);
        if (energy < ENERGY_MIN || energy > ENERGY_MAX) {
            throw new IllegalArgumentException("Plip energy is out of bound: " + energy);
        }
        this.setEnergyAndColor(energy);
    }

    private void setEnergyAndColor(double energy) {

        if (energy > ENERGY_MAX) { energy = ENERGY_MAX; }
        if (energy < ENERGY_MIN) { energy = ENERGY_MIN; }
        this.energy = energy;

        g = (int)(GREEN_MIN + (GREEN_MAX - GREEN_MIN)/ENERGY_MAX*this.energy);
        if (g < GREEN_MIN || g > GREEN_MAX) {throw new Error("Plip green is out of bound: " + g);}
    }

    @Override
    public void move() {
        this.setEnergyAndColor(energy - 0.15);
    }

    @Override
    public void attack(Creature c) {}

    @Override
    public Plip replicate() {
        this.setEnergyAndColor(energy/2);
        return new Plip(energy);
    }

    @Override
    public void stay() {
        this.setEnergyAndColor(energy + 0.2);
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {

        List<Direction> empties = getNeighborsOfType(neighbors, Type.EMPTY);
        if (empties.isEmpty()) {
            return new Action(Action.Type.STAY);
        }

        if (energy > 1d) {
            return new Action(Action.Type.REPLICATE);
        }

        List<Direction> cloruses = getNeighborsOfType(neighbors, Type.CLORUS);
        if (!cloruses.isEmpty() && HugLifeUtils.random() < MOVE_PROBABILITY) {
            Direction toDir = randomEntry(empties);
            return new Action(Action.Type.MOVE, toDir);
        }

        return new Action(Action.Type.STAY);
    }

    @Override
    public Color color() {
        return color(r, g, b);
    }
}
