package kernel;

import os.commands.*;
import os.interrupt.Handler;
import os.interrupt.Interrupt;
import os.screen.Cursor;
import os.screen.GraphicLogo;
import os.screen.Terminal;
import os.tasks.*;

public class Kernel {

    public static Scheduler globalScheduler;
    public static CommandTask[] commands;

    public static void main() {
        MAGIC.doStaticInit();
        Interrupt.initPic();

        Terminal mainTerminal = new Terminal();
        mainTerminal.enableCursor();
        mainTerminal.clear();
        mainTerminal.focus();

        BIOS.switchMode(BIOS.GRAPHICS_MODE);

//        GraphicLogo.doIntro(1);
//        sleep(30);

        BIOS.switchMode(BIOS.TEXT_MODE);
        globalScheduler = new Scheduler("root");

        Task task = new TerminalTask(mainTerminal, globalScheduler, "mainTerminal");

        globalScheduler.addTask(task);

        task = new Counter("countTask");
        globalScheduler.addTask(task);

        task = new SchedulerInfoPrinter("sPrinter");
        globalScheduler.addTask(task);

        commands = new CommandTask[6];
        commands[0] = new CC();
        commands[1] = new Echo(mainTerminal);
        commands[2] = new MemLayout(globalScheduler, mainTerminal);
        commands[3] = new PCI(mainTerminal);
        commands[4] = new PrintEmptyObject(mainTerminal);
        commands[5] = new Help(mainTerminal);

        globalScheduler.runIndefinitely();

//        while (true) {
//            String cmd = mainTerminal.promptCommand(256);
//
//            if (cmd.startsWith("echo ")) {
//                Echo.handle(cmd, mainTerminal);
//
//            } else if (cmd.startsWith("mem")) {
//                MemLayout.printMemLayout(mainTerminal);
//
//            } else if (cmd.startsWith("pci")) {
//                PCI.printPCI(mainTerminal);
//
//            } else if (cmd.startsWith("cc")) {
//                CC.causeCC();
//
//            } else if (cmd.startsWith("empties")) {
//                PrintEmptyObject.printEmptyObject(mainTerminal);
//
//            } else if (cmd.startsWith("lol")) {
//                String[] sososo= new String[200];
//                for (int i = 0; i < 200; i++) {
//                    sososo[i] = "hallo hallo hallo";
//                }
//                PrintEmptyObject.printEmptyObject(mainTerminal);
//
//
//            } else if (cmd.startsWith("help") || cmd.startsWith("?")) {
//                mainTerminal.println("echo [value]    - Print value");
//                mainTerminal.println("mem             - View BIOS memory usage");
//                mainTerminal.println("pci             - View connected PCI devices");
//                mainTerminal.println("cc              - Cause Breakboint interrupt");
//                mainTerminal.println("help            - View help");
//            }
//        }
    }

    public static void sleep(int seconds) {
        // https://wiki.osdev.org/PIT
        // https://www.visualmicro.com/page/Timer-Interrupts-Explained.aspx

        int current = Handler.time;
        while (Handler.time < current + seconds);

//    MAGIC.wIOs8(0x70, (byte) 0);
//    byte seconds = (byte) (MAGIC.rIOs8(0x71) & 0xFF);
//    byte ts = seconds;
//    while(seconds + 20 > ts){
//      MAGIC.wIOs8(0x70, (byte) 0);
//      ts = (byte) (MAGIC.rIOs8(0x71) & 0xFF);
//    }
    }
}
