package keyboard;

public class KeyBuffer {
    public static final int DEFAULT_SIZE = 128;

    private final KeyEvent[] buffer;
    private int start;
    private int end;

    public KeyBuffer() {
        this.buffer = new KeyEvent[DEFAULT_SIZE];
        this.start = 0;
        this.end = 0;
    }

    public KeyBuffer(int size) {
        this.buffer = new KeyEvent[size];
        this.start = 0;
        this.end = 0;
    }

    @SJC.Inline
    public boolean isEmpty() {
        return start == end;
    }

    public int getSize() {
        int size = end - start;
        if (size < 0)
            size += buffer.length;
        return size;
    }

    public void add(KeyEvent keyEvent) {
        buffer[end++] = keyEvent;

        // didn't do modulo arithmetic because
        // CPU might be more efficient taking likely branches
        if(end == buffer.length)
            end = 0;
        if(start == end) {
            start++;
            if(start == buffer.length)
                start = 0;
        }
    }

    public KeyEvent pop() {
        if (isEmpty())
            return null;
        KeyEvent value = buffer[start++];
        if(start == buffer.length)
            start = 0;
        return value;
    }

    public KeyEvent peek() {
        if(isEmpty())
            return null;
        return buffer[start];
    }
}
