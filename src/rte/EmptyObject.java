package rte;

import devices.StaticV24;

public class EmptyObject {

    // the way EmptyObject is initialized, these attributes are not set (!)
    // null or 0 by default, prev must be set outside (not set to "this" as shown here!)
    public EmptyObject next = null;
    protected Object prev = this;

    /*
     * Parameters are assumed to be adjusted for address widths etc.
     *
     * Bit-Shifts and pointer size calculations are expected to have already happened!
     */
    public Object addObject(int scalarSize, int relocEntries, SClassDesc type) {

        int i, start, end, address;
        Object me;

        scalarSize = ((scalarSize + 3) & ~3);

        end = MAGIC.cast2Ref(this) + this._r_scalarSize;
        address = end - scalarSize;
        start = address - (relocEntries << 2);

        if(start > (MAGIC.cast2Ref(this) + this._r_scalarSize)) {
            // not enough space
            return null;
        }

        for(i = start; i < end; i += 4) {
            MAGIC.wMem32(i, 0);
        }

        me = MAGIC.cast2Obj(address);
        MAGIC.assign(me._r_relocEntries, relocEntries);
        MAGIC.assign(me._r_scalarSize, scalarSize);
        MAGIC.assign(me._r_type, type);

        // adjust next pointers
        MAGIC.assign(me._r_next, (Object) next);
        MAGIC.assign(prev._r_next, me);
        prev = me;

        // adjust space size
        MAGIC.assign(this._r_scalarSize, this._r_scalarSize - (end - start));

        return me;
    }
}
