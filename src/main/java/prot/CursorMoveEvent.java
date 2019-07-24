package prot;

public class CursorMoveEvent{
    private long windowPointer;

    private double x;

    private double y;

    CursorMoveEvent(long windowPointer, double x, double y) {
        this.windowPointer = windowPointer;
        this.x = x;
        this.y = y;
    }

    long getWindowPointer() {
        return windowPointer;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public EventType getEventType() {
        return EventType.CURSOR_MOVE;
    }
}
