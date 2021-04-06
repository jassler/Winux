package kernel;

import screen.*;

public class Kernel {
  public static void main() {
    Screen.clear();

    Cursor c = new Cursor();
    c.print("Hi there");

    c.setColor(Color.BLUE, Color.RED);
    c.setCursor(Screen.WIDTH - 5, 2);
    c.print("Bye there");

    c.setColor(Color.VIOLET, Color.GRAY);
    c.println();
    c.println("Now onto numbers");
    c.println(1000);
    c.println(-1000);
    c.println(0);
    c.println(123);
    c.println(-1230);
    
    c.println();
    c.setColor(Color.GRAY, Color.BLACK);
    c.println("Now onto hex");
    c.print("42 = ");
    c.printHex((byte) 42);
    c.println();
    
    c.print("-1 = ");
    c.printHex((byte) -1);
    c.println();
    
    c.print("420 = ");
    c.printHex((short) 420);
    c.println();

    c.print("1.000.000 = ");
    c.printHex((int) 1000000);
    c.println();

    c.print("1.000.000.000 = ");
    c.printHex((long) 1000000000);
    c.println();

    while(true);
  }
}
