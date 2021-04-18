package keyboard;

public class KeyEvent {

    public final int code;
    public final boolean alt;
    public final boolean ctrl;
    public final boolean capsLock;
    public final boolean scrollLock;
    public final boolean numLock;

    public KeyEvent(int code, boolean alt, boolean ctrl, boolean capsLock, boolean scrollLock, boolean numLock) {
        this.code = code;
        this.alt = alt;
        this.ctrl = ctrl;
        this.capsLock = capsLock;
        this.scrollLock = scrollLock;
        this.numLock = numLock;
    }
}
