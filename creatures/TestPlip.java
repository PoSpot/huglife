package creatures;

import huglife.Action;
import huglife.Direction;
import huglife.Impassible;
import huglife.Occupant;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/** Tests the plip class   
 *  @author Ganesh Rapolu
 */

public class TestPlip {

    @Test
    public void testBasics() {
        Plip p = new Plip(2);
        assertEquals(2, p.energy(), 0.01);
        assertEquals(new Color(99, 255, 76), p.color());
        p.move();
        assertEquals(1.85, p.energy(), 0.01);
        p.move();
        assertEquals(1.70, p.energy(), 0.01);
        p.stay();
        assertEquals(1.90, p.energy(), 0.01);
        p.stay();
        assertEquals(2.00, p.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Plip p = new Plip(2);
        double newEnergy = p.energy() / 2;
        Plip q = p.replicate();
        assertNotSame(p,q);
        assertEquals(p.energy(), newEnergy, 0.01);
        assertEquals(q.energy(), newEnergy, 0.01);
    }

    @Test
    public void testChoose() {
        Plip p = new Plip(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        //You can create new empties with new Empty();
        //Despite what the spec says, you cannot test for Cloruses nearby yet.
        //Sorry!

        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.Type.STAY);

        assertEquals(expected, actual);
    }

    public static void main(String[] args) {
        // Their way to reu tests
//        System.exit(jh61b.junit.textui.runClasses(TestPlip.class));
    }
} 
