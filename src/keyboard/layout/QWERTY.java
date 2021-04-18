package keyboard.layout;

import keyboard.ASCII;

public class QWERTY {

    public static int mapKey(int set, int code) {
        // set should only be 0, 1 or 2
        if(set == 0) {
            if(code == 30)
                return ASCII.a;
            return ASCII.b;

        } else if(set == 1) {
            if(code == 30)
                return ASCII.A;
            return ASCII.B;

        } else {
            if(code == 30)
                return ASCII._6;
            return ASCII._9;

        }
    }

}
