package os.utils.stringTemplate;

public class NumberWidths {

    public static int getIntWidth(int n) {
        int w = 0;
        if(n < 0) {w++; n = -n;}
        do {n /= 10; w++;
        } while(n > 0);
        return w;
    }

    public static int getLongWidth(long n) {
        int w = 0;
        if(n < 0) {w++; n = -n;}
        do {n /= 10; w++;
        } while(n > 0);
        return w;
    }
}
