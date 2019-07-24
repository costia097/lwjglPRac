package prot;

class CursorMouseButtonEvent {

    private long windowPointer;

    private int button;

    private int action;

    private int mods;

    CursorMouseButtonEvent(long windowPointer, int button, int action, int mods) {
        this.windowPointer = windowPointer;
        this.button = button;
        this.action = action;
        this.mods = mods;
    }

    long getWindowPointer() {
        return windowPointer;
    }

    int getButton() {
        return button;
    }

    int getAction() {
        return action;
    }

    public int getMods() {
        return mods;
    }
}
