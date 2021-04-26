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

    /**
     * Substring from start (inclusive) to end (exclusive).
     *
     * No checks if {@code end > start} and {@code 0 <= start < length()} and {@code 0 < end <= length()}.
     *
     * @param start inclusive start index
     * @param end exclusive start index
     * @return new String
     */
    public String substring(int start, int end) {
        char[] result = new char[end - start];
        for(int i = start; i < end; i++)
            result[i-start] = value[i];

        return new String(result);
    }

    /**
     * Get index of char from start point. Returns -1 if char not found.
     *
     * @param c char to look for
     * @param start starting index ({@code start >= 0})
     * @return character index or -1
     */
    public int indexOf(char c, int start) {
        for(int i = start; i < count; i++) {
            if(value[i] == c)
                return i;
        }
        return -1;
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