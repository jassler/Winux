package os.commands;

public class CC {

    public static void causeCC() {
        sthElse(-1, 'F', (byte) 0x0F);
    }

    private static void sthElse(int arg1, char arg2, byte arg3) {
        more();
    }

    private static void more() {
        MAGIC.inline(0xCC);
    }
}
