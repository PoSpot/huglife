package huglife;

public enum Direction {
    TOP, BOTTOM, LEFT, RIGHT;

    Direction reverse() {
        switch (this) {
            case TOP: return BOTTOM;
            case BOTTOM: return TOP;
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
            default: throw new IllegalArgumentException("Unsupported direction.");
        }
    }
}