package huglife;

import java.awt.*;

/**
 *  @author Josh Hug
 *  Represents possible occupants of the grid world.
 *  Intended for extension by:
 *     Creature, Empty, and Impassible only.
 */
public abstract class Occupant {

    public enum Type {EMPTY, IMPASSABLE, SAMPLE_CREATURE, PLIP, CLORUS}

    /** Type for this type of Occupant. */
    protected final Type type;

    /** Creates an Occupant with the given type */
    protected Occupant(Type type) {
        this.type = type;
    }

    /** Returns the type of this occupant. */
    public Type getType() {
        return type;
    }

    /** Returns a Color object given R, G, and B values.
     *  Intended for use by subtypes so they don't have to import
     *  or think about colors.
     */
    protected static Color color(int r, int g, int b) {
        return new Color(r, g, b);
    }

    /** Required method that returns a color. */
    public abstract Color color();
}
