package rte;

public class EmptyObject {

    public EmptyObject next = null;

    /*
     * Parameters are assumed to be adjusted for address widths etc.
     *
     * Bit-Shifts and pointer size calculations are expected to already have happened!
     */
    @SJC.Inline
    public Object addObject(int size, int sizeRequired, int relocEntries, int scalarSize, SClassDesc type) {
        int address = MAGIC.cast2Ref(this) + this._r_scalarSize - size;
        int i;
        Object me;
//        (relocEntries << 2) + scalarSize
        for(i = address; i < address + size; i += 4) {
            MAGIC.wMem32(i, 0);
        }

        me = MAGIC.cast2Obj(address);
        MAGIC.assign(me._r_relocEntries, relocEntries);
        MAGIC.assign(me._r_scalarSize, scalarSize);
        MAGIC.assign(me._r_type, type);

        // adjust next pointers
        MAGIC.assign(me._r_next, 0);
//        MAGIC.assign(this._r_next, me);

        // adjust space size
        MAGIC.assign(this._r_scalarSize, this._r_scalarSize - sizeRequired);

        return me;
    }
}
