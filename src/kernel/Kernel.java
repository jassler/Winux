package kernel;

import commands.Echo;
import commands.MemLayout;
import commands.PCI;
import keyboard.ASCII;
import keyboard.KeyEvent;
import keyboard.KeyboardController;
import screen.*;
import interrupt.*;

public class Kernel {
  private static final Graphic graphic = new Graphic();

  public static void main() {
    MAGIC.doStaticInit();
    Interrupt.initPic();

    Terminal mainTerminal = new Terminal();
    mainTerminal.enableCursor();
    mainTerminal.clear();
    mainTerminal.focus();

    BIOS.regs.EAX = 0x0013;
    BIOS.rint(0x10);

    Graphic g = new Graphic();
    for(int y = 0; y < Graphic.HEIGHT; y++) {
      for(int x = 0; x < Graphic.WIDTH; x++) {
        g.setPtrAndInc((byte) ((y * 16 / 200) | 0x80));
      }
    }
    g.slapLogoOnTop(0, 0x38, 0x67);


    sleep(30);

    BIOS.regs.EAX = 0x0003;
    BIOS.rint(0x10);

    while(true) {
      String cmd = mainTerminal.promptCommand(256);

      if(cmd.startsWith("echo ")) {
        Echo.handle(cmd, mainTerminal);

      } else if(cmd.startsWith("mem")) {
        MemLayout.printMemLayout(mainTerminal);

      } else if(cmd.startsWith("pci")) {
        PCI.printPCI(mainTerminal);
      }

//      if(!KeyboardController.getKeyBuffer().isEmpty()) {
//        KeyEvent k = KeyboardController.getKeyBuffer().pop();
//        if(ASCII.isPrintable(k.code)) {
//          charCursor.print((char) k.code);
//          Terminal.moveCursor(5, 2);
//          if(x++ >= Screen.WIDTH)
//            x = 0;
//
//        } else {
//          debugCursor.print('0');
//          debugCursor.print('x');
//          debugCursor.printHex(k.code);
//          debugCursor.print(' ');
//
//          if(++debugCount == 4) {
//            debugCursor.setCursor(0, Screen.HEIGHT-1);
//            debugCount = 0;
//            debugCursor.setColor((byte) (debugCursor.getColor() ^ 0x40));
//          }
//        }
//      }
    }
  }

  public static void sleep(int seconds) {
    // https://wiki.osdev.org/PIT
    // https://www.visualmicro.com/page/Timer-Interrupts-Explained.aspx

    Handler.time = 0;
    while(Handler.time < seconds);

//    MAGIC.wIOs8(0x70, (byte) 0);
//    byte seconds = (byte) (MAGIC.rIOs8(0x71) & 0xFF);
//    byte ts = seconds;
//    while(seconds + 20 > ts){
//      MAGIC.wIOs8(0x70, (byte) 0);
//      ts = (byte) (MAGIC.rIOs8(0x71) & 0xFF);
//    }
  }

  private static void testCursor() {
    Cursor c = new Cursor();
    c.print("Hi there");

    c.setColor(Color.BLUE, Color.RED);
    c.setCursor(Terminal.COLS - 5, 2);
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
