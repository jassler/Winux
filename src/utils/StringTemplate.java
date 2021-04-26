package utils;

import screen.Cursor;
import screen.Terminal;

/**
 * "{}" are placeholders. "{" can be escaped via "\\{"
 *
 * Specify placeholder length with number inside curly braces (no space). "{3}" -> placeholder at least of length 3
 * (padding done with spaces). Values are always left aligned.
 *
 * To circumvent missing variadic functions, use {@link StringTemplate#start(Terminal)} to start outputting and
 * use p and hex to fill in the missing gaps.
 *
 * Eg.:
 *
 * StringTemplate s = new StringTemplate("My name is {} and I am {} years old and I was born on {2}.{2}.{4}");
 *
 * s.start(myTerminal);
 *
 * s.p("Matt").hex(21).p(1).p(4).(1999);
 */
public class StringTemplate {

    // String array does some weird stuff
//    private String[] parts;

    private String templateString;
    private int[] starts, ends;
    private int[] templateLengths;

    private int outIndex;
    private Terminal out;

    public StringTemplate(String template) {
        int splits = 0;
        int splitIndex, splitStart, i, length;

        this.templateString = template;

        // first figure out how many splits we have
        for(i = 0; i < template.length(); i++) {
            if(template.charAt(i) == '\\') {
                i++;
                continue;
            }

            if(template.charAt(i) == '{') {
                while(++i < template.length() && template.charAt(i) != '}');
                if(i >= template.length()) {
                    break;
                }

                splits++;
            }
        }

//        this.parts = new String[splits+1];
        starts = new int[splits+1];
        ends = new int[splits+1];
        templateLengths = new int[splits];

        // do the actual splitting stuff
        splitIndex = 0;
        splitStart = 0;
        for(i = 0; i < template.length() && splitIndex < splits; i++) {
            if(template.charAt(i) == '\\') {
                i++;
                continue;
            }

            if(template.charAt(i) == '{') {
//                this.parts[splitIndex] = template.substring(splitStart, i);
                starts[splitIndex] = splitStart;
                ends[splitIndex] = i;

                length = 0;
                while(true) {
                    i++;
                    if(template.charAt(i) >= '0' && template.charAt(i) <= '9')
                        length = (length * 10) + (template.charAt(i) - '0');
                    else
                        break;
                }
                while(template.charAt(i) != '}')
                    i++;

                templateLengths[splitIndex] = length;
                splitStart = i + 1;
                splitIndex++;
            }
        }

        starts[splitIndex] = splitStart;
        ends[splitIndex] = template.length();

//        if(splitStart >= template.length()) {
//            this.parts[splitIndex] = "";
//        } else {
//            this.parts[splitIndex] = template.substring(splitStart, template.length());
//        }
    }

    private int getIntWidth(int n) {
        int w = 0;
        if(n < 0) {
            w++;
            n = -n;
        }

        do {
            n /= 10;
            w++;
        } while(n > 0);

        return w;
    }

    public void start(Terminal out) {
        outIndex = 0;
        this.out = out;
//        out.print(parts[outIndex]);
        out.print(templateString, starts[outIndex], ends[outIndex]);
    }

    private void nextPart() {
        outIndex++;
//        out.print(parts[outIndex]);
        out.print(templateString, starts[outIndex], ends[outIndex]);
        if(outIndex >= templateLengths.length)
            outIndex = 0;
    }

    @SJC.Inline
    private void printSpaces(int spacesLeft) {
        while(spacesLeft --> 0)
            out.print(' ');
    }

    /*
    PRINT basics
     */
    public StringTemplate p(String s) {
        out.print(s);
        printSpaces(templateLengths[outIndex] - s.length());
        nextPart();
        return this;
    }

    public StringTemplate p(int n) {
        out.print(n);
        printSpaces(templateLengths[outIndex] - getIntWidth(n));
        nextPart();
        return this;
    }

    /*
    WITHOUT 0x at the beginning
     */
    public StringTemplate hex(byte n) {
        out.printHex(n);
        printSpaces(templateLengths[outIndex] - 2);
        nextPart();
        return this;
    }

    public StringTemplate hex(short n) {
        out.printHex(n);
        printSpaces(templateLengths[outIndex] - 4);
        nextPart();
        return this;
    }

    public StringTemplate hex(int n) {
        out.printHex(n);
        printSpaces(templateLengths[outIndex] - 8);
        nextPart();
        return this;
    }

    public StringTemplate hex(long n) {
        out.printHex(n);
        printSpaces(templateLengths[outIndex] - 16);
        nextPart();
        return this;
    }

    /*
    WITH 0x at the beginning
     */
    public StringTemplate hex0x(byte n) {
        out.print("0x");
        out.printHex(n);
        printSpaces(templateLengths[outIndex] - 4);
        nextPart();
        return this;
    }

    public StringTemplate hex0x(short n) {
        out.print("0x");
        out.printHex(n);
        printSpaces(templateLengths[outIndex] - 6);
        nextPart();
        return this;
    }

    public StringTemplate hex0x(int n) {
        out.print("0x");
        out.printHex(n);
        printSpaces(templateLengths[outIndex] - 10);
        nextPart();
        return this;
    }

    public StringTemplate hex0x(long n) {
        out.print("0x");
        out.printHex(n);
        printSpaces(templateLengths[outIndex] - 18);
        nextPart();
        return this;
    }
}
