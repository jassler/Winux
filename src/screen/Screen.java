package screen;

public class Screen {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 25;

    public static final int MEM_START = 0xB8000;
    public static final int MEM_END = MEM_START + (WIDTH * HEIGHT * 2);

    public static void clear() {
        int i;
        for(i = MEM_START; i < MEM_END; i++) {
            MAGIC.wMem8(i, (byte) 0);
        }
    }
}
