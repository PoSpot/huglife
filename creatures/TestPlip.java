package creatures;

import huglife.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * Tests the plip class
 *
 * @author Po
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({HugLifeUtils.class })
public class TestPlip {

    @Test
    public void setEnergyAndColor_shouldSucceed_onValidInput() {
        Plip p = new Plip(2.0);
        assertEquals(2.0, p.energy(), 0.01);
        assertEquals(new Color(99, 255, 76), p.color());
    }

    @Test
    public void setEnergyAndColor_shouldSetMax_onTooHigh() {
        Plip p = new Plip(2.0);
        p.setEnergyAndColor(3.0);
        assertEquals(2.0, p.energy(), 0.01);
        assertEquals(new Color(99, 255, 76), p.color());
    }

    @Test
    public void setEnergyAndColor_shouldSetZero_onNegative() {
        Plip p = new Plip(2.0);
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
    public void move_shouldLoseEnergy() {
        Plip p = new Plip(2.0);
        p.move();
        assertEquals(1.85, p.energy(), 0.01);
        assertEquals(new Color(99, 240, 76), p.color());
        p.move();
        assertEquals(1.70, p.energy(), 0.01);
    }

    @Test
    public void move_shouldHaveZeroEnergy_whenTooLow() {
        Plip p = new Plip(0.1);
        p.move();
        assertEquals(0.0, p.energy(), 0.01);
        assertEquals(new Color(99, 63, 76), p.color());
    }

    @Test
    public void stay_shouldGainEnergy() {
        Plip p = new Plip(1.70);
        p.stay();
        assertEquals(1.90, p.energy(), 0.01);
        assertEquals(new Color(99, 245, 76), p.color());
        p.stay();
        assertEquals(2.0, p.energy(), 0.01);
    }

    @Test
    public void stay_shouldHaveMaxEnergy_whenTooHigh() {
        Plip p = new Plip(2.0);
        p.stay();
        assertEquals(2.0, p.energy(), 0.01);
        assertEquals(new Color(99, 255, 76), p.color());
    }

    @Test(expected =  UnsupportedOperationException.class)
    public void attack() {
        Plip p = new Plip(1.70);
        p.attack(new SampleCreature());
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
    public void chooseAction_ShouldReturnStay_whenSurrounded() {
        Plip p = new Plip(1.2);
        Map<Direction, Occupant> surrounded = new EnumMap<>(Direction.class);
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        assertEquals(new Action(Action.Type.STAY), p.chooseAction(surrounded));
    }

    @Test
    public void chooseAction_shouldReturnReplicate_withEnoughEnergyAndNotSurrounded() {
        Plip p = new Plip(1.2);
        Map<Direction, Occupant> notSurrounded = new EnumMap<>(Direction.class);
        notSurrounded.put(Direction.TOP, new Impassible());
        notSurrounded.put(Direction.BOTTOM, new Impassible());
        notSurrounded.put(Direction.LEFT, new Impassible());
        notSurrounded.put(Direction.RIGHT, new Empty());

        assertEquals(new Action(Action.Type.REPLICATE, Direction.RIGHT), p.chooseAction(notSurrounded));
    }

    @Test
    public void chooseAction_shouldReturnMove_withLessEnergyAndNotSurroundedAndClorus() {
        // given
        Plip p = new Plip(0.1);
        Map<Direction, Occupant> notSurroundedAndClorus = new EnumMap<>(Direction.class);
        notSurroundedAndClorus.put(Direction.TOP, new Impassible());
        notSurroundedAndClorus.put(Direction.BOTTOM, new Impassible());
        notSurroundedAndClorus.put(Direction.LEFT, new Clorus());
        notSurroundedAndClorus.put(Direction.RIGHT, new Empty());

        // mockito-core can't mock static methods
        // mockito-inline can mock, but can't spy static methods
        // => PowerMockito (with some warnings..)
        PowerMockito.spy(HugLifeUtils.class);   // cos we need the other random methods
        PowerMockito.when(HugLifeUtils.random()).thenReturn(0.1);

        // when/then
        assertEquals(new Action(Action.Type.MOVE, Direction.RIGHT), p.chooseAction(notSurroundedAndClorus));
    }

    @Test
    public void chooseAction_shouldReturnStay_withLessEnergyAndNotSurroundedAndClorusAndHighRandom() {
        // given
        Plip p = new Plip(0.1);
        Map<Direction, Occupant> notSurroundedAndClorus = new EnumMap<>(Direction.class);
        notSurroundedAndClorus.put(Direction.TOP, new Impassible());
        notSurroundedAndClorus.put(Direction.BOTTOM, new Impassible());
        notSurroundedAndClorus.put(Direction.LEFT, new Clorus());
        notSurroundedAndClorus.put(Direction.RIGHT, new Empty());

        // see comment above
        PowerMockito.spy(HugLifeUtils.class);
        PowerMockito.when(HugLifeUtils.random()).thenReturn(0.7);

        // when/then
        assertEquals(new Action(Action.Type.STAY), p.chooseAction(notSurroundedAndClorus));
    }

    @Test
    public void chooseAction_shouldReturnStay_withLessEnergyAndNotSurroundedAndNoClorus() {
        Plip p = new Plip(0.7);
        Map<Direction, Occupant> notSurroundedAndNoClorus = new EnumMap<>(Direction.class);
        notSurroundedAndNoClorus.put(Direction.TOP, new Impassible());
        notSurroundedAndNoClorus.put(Direction.BOTTOM, new Impassible());
        notSurroundedAndNoClorus.put(Direction.LEFT, new Impassible());
        notSurroundedAndNoClorus.put(Direction.RIGHT, new Empty());

        assertEquals(new Action(Action.Type.STAY), p.chooseAction(notSurroundedAndNoClorus));
    }
} 
