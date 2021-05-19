package os.tasks.specificTasks;

import devices.StaticV24;
import os.tasks.CommandTask;
import os.tasks.Task;
import rte.DynamicRuntime;
import rte.EmptyObject;

public class MarkAndSweep extends CommandTask {

    private static final int negBit = 1 << 32;

    @SJC.Inline
    private static void mark(Object o) {
        o._r_scalarSize |= negBit;
    }

    @SJC.Inline
    private static void unmark(Object o) {
        o._r_scalarSize &= ~negBit;
    }

    @SJC.Inline
    private static boolean isMarked(Object o) {
        return (o._r_scalarSize & negBit) != 0;
    }


    public MarkAndSweep() {
        super("sweep", "Apply mark and sweep", null);
    }

    @Override
    public void run() {
        Object o;
        int total, part;
        int imageEnd = MAGIC.imageBase + MAGIC.rMem32(MAGIC.imageBase + 4);

        // "Clear Interrupt Flag", disable interrupts
        MAGIC.inline(0xFA);

        StaticV24.println("Marking objects...");
        o = DynamicRuntime.getFirstObject();
        total = 0;
        while(o != null) {
            mark(o);
            o = o._r_next;
            total++;
        }

        StaticV24.print("Marked ");
        StaticV24.print(total);
        StaticV24.println(" objects");

        StaticV24.println("Checking which ones can be sweeped...");
        o = DynamicRuntime.getFirstObject();
        part = 0;
        while(o != null && MAGIC.cast2Ref(o) < imageEnd) {
            part += traverseObject(o);
            o = o._r_next;
        }
        StaticV24.print(part);
        StaticV24.println(" objects will stay");

        StaticV24.println("Sweeping...");
        sweep();

        // "Set Interrupt Flag", enable interrupts
        MAGIC.inline(0xFB);
        setDone(true);
    }

    private int traverseObject(Object o) {
        int i, relocs, relocRef, count;

        if(!isMarked(o) || o instanceof EmptyObject) {
            // already marked, nothing to traverse (since all objects below are also marked)
            StaticV24.printHex(MAGIC.cast2Ref(o), 8);
            StaticV24.println(" already unmarked");
            return 0;
        }

        relocs = o._r_relocEntries - MAGIC.getInstRelocEntries("Object");
        relocRef = MAGIC.cast2Ref(o) - 8;

        unmark(o);

        count = 1;
        for(i = 0; i < relocs; i++) {
            // Object layout (each > is 8 byte)
            // > ...
            // > Inst-Reloc 2
            // > Inst-Reloc 1
            // > _r_next
            // > _r_type
            // > _r_relocEntries <- pointing to this
            // > _r_scalarSize
            // > Inst-Ska 1
            // > Inst-Ska 2
            // > ...
            count += traverseObject(MAGIC.cast2Obj(MAGIC.rMem32(relocRef)));
            relocRef -= 4;
        }

        return count;
    }

    @SJC.Inline
    private int startAddress(Object o) {
        return MAGIC.cast2Ref(o) - (o._r_relocEntries << 2);
    }

    @SJC.Inline
    private int endAddress(Object o) {
        return MAGIC.cast2Ref(o) + o._r_scalarSize;
    }

    private void checkNeighbouringMarks(Object current) {
        int endOfObject = endAddress(current);

        for(Object o = current._r_next; o != null; o = o._r_next) {
            if(isMarked(o) && startAddress(o) == endOfObject) {
                StaticV24.print(MAGIC.cast2Ref(current));
                StaticV24.print(" borders to ");
                StaticV24.println(MAGIC.cast2Ref(current));

                int oSize = (o._r_relocEntries << 2) + o._r_scalarSize;
                current._r_scalarSize += oSize;
                endOfObject += oSize;
            }
        }
    }

    private void sweep() {
        Object prev, current;
        current = DynamicRuntime.getFirstObject();
        while(current != null) {
            if(isMarked(current))
                checkNeighbouringMarks(current);
            current = current._r_next;
        }
    }

    @Override
    public void setup(String[] args) {
        setDone(false);
    }
}
