package java.lang;

public class String {
    private char[] value;
    private int count;

    public String(char[] arr) {
        int i;
        // add new instance - only then are instance-variables valid
        MAGIC.useAsThis(rte.DynamicRuntime.newInstance(
                MAGIC.getInstScalarSize("String")+(arr.length<<1),
                MAGIC.getInstRelocEntries("String"), MAGIC.clssDesc("String")
        ));

        count = arr.length;
        for(i = 0; i < arr.length; i++)
            value[i] = arr[i];
    }

    @SJC.Inline
    public int length() {
        return count;
    }
    @SJC.Inline
    public char charAt(int i) {
        return value[i];
    }

    public boolean startsWith(String other) {
        int i;
        if(other.length() > this.count)
            return false;

        for(i = 0; i < other.length(); i++) {
            if(this.charAt(i) != other.charAt(i))
                return false;
        }
        return true;
    }
}