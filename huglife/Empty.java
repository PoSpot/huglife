package huglife;

import java.awt.*;

public class Empty extends Occupant {
    public Empty() {
        super(Type.EMPTY);
    }

    /** Returns hardcoded black */
    public Color color() {
        return color(255, 255, 255);
    }    
}