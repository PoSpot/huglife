package huglife;

import java.awt.*;

public class Impassible extends Occupant {
    public Impassible() {
        super(Type.IMPASSABLE);
    }

    /** Returns hardcoded black */
    public Color color() {
        return color(0, 0, 0);
    }    
}