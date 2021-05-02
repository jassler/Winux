package os.utils.stringTemplate;

import os.screen.Terminal;
import os.utils.Range;

public class Placeholder {
    public static final char LEFT = 'l';
    public static final char CENTER = 'c';
    public static final char RIGHT = 'r';
    public static final char IS_HEX = 'x';

    public final int length;
    public final char orientation;
    // if isHex = true, 0x is printed before number
    public final boolean isHex;

    /**
     * Count number of placeholders (noted with '{}', escaped with backslash) in String.
     *
     * @param t text block with placeholders
     * @return number of placeholders in text
     */
    public static int countPlaceholders(String t) {
        int amount = 0;
        int i = 0;
        Range r;

        while((r = nextPlaceholder(t, i)) != null) {
            amount++;
            i = r.end + 1;
        }

        return amount;
    }

    /**
     * Get next placeholder start and end index.
     *
     * @param t template string
     * @param start start index
     * @return index of '{' and '}' token, -1 if not found
     */
    public static Range nextPlaceholder(String t, int start) {
        int end;
        for(; start < t.length(); start++) {
            if(t.charAt(start) == '\\') {
                start++;

            } else if(t.charAt(start) == '{') {
                end = t.indexOf('}', start+1);
                if(end >= 0)
                    return new Range(start, end);
                return null;
            }
        }
        return null;
    }

    /**
     * Parse placeholder. Look for next placeholder from start index.
     *
     * @param t text block
     * @param start '{' char index
     * @return placeholder object
     */
    public static Placeholder parseNextPlaceholder(String t, int start) {
        Range range = nextPlaceholder(t, start);
        if(range == null)
            return null;

        return parseNextPlaceholder(t, range);
    }

    /**
     * Parse placeholder in given range. No particular care has been given to grammar correctness.
     *
     * @param t text block
     * @param range '{' and '}' index
     * @return placeholder object
     */
    public static Placeholder parseNextPlaceholder(String t, Range range) {
        int i;
        Placeholder place = new Placeholder();

        for(i = range.start+1; i < range.end; i++) {
            if('0' <= t.charAt(i) && t.charAt(i) <= '9') {
                MAGIC.assign(place.length, (place.length * 10) + (t.charAt(i) - '0'));

            } else if(
                    t.charAt(i) == LEFT ||
                            t.charAt(i) == CENTER ||
                            t.charAt(i) == RIGHT
            ) {
                MAGIC.assign(place.orientation, t.charAt(i));

            } else if(t.charAt(i) == IS_HEX) {
                MAGIC.assign(place.isHex, true);
            }
        }

        return place;
    }

    public Placeholder() {
        this.length = 0;
        this.orientation = LEFT;
        this.isHex = false;
    }

    public Placeholder(int length, char orientation, boolean isHex) {
        this.length = length;
        this.orientation = orientation;
        this.isHex = isHex;
    }

    /**
     * Add whitespace to the left of content.
     *
     * @param out Output object to print whitespaces to
     * @param contentLength Length of placeholder content to determine amount of whitespace needed
     */
    public void padLeft(Terminal out, int contentLength) {
        int spaces = 0;

        if(orientation == RIGHT)
            spaces = length - contentLength;
        else if(orientation == CENTER)
            spaces = (length - contentLength) / 2;

        while(spaces --> 0)
            out.print(' ');
    }

    /**
     * Add whitespace to the right of content.
     *
     * If placeholder is centered and the amount of whitespace to the left and right uneven (aka
     * not cleanly divisible by 2), add one more extra whitespace to the right.
     *
     * @param out Output object to print whitespaces to
     * @param contentLength Length of placeholder content to determine amount of whitespace needed
     */
    public void padRight(Terminal out, int contentLength) {
        int spaces = 0;

        if(orientation == LEFT)
            spaces = length - contentLength;
        else if(orientation == CENTER)
            spaces = (length - contentLength) / 2 + (length - contentLength) % 2;

        while(spaces --> 0)
            out.print(' ');
    }
}
