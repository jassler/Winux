package java.lang;

public class String {
    private char[] value;
    private int count;

    public String(char[] arr) {
        this.value = arr;
        this.count = arr.length;
    }

    public String(char[] arr, int start, int end) {
        int i;
        this.count = end - start;
        this.value = new char[this.count];

        for(i = 0; i < this.count; i++) {
            this.value[i] = arr[i + start];
        }
    }

    /**
     * Substring from beginIndex to end.
     * <p>
     * No checks if {@code 0 <= start < length()}.
     *
     * @param beginIndex inclusive start index
     * @return new String
     */
    @SJC.Inline
    public String substring(int beginIndex) {
        return substring(beginIndex, this.count);
    }

    /**
     * Substring from start (inclusive) to end (exclusive).
     * <p>
     * No checks if {@code end > start} and {@code 0 <= start < length()} and {@code 0 < end <= length()}.
     *
     * @param start inclusive start index
     * @param end   exclusive start index
     * @return new String
     */
    public String substring(int start, int end) {
        char[] result = new char[end - start];
        for (int i = start; i < end; i++)
            result[i - start] = value[i];

        return new String(result);
    }

    /**
     * Get index of char from start point. Returns -1 if char not found.
     *
     * @param c     char to look for
     * @param start starting index ({@code start >= 0})
     * @return character index or -1
     */
    public int indexOf(char c, int start) {
        for (int i = start; i < count; i++) {
            if (value[i] == c)
                return i;
        }
        return -1;
    }

    /**
     * Get index of character ch. -1 if not found.
     *
     * @param ch Character to search
     * @return index of ch, -1 if not found
     */
    public int indexOf(char ch) {
        return indexOf(ch, 0);
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
        if (other.length() > this.count)
            return false;

        for (i = 0; i < other.length(); i++) {
            if (this.charAt(i) != other.charAt(i))
                return false;
        }
        return true;
    }

    public String[] split(char c) {
        String[] result;
        int count = 1;
        int i = 0, j;

        while((i = indexOf(c, i)) >= 0) {
            count++;
            i++;
        }

        result = new String[count];
        i = j = 0;
        count = 0;
        while((j = indexOf(c, j)) >= 0) {
            result[count] = substring(i, j);
            i = j + 1;
        }

        return result;
    }

    public boolean equals(String other) {
        if(count != other.count)
            return false;
        for (int i = 0; i < count; i++) {
            if(value[i] != other.value[i])
                return false;
        }
        return true;
    }
}