package os.tasks.specificTasks;

import devices.StaticV24;
import os.tasks.Task;
import rte.DynamicRuntime;

public class MarkAndSweep /*extends Task*/ {
//    public MarkAndSweep(String name) {
//        super(name);
//    }
//
//    @Override
//    public void run() {
//        while(true);
//        // "Clear Interrupt Flag", disable interrupts
//        MAGIC.inline(0xFA);
//
//        StaticV24.println("Marking objects...");
//        markPossibleObjects();
//
//        StaticV24.println("Checking which ones can be sweeped...");
//        markSweepable();
//
//
//        StaticV24.println("Sweeping...");
//        sweep();
//
//
//        // "Set Interrupt Flag", enable interrupts
//        MAGIC.inline(0xFB);
//    }
//
//    private void markPossibleObjects() {
//        int marked, total;
//        int imageEnd = MAGIC.rMem32(MAGIC.imageBase + 4);
//        Object obj = DynamicRuntime.getFirstObject();
//
//        total = marked = 0;
//        while(obj != null) {
//            total++;
//            if(MAGIC.cast2Ref(obj) >= imageEnd) {
//                marked++;
//                obj._r_scalarSize *= -1;
//            }
//            obj = obj._r_next;
//        }
//
//        StaticV24.print("Found ");
//        StaticV24.print(marked);
//        StaticV24.print(" of ");
//        StaticV24.print(total);
//        StaticV24.println(" remarkable candidates");
//    }
//
//    private void markSweepable() {
//        Object obj = DynamicRuntime.getFirstObject();
//        int imageEnd = MAGIC.rMem32(MAGIC.imageBase + 4);
//
//        while(MAGIC.cast2Ref(obj) < imageEnd) {
//            traverseObject(obj);
//        }
//    }
//
//    private void traverseObject(Object o) {
//        int i;
//        int relocs = o._r_relocEntries - MAGIC.getInstRelocEntries("Object");
//        int refStart = MAGIC.cast2Ref(o) - 8;
//
//        if(o._r_scalarSize < 0)
//            o._r_scalarSize *= -1;
//
//        for(i = 0; i < relocs; i++) {
//            // Object layout (each > is 8 byte)
//            // > Inst-Reloc 1
//            // > Inst-Reloc 2
//            // > ...
//            // > _r_next
//            // > _r_type
//            // > _r_relocEntries <- pointing to this
//            // > _r_scalarSize
//            // > Inst-Ska 1
//            // > Inst-Ska 2
//            // > ...
//            traverseObject(MAGIC.cast2Obj(MAGIC.rMem32(refStart)));
//            refStart -= 4;
//        }
//    }
//
//    private void sweep() {
//        Object obj = DynamicRuntime.getFirstObject();
//        int total = 0;
//        int marked = 0;
//
//        while(obj != null) {
//            total++;
//            if(obj._r_scalarSize < 0) {
//                marked++;
//            }
//            obj._r_scalarSize *= -1;
//            obj = obj._r_next;
//        }
//
//        StaticV24.print("Of ");
//        StaticV24.print(total);
//        StaticV24.print(" object ");
//        StaticV24.print(marked);
//        StaticV24.print(" can be sweeped");
//    }
}
