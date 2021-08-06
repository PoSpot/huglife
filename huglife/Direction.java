package huglife;

public enum Direction {
    TOP, BOTTOM, LEFT, RIGHT;

    Direction reverse() {
        return switch (this) {
            case TOP -> BOTTOM;
            case BOTTOM -> TOP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }
}