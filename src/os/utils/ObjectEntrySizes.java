package os.utils;

import java.lang.FLASH;
import java.lang.Object;
import java.lang.String;
import java.lang.BIOS;
import java.lang.STRUCT;
import rte.DynamicRuntime;
import rte.SClassDesc;
import rte.SArray;
import rte.SIntfMap;
import rte.EmptyObject;
import rte.ImageHelper;
import rte.SMthdBlock;
import rte.SIntfDesc;
import os.pci.PCIDevice;
import os.pci.PCIController;
import os.tasks.specificTasks.TerminalTask;
import os.tasks.specificTasks.TerminalPrompter;
import os.tasks.specificTasks.Counter;
import os.tasks.specificTasks.MarkAndSweep;
import os.tasks.specificTasks.SchedulerInfoPrinter;
import os.tasks.CommandTask;
import os.tasks.DelayTask;
import os.tasks.LoopTask;
import os.tasks.Task;
import os.interrupt.Handler;
import os.interrupt.Interrupt;
import os.interrupt.BlueScreen;
import os.interrupt.StackTraverser;
import os.utils.StringBuffer;
import os.utils.stringTemplate.StringTemplate;
import os.utils.stringTemplate.Placeholder;
import os.utils.ObjectEntrySizes;
import os.utils.TableFormatter;
import os.utils.Range;
import os.utils.Math;
import os.utils.OutStream;
import os.utils.NumberHelper;
import os.filesystem.FileSystem;
import os.filesystem.FileStream;
import os.filesystem.File;
import os.screen.GraphicLogo;
import os.screen.Terminal;
import os.screen.Graphic;
import os.screen.Color;
import os.screen.Cursor;
import os.commands.Page;
import os.commands.ObjectViewer;
import os.commands.Help;
import os.commands.Info;
import os.commands.LS;
import os.commands.AddCounter;
import os.commands.CC;
import os.commands.PrintEmptyObject;
import os.commands.Echo;
import os.commands.MemLayout;
import os.commands.PCI;
import os.editor.Editor;
import os.keyboard.ASCII;
import os.keyboard.KeyBuffer;
import os.keyboard.KeyboardController;
import os.keyboard.layout.QWERTY;
import os.keyboard.KeyEvent;
import os.virtualMemory.VirtualMemory;
import kernel.Scheduler;
import kernel.Sample;
import kernel.Kernel;
import devices.StaticV24;

public class ObjectEntrySizes {

    public static int getInstRelocEntries(String str) {

        if("FLASH".equals(str)) return MAGIC.getInstRelocEntries("FLASH");
        if("Object".equals(str)) return MAGIC.getInstRelocEntries("Object");
        if("String".equals(str)) return MAGIC.getInstRelocEntries("String");
        if("BIOS".equals(str)) return MAGIC.getInstRelocEntries("BIOS");
        if("STRUCT".equals(str)) return MAGIC.getInstRelocEntries("STRUCT");
        if("DynamicRuntime".equals(str)) return MAGIC.getInstRelocEntries("DynamicRuntime");
        if("SClassDesc".equals(str)) return MAGIC.getInstRelocEntries("SClassDesc");
        if("SArray".equals(str)) return MAGIC.getInstRelocEntries("SArray");
        if("SIntfMap".equals(str)) return MAGIC.getInstRelocEntries("SIntfMap");
        if("EmptyObject".equals(str)) return MAGIC.getInstRelocEntries("EmptyObject");
        if("ImageHelper".equals(str)) return MAGIC.getInstRelocEntries("ImageHelper");
        if("SMthdBlock".equals(str)) return MAGIC.getInstRelocEntries("SMthdBlock");
        if("SIntfDesc".equals(str)) return MAGIC.getInstRelocEntries("SIntfDesc");
        if("PCIDevice".equals(str)) return MAGIC.getInstRelocEntries("PCIDevice");
        if("PCIController".equals(str)) return MAGIC.getInstRelocEntries("PCIController");
        if("TerminalTask".equals(str)) return MAGIC.getInstRelocEntries("TerminalTask");
        if("TerminalPrompter".equals(str)) return MAGIC.getInstRelocEntries("TerminalPrompter");
        if("Counter".equals(str)) return MAGIC.getInstRelocEntries("Counter");
        if("MarkAndSweep".equals(str)) return MAGIC.getInstRelocEntries("MarkAndSweep");
        if("SchedulerInfoPrinter".equals(str)) return MAGIC.getInstRelocEntries("SchedulerInfoPrinter");
        if("CommandTask".equals(str)) return MAGIC.getInstRelocEntries("CommandTask");
        if("DelayTask".equals(str)) return MAGIC.getInstRelocEntries("DelayTask");
        if("LoopTask".equals(str)) return MAGIC.getInstRelocEntries("LoopTask");
        if("Task".equals(str)) return MAGIC.getInstRelocEntries("Task");
        if("Handler".equals(str)) return MAGIC.getInstRelocEntries("Handler");
        if("Interrupt".equals(str)) return MAGIC.getInstRelocEntries("Interrupt");
        if("BlueScreen".equals(str)) return MAGIC.getInstRelocEntries("BlueScreen");
        if("StackTraverser".equals(str)) return MAGIC.getInstRelocEntries("StackTraverser");
        if("StringBuffer".equals(str)) return MAGIC.getInstRelocEntries("StringBuffer");
        if("StringTemplate".equals(str)) return MAGIC.getInstRelocEntries("StringTemplate");
        if("Placeholder".equals(str)) return MAGIC.getInstRelocEntries("Placeholder");
        if("ObjectEntrySizes".equals(str)) return MAGIC.getInstRelocEntries("ObjectEntrySizes");
        if("TableFormatter".equals(str)) return MAGIC.getInstRelocEntries("TableFormatter");
        if("Range".equals(str)) return MAGIC.getInstRelocEntries("Range");
        if("Math".equals(str)) return MAGIC.getInstRelocEntries("Math");
        if("OutStream".equals(str)) return MAGIC.getInstRelocEntries("OutStream");
        if("NumberHelper".equals(str)) return MAGIC.getInstRelocEntries("NumberHelper");
        if("FileSystem".equals(str)) return MAGIC.getInstRelocEntries("FileSystem");
        if("FileStream".equals(str)) return MAGIC.getInstRelocEntries("FileStream");
        if("File".equals(str)) return MAGIC.getInstRelocEntries("File");
        if("GraphicLogo".equals(str)) return MAGIC.getInstRelocEntries("GraphicLogo");
        if("Terminal".equals(str)) return MAGIC.getInstRelocEntries("Terminal");
        if("Graphic".equals(str)) return MAGIC.getInstRelocEntries("Graphic");
        if("Color".equals(str)) return MAGIC.getInstRelocEntries("Color");
        if("Cursor".equals(str)) return MAGIC.getInstRelocEntries("Cursor");
        if("Page".equals(str)) return MAGIC.getInstRelocEntries("Page");
        if("ObjectViewer".equals(str)) return MAGIC.getInstRelocEntries("ObjectViewer");
        if("Help".equals(str)) return MAGIC.getInstRelocEntries("Help");
        if("Info".equals(str)) return MAGIC.getInstRelocEntries("Info");
        if("LS".equals(str)) return MAGIC.getInstRelocEntries("LS");
        if("AddCounter".equals(str)) return MAGIC.getInstRelocEntries("AddCounter");
        if("CC".equals(str)) return MAGIC.getInstRelocEntries("CC");
        if("PrintEmptyObject".equals(str)) return MAGIC.getInstRelocEntries("PrintEmptyObject");
        if("Echo".equals(str)) return MAGIC.getInstRelocEntries("Echo");
        if("MemLayout".equals(str)) return MAGIC.getInstRelocEntries("MemLayout");
        if("PCI".equals(str)) return MAGIC.getInstRelocEntries("PCI");
        if("Editor".equals(str)) return MAGIC.getInstRelocEntries("Editor");
        if("ASCII".equals(str)) return MAGIC.getInstRelocEntries("ASCII");
        if("KeyBuffer".equals(str)) return MAGIC.getInstRelocEntries("KeyBuffer");
        if("KeyboardController".equals(str)) return MAGIC.getInstRelocEntries("KeyboardController");
        if("QWERTY".equals(str)) return MAGIC.getInstRelocEntries("QWERTY");
        if("KeyEvent".equals(str)) return MAGIC.getInstRelocEntries("KeyEvent");
        if("VirtualMemory".equals(str)) return MAGIC.getInstRelocEntries("VirtualMemory");
        if("Scheduler".equals(str)) return MAGIC.getInstRelocEntries("Scheduler");
        if("Sample".equals(str)) return MAGIC.getInstRelocEntries("Sample");
        if("Kernel".equals(str)) return MAGIC.getInstRelocEntries("Kernel");
        if("StaticV24".equals(str)) return MAGIC.getInstRelocEntries("StaticV24");

        return -1;
    }

    public static int getInstScalarEntries(String str) {

        if("FLASH".equals(str)) return MAGIC.getInstScalarSize("FLASH");
        if("Object".equals(str)) return MAGIC.getInstScalarSize("Object");
        if("String".equals(str)) return MAGIC.getInstScalarSize("String");
        if("BIOS".equals(str)) return MAGIC.getInstScalarSize("BIOS");
        if("STRUCT".equals(str)) return MAGIC.getInstScalarSize("STRUCT");
        if("DynamicRuntime".equals(str)) return MAGIC.getInstScalarSize("DynamicRuntime");
        if("SClassDesc".equals(str)) return MAGIC.getInstScalarSize("SClassDesc");
        if("SArray".equals(str)) return MAGIC.getInstScalarSize("SArray");
        if("SIntfMap".equals(str)) return MAGIC.getInstScalarSize("SIntfMap");
        if("EmptyObject".equals(str)) return MAGIC.getInstScalarSize("EmptyObject");
        if("ImageHelper".equals(str)) return MAGIC.getInstScalarSize("ImageHelper");
        if("SMthdBlock".equals(str)) return MAGIC.getInstScalarSize("SMthdBlock");
        if("SIntfDesc".equals(str)) return MAGIC.getInstScalarSize("SIntfDesc");
        if("PCIDevice".equals(str)) return MAGIC.getInstScalarSize("PCIDevice");
        if("PCIController".equals(str)) return MAGIC.getInstScalarSize("PCIController");
        if("TerminalTask".equals(str)) return MAGIC.getInstScalarSize("TerminalTask");
        if("TerminalPrompter".equals(str)) return MAGIC.getInstScalarSize("TerminalPrompter");
        if("Counter".equals(str)) return MAGIC.getInstScalarSize("Counter");
        if("MarkAndSweep".equals(str)) return MAGIC.getInstScalarSize("MarkAndSweep");
        if("SchedulerInfoPrinter".equals(str)) return MAGIC.getInstScalarSize("SchedulerInfoPrinter");
        if("CommandTask".equals(str)) return MAGIC.getInstScalarSize("CommandTask");
        if("DelayTask".equals(str)) return MAGIC.getInstScalarSize("DelayTask");
        if("LoopTask".equals(str)) return MAGIC.getInstScalarSize("LoopTask");
        if("Task".equals(str)) return MAGIC.getInstScalarSize("Task");
        if("Handler".equals(str)) return MAGIC.getInstScalarSize("Handler");
        if("Interrupt".equals(str)) return MAGIC.getInstScalarSize("Interrupt");
        if("BlueScreen".equals(str)) return MAGIC.getInstScalarSize("BlueScreen");
        if("StackTraverser".equals(str)) return MAGIC.getInstScalarSize("StackTraverser");
        if("StringBuffer".equals(str)) return MAGIC.getInstScalarSize("StringBuffer");
        if("StringTemplate".equals(str)) return MAGIC.getInstScalarSize("StringTemplate");
        if("Placeholder".equals(str)) return MAGIC.getInstScalarSize("Placeholder");
        if("ObjectEntrySizes".equals(str)) return MAGIC.getInstScalarSize("ObjectEntrySizes");
        if("TableFormatter".equals(str)) return MAGIC.getInstScalarSize("TableFormatter");
        if("Range".equals(str)) return MAGIC.getInstScalarSize("Range");
        if("Math".equals(str)) return MAGIC.getInstScalarSize("Math");
        if("OutStream".equals(str)) return MAGIC.getInstScalarSize("OutStream");
        if("NumberHelper".equals(str)) return MAGIC.getInstScalarSize("NumberHelper");
        if("FileSystem".equals(str)) return MAGIC.getInstScalarSize("FileSystem");
        if("FileStream".equals(str)) return MAGIC.getInstScalarSize("FileStream");
        if("File".equals(str)) return MAGIC.getInstScalarSize("File");
        if("GraphicLogo".equals(str)) return MAGIC.getInstScalarSize("GraphicLogo");
        if("Terminal".equals(str)) return MAGIC.getInstScalarSize("Terminal");
        if("Graphic".equals(str)) return MAGIC.getInstScalarSize("Graphic");
        if("Color".equals(str)) return MAGIC.getInstScalarSize("Color");
        if("Cursor".equals(str)) return MAGIC.getInstScalarSize("Cursor");
        if("Page".equals(str)) return MAGIC.getInstScalarSize("Page");
        if("ObjectViewer".equals(str)) return MAGIC.getInstScalarSize("ObjectViewer");
        if("Help".equals(str)) return MAGIC.getInstScalarSize("Help");
        if("Info".equals(str)) return MAGIC.getInstScalarSize("Info");
        if("LS".equals(str)) return MAGIC.getInstScalarSize("LS");
        if("AddCounter".equals(str)) return MAGIC.getInstScalarSize("AddCounter");
        if("CC".equals(str)) return MAGIC.getInstScalarSize("CC");
        if("PrintEmptyObject".equals(str)) return MAGIC.getInstScalarSize("PrintEmptyObject");
        if("Echo".equals(str)) return MAGIC.getInstScalarSize("Echo");
        if("MemLayout".equals(str)) return MAGIC.getInstScalarSize("MemLayout");
        if("PCI".equals(str)) return MAGIC.getInstScalarSize("PCI");
        if("Editor".equals(str)) return MAGIC.getInstScalarSize("Editor");
        if("ASCII".equals(str)) return MAGIC.getInstScalarSize("ASCII");
        if("KeyBuffer".equals(str)) return MAGIC.getInstScalarSize("KeyBuffer");
        if("KeyboardController".equals(str)) return MAGIC.getInstScalarSize("KeyboardController");
        if("QWERTY".equals(str)) return MAGIC.getInstScalarSize("QWERTY");
        if("KeyEvent".equals(str)) return MAGIC.getInstScalarSize("KeyEvent");
        if("VirtualMemory".equals(str)) return MAGIC.getInstScalarSize("VirtualMemory");
        if("Scheduler".equals(str)) return MAGIC.getInstScalarSize("Scheduler");
        if("Sample".equals(str)) return MAGIC.getInstScalarSize("Sample");
        if("Kernel".equals(str)) return MAGIC.getInstScalarSize("Kernel");
        if("StaticV24".equals(str)) return MAGIC.getInstScalarSize("StaticV24");

        return -1;
    }

    public static void printPossibilities(Terminal out) {

        out.print("FLASH ");
        out.print("Object ");
        out.print("String ");
        out.print("BIOS ");
        out.print("STRUCT ");
        out.print("DynamicRuntime ");
        out.print("SClassDesc ");
        out.print("SArray ");
        out.print("SIntfMap ");
        out.print("EmptyObject ");
        out.print("ImageHelper ");
        out.print("SMthdBlock ");
        out.print("SIntfDesc ");
        out.print("PCIDevice ");
        out.print("PCIController ");
        out.print("TerminalTask ");
        out.print("TerminalPrompter ");
        out.print("Counter ");
        out.print("MarkAndSweep ");
        out.print("SchedulerInfoPrinter ");
        out.print("CommandTask ");
        out.print("DelayTask ");
        out.print("LoopTask ");
        out.print("Task ");
        out.print("Handler ");
        out.print("Interrupt ");
        out.print("BlueScreen ");
        out.print("StackTraverser ");
        out.print("StringBuffer ");
        out.print("StringTemplate ");
        out.print("Placeholder ");
        out.print("ObjectEntrySizes ");
        out.print("TableFormatter ");
        out.print("Range ");
        out.print("Math ");
        out.print("OutStream ");
        out.print("NumberHelper ");
        out.print("FileSystem ");
        out.print("FileStream ");
        out.print("File ");
        out.print("GraphicLogo ");
        out.print("Terminal ");
        out.print("Graphic ");
        out.print("Color ");
        out.print("Cursor ");
        out.print("Page ");
        out.print("ObjectViewer ");
        out.print("Help ");
        out.print("Info ");
        out.print("LS ");
        out.print("AddCounter ");
        out.print("CC ");
        out.print("PrintEmptyObject ");
        out.print("Echo ");
        out.print("MemLayout ");
        out.print("PCI ");
        out.print("Editor ");
        out.print("ASCII ");
        out.print("KeyBuffer ");
        out.print("KeyboardController ");
        out.print("QWERTY ");
        out.print("KeyEvent ");
        out.print("VirtualMemory ");
        out.print("Scheduler ");
        out.print("Sample ");
        out.print("Kernel ");
        out.print("StaticV24 ");

        out.println();
    }
}

