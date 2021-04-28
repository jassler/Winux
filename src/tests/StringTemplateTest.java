package tests;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import os.screen.Terminal;
import os.utils.StringTemplate;

@SJC.IgnoreUnit
class StringTemplateTest {

    @Test
    void start() {
        TerminalSub t = new TerminalSub();
        StringTemplate templ = new StringTemplate("Hello {10}, I am {} and I am {4} years old.");
        templ.start(t);
        templ.p("Max").p("Tom").p(20);

        Assert.assertEquals("Hello Max       , I am Tom and I am 20   years old.", t.text);
    }


    private class TerminalSub extends Terminal {
        private StringBuilder text = new StringBuilder();

        @Override
        public void clear() {
            super.clear();
        }

        @Override
        public String promptCommand(int maxLength) {
            return "";
        }

        @Override
        public void focus() {}

        @Override
        public void moveCursorHorizontally(int amount) {}

        @Override
        public void enableCursor() {}

        @Override
        public void disableCursor() {}

        @Override
        public void moveCursor(int x, int y) {}

        @Override
        public void updateCursorPos() {}

        @Override
        public int getCursorPosition() {
            return 0;
        }

        @Override
        public void setColor(int fg, int bg) {}

        @Override
        public void setColor(int c) {}

        @Override
        public int getColor() {}

        @Override
        public void setPos(int newX, int newY) {}

        @Override
        public void writeTo(int tmpX, int tmpY, String s) {}

        @Override
        public void print(char c) {
            text.append(c);
        }

        @Override
        public void print(int x) {
            text.append(x);
        }

        @Override
        public void printHex(byte b) {
            text.append(b);
        }

        @Override
        public void printHex(short s) {
            text.append(s);
        }

        @Override
        public void printHex(int x) {
            text.append(x);
        }

        @Override
        public void printHex(long x) {
            text.append(x);
        }

        @Override
        public void print(long x) {
            text.append(x);
        }

        @Override
        public void print(String str) {
            text.append(str);
        }

        @Override
        public void print(String str, int start, int end) {
            text.append(str, start, end);
        }

        @Override
        public void println() {
            text.append('\n');
        }

        @Override
        public void println(char c) {
            text.append(c).append('\n');
        }

        @Override
        public void println(int i) {
            text.append(i).append('\n');
        }

        @Override
        public void println(long l) {
            text.append(l).append('\n');
        }

        @Override
        public void println(String str) {
            text.append(str).append('\n');
        }
    }
}