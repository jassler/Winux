package kernel;

import os.commands.CC;
import os.commands.Echo;
import os.commands.MemLayout;
import os.commands.PCI;
import os.screen.*;
import os.interrupt.*;

public class Kernel {
//  public static void main() {
//    MAGIC.doStaticInit();
//    Interrupt.initPic();
//
//    Terminal mainTerminal = new Terminal();
//    mainTerminal.enableCursor();
//    mainTerminal.clear();
//    mainTerminal.focus();
//
//    BIOS.switchMode(BIOS.GRAPHICS_MODE);
//
//    GraphicLogo.doIntro(1);
//    sleep(30);
//
//    BIOS.switchMode(BIOS.TEXT_MODE);
//
//    while(true) {
//      String cmd = mainTerminal.promptCommand(256);
//
//      if(cmd.startsWith("echo ")) {
//        Echo.handle(cmd, mainTerminal);
//
//      } else if(cmd.startsWith("mem")) {
//        MemLayout.printMemLayout(mainTerminal);
//
//      } else if(cmd.startsWith("os/pci")) {
//        PCI.printPCI(mainTerminal);
//
//      } else if(cmd.startsWith("cc")) {
//        CC.causeCC();
//
//      } else if(cmd.startsWith("help") || cmd.startsWith("?")) {
//        mainTerminal.println("echo [value]    - Print value");
//        mainTerminal.println("mem             - View BIOS memory usage");
//        mainTerminal.println("pci             - View connected PCI devices");
//        mainTerminal.println("cc              - Cause Breakboint interrupt");
//        mainTerminal.println("help            - View help");
//      }
//    }
//  }
//
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

  public static void main() {
    MAGIC.doStaticInit();
    Interrupt.initPic();

    String s = "abcdefghijklmnopqrstuvwxyz";
    String[] strings = new String[4];
    strings[0] = s.substring(0, 3);
    strings[1] = s.substring(4, 7);
    strings[2] = s.substring(10, 15);
    strings[3] = s.substring(19, 20);

    for(String a : strings) {
      Cursor.staticCursor.print(a);
      Cursor.staticCursor.print(' ');
    }
  }
}
