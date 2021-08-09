package creatures;

import huglife.*;
import org.junit.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class ClorusTest {

    @Test(expected = IllegalArgumentException.class)
    public void createTooWeakClorus() {
        new Clorus(-1.0);
    }

    @Test
    public void move_shouldLoseEnergy() {
        Clorus c = new Clorus(2.0);
        c.move();
        assertEquals(1.97, c.energy(), 0.01);
    }

    @Test
    public void move_shouldHaveZeroEnergy_whenTooLow() {
        Clorus c = new Clorus(0.01);
        c.move();
        assertEquals(0.0, c.energy(), 0.01);
    }

    @Test
    public void attack() {
        Clorus c = new Clorus(2.0);
        Plip p = new Plip(2.0);
        c.attack(p);
        assertEquals(4.0, c.energy(), 0.01);
    }

    @Test
    public void replicate() {
        double energy = 2.0;
        Clorus c = new Clorus(energy);
        Clorus offspring = c.replicate();
        assertNotSame(c, offspring);
        assertEquals(energy/2, c.energy(), 0.01);
        assertEquals(energy/2, offspring.energy(), 0.01);

    }

    @Test
    public void stay_shouldLoseEnergy() {
        Clorus c = new Clorus(2.0);
        c.stay();
        assertEquals(1.99, c.energy(), 0.01);
    }

    @Test
    public void stay_shouldHaveZeroEnergy_whenTooLow() {
        Clorus c = new Clorus(0.0);
        c.stay();
        assertEquals(0.0, c.energy(), 0.01);
    }

    @Test
    public void chooseAction_shouldStay_whenSurrounded() {
        Clorus c = new Clorus();
        Map<Direction, Occupant> surrounded = new EnumMap<>(Direction.class);
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action action = c.chooseAction(surrounded);
        assertEquals(new Action(Action.Type.STAY), action);
    }

    @Test
    public void chooseAction_shouldAttack_whenNotSurroundedAndPlip() {
        Clorus c = new Clorus();
        Map<Direction, Occupant> notSurroundedAndPlip = new EnumMap<>(Direction.class);
        notSurroundedAndPlip.put(Direction.BOTTOM, new Empty());
        notSurroundedAndPlip.put(Direction.LEFT, new Empty());
        notSurroundedAndPlip.put(Direction.TOP, new Impassible());
        notSurroundedAndPlip.put(Direction.RIGHT, new Plip());

        Action action = c.chooseAction(notSurroundedAndPlip);
        assertEquals(new Action(Action.Type.ATTACK, Direction.RIGHT), action);
    }

    @Test
    public void chooseAction_shouldReplicate_whenNotSurroundedAndNoPlipsAndEnoughEnergy() {
        Clorus c = new Clorus(2);
        Map<Direction, Occupant> notSurrounded = new EnumMap<>(Direction.class);
        notSurrounded.put(Direction.BOTTOM, new Empty());
        notSurrounded.put(Direction.LEFT, new Impassible());
        notSurrounded.put(Direction.TOP, new Impassible());
        notSurrounded.put(Direction.RIGHT, new Impassible());

        Action action = c.chooseAction(notSurrounded);
        assertEquals(new Action(Action.Type.REPLICATE, Direction.BOTTOM), action);
    }

    @Test
    public void chooseAction_shouldStay_whenNotSurroundedAndNoPlipsAndLessEnergy() {
        Clorus c = new Clorus(0.6);
        Map<Direction, Occupant> notSurrounded = new EnumMap<>(Direction.class);
        notSurrounded.put(Direction.BOTTOM, new Empty());
        notSurrounded.put(Direction.LEFT, new Impassible());
        notSurrounded.put(Direction.TOP, new Impassible());
        notSurrounded.put(Direction.RIGHT, new Impassible());

        Action action = c.chooseAction(notSurrounded);
        assertEquals(new Action(Action.Type.MOVE, Direction.BOTTOM), action);
    }
}