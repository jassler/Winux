package keyboard;

public class KeyEvent {

    public final int code;
    public final boolean alt;
    public final boolean ctrl;

    public KeyEvent(int code, boolean alt, boolean ctrl) {
        this.code = code;
        this.alt = alt;
        this.ctrl = ctrl;
    }
}
