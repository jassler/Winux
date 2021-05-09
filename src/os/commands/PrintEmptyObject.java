package os.commands;

import os.screen.Terminal;
import os.utils.stringTemplate.StringTemplate;
import rte.DynamicRuntime;
import rte.EmptyObject;

public class PrintEmptyObject {

    public static void printEmptyObject(Terminal out) {
        StringTemplate t = new StringTemplate(" {10cx} \u00b3 {10cx}\n");
        EmptyObject e = DynamicRuntime.firstEmptyObject;

        t.start(out);
        t.p("Start").p("Size");

        while(e != null) {

            out.printHex(MAGIC.cast2Ref(e));
            out.print(' ');
            out.printHex(e._r_scalarSize);
            out.println();
            e = e.next;
        }
    }

}
