package creatures;

import huglife.*;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.awt.*;
import java.util.EnumMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * Tests the plip class
 *
 * @author Po
 */

public class TestPlip {

    @Test
    public void setEnergyAndColor() {
        Plip p = new Plip(2.0);
        assertEquals(2.0, p.energy(), 0.01);
        assertEquals(new Color(99, 255, 76), p.color());

        p.setEnergyAndColor(0.1);
        assertEquals(0.1, p.energy(), 0.01);
        assertEquals(new Color(99, 72, 76), p.color());

        p.setEnergyAndColor(3.0);   // separate tests?
        assertEquals(2.0, p.energy(), 0.01);
        assertEquals(new Color(99, 255, 76), p.color());

        p.setEnergyAndColor(-1.0);
        assertEquals(0, p.energy(), 0.01);
        assertEquals(new Color(99, 63, 76), p.color());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTooEnergeticPlip() {
        new Plip(3.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTooWeakPlip() {
        new Plip(-1.0);
    }

    @Test
    public void move() {
        Plip p = new Plip(2.0);
        p.move();
        assertEquals(1.85, p.energy(), 0.01);
        assertEquals(new Color(99, 240, 76), p.color());
        p.move();
        assertEquals(1.70, p.energy(), 0.01);
    }

    @Test
    public void stay() {
        Plip p = new Plip(1.70);
        p.stay();
        assertEquals(1.90, p.energy(), 0.01);
        assertEquals(new Color(99, 245, 76), p.color());
        p.stay();
        assertEquals(2.0, p.energy(), 0.01);
    }

    @Test
    public void attack() {// ?
    }

    @Test
    public void replicate() {
        Plip p = new Plip(2.0);
        double newEnergy = p.energy() / 2;
        Plip q = p.replicate();
        assertNotSame(p, q);
        assertEquals(p.energy(), newEnergy, 0.01);
        assertEquals(new Color(99, 159, 76), p.color());
        assertEquals(q.energy(), newEnergy, 0.01);
        assertEquals(new Color(99, 159, 76), q.color());
    }

    @Test
    public void chooseWhenSurrounded() {
        Plip p = new Plip(1.2);
        EnumMap<Direction, Occupant> surrounded = new EnumMap<>(Direction.class);
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        assertEquals(new Action(Action.Type.STAY), p.chooseAction(surrounded));
    }

    @Test
    public void chooseWithEnoughEnergyAndNotSurrounded() {
        Plip p = new Plip(1.2);
        EnumMap<Direction, Occupant> neighbours = new EnumMap<>(Direction.class);
        neighbours.put(Direction.TOP, new Impassible());
        neighbours.put(Direction.BOTTOM, new Impassible());
        neighbours.put(Direction.LEFT, new Impassible());
        neighbours.put(Direction.RIGHT, new Empty());

        assertEquals(new Action(Action.Type.REPLICATE, Direction.RIGHT), p.chooseAction(neighbours));
    }

    @Test
    @Ignore
    public void chooseWithLessEnergyAndNotSurroundedAndClorus() {
        // given
        Plip p = new Plip(0.1);
        EnumMap<Direction, Occupant> neighbours = new EnumMap<>(Direction.class);
        neighbours.put(Direction.TOP, new Impassible());
        neighbours.put(Direction.BOTTOM, new Impassible());
//        neighbours.put(Direction.LEFT, new Clorus()); TODO when implemented..
        neighbours.put(Direction.RIGHT, new Empty());

        Mockito.mockStatic(HugLifeUtils.class);
        BDDMockito.given(HugLifeUtils.random()).willReturn(0.1); // maybe will work: https://stackoverflow.com/questions/21105403/mocking-static-methods-with-mockito

        // when/then
        assertEquals(new Action(Action.Type.MOVE, Direction.RIGHT), p.chooseAction(neighbours));
    }

    @Test
    @Ignore
    public void chooseWithLessEnergyAndNotSurroundedAndClorusAndHighRandom() {
        // given
        Plip p = new Plip(0.1);
        EnumMap<Direction, Occupant> neighbours = new EnumMap<>(Direction.class);
        neighbours.put(Direction.TOP, new Impassible());
        neighbours.put(Direction.BOTTOM, new Impassible());
//        neighbours.put(Direction.LEFT, new Clorus()); TODO when implemented..
        neighbours.put(Direction.RIGHT, new Empty());

        Mockito.mockStatic(HugLifeUtils.class);
        BDDMockito.given(HugLifeUtils.random()).willReturn(0.7); // maybe will work: https://stackoverflow.com/questions/21105403/mocking-static-methods-with-mockito

        // when/then
        assertEquals(new Action(Action.Type.STAY), p.chooseAction(neighbours));
    }

    @Test
    public void chooseWithLessEnergyAndNotSurroundedAndNoClorus() {
        Plip p = new Plip(0.7);
        EnumMap<Direction, Occupant> neighbours = new EnumMap<>(Direction.class);
        neighbours.put(Direction.TOP, new Impassible());
        neighbours.put(Direction.BOTTOM, new Impassible());
        neighbours.put(Direction.LEFT, new Impassible());
        neighbours.put(Direction.RIGHT, new Empty());

        assertEquals(new Action(Action.Type.STAY), p.chooseAction(neighbours));
    }
} 
