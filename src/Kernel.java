package kernel;

import screen.*;
import interrupt.*;

public class Kernel {
  public static void main() {
    MAGIC.doStaticInit();
    Interrupt.initPic();
    Screen.clear();

    MAGIC.inline(0xCC);

    BIOS.regs.EAX = 0x0013;
    BIOS.rint(0x10);

    int coord = 0xA0000;
    for(int y = 0; y < 200; y++) {
      for(int x = 0; x < 320; x++) {
        MAGIC.wMem32(coord++, (y * 16 / 200) | 0x20);
      }
    }

    sleep(10);

    BIOS.regs.EAX = 0x0003;
    BIOS.rint(0x10);

    while(true);
  }

  public static void sleep(int seconds) {
    // https://wiki.osdev.org/PIT
    // https://www.visualmicro.com/page/Timer-Interrupts-Explained.aspx

    // TODO
    while(true) {
      // Clear interrupt flag; interrupts disabled when interrupt flag cleared
      MAGIC.inline("cli");
      if(seconds == 0)
        break;
      
      // Set interrupt flag; external, maskable interrupts enabled at the end of the next instruction
      MAGIC.inline("sti");
    }
  }

  private static void testCursor() {
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
  }
}
