package os.utils;

import os.screen.Color;
import os.screen.Terminal;
import os.utils.stringTemplate.StringTemplate;

public class TableFormatter {
    public static final int COLOR_HEADER = 0x01;// Color.mix(Color.BLACK, Color.GRAY);
    public static final int COLOR_EVEN = 0x02;// Color.mix(Color.GRAY | Color.BRIGHT, Color.BLUE);
    public static final int COLOR_ODD = 0x03;// Color.mix(Color.GRAY | Color.BRIGHT, Color.BLUE | Color.BRIGHT);

    private StringTemplate template;

    // | 31     24 | 23      16 | 15      08 | 07     00 |
    // |     -     |   header   |  even row  |  odd row  |
    private int colors;
    private int row;

    private int pCount;

    private int oldColor;
    private Terminal out;

    public TableFormatter(String rowTemplate) {
        this(
                rowTemplate,
                COLOR_HEADER,
                COLOR_EVEN,
                COLOR_ODD
        );
    }

    public TableFormatter(String rowTemplate, int headerColor, int evenRowColor, int oddRowColor) {
        this.template = new StringTemplate(rowTemplate);
        this.colors = oddRowColor | (evenRowColor << 8) | (headerColor << 16);
    }

    public TableFormatter start(Terminal out) {
        this.row = -1;
        this.pCount = 0;
        this.out = out;

        oldColor = out.getColor();
        out.setColor(colors >>> 16);
        template.start(out);
        return this;
    }

    @SJC.Inline
    private void updatePCount() {
        if(++pCount == template.getAmountOfPlaceholders()) {
            row++;
            out.setColor(colors >>> (8 * (row & 0x01)));
            pCount = -1;
        }
    }

    public TableFormatter p(String s) {
        if(pCount == -1) {
            template.start(out);
            pCount = 0;
        }
        template.p(s);
        updatePCount();
        return this;
    }

    public TableFormatter p(byte n) {
        if(pCount == -1) {
            template.start(out);
            pCount = 0;
        }
        template.p(n);
        updatePCount();
        return this;
    }

    public TableFormatter p(short n) {
        if(pCount == -1) {
            template.start(out);
            pCount = 0;
        }
        template.p(n);
        updatePCount();
        return this;
    }

    public TableFormatter p(int n) {
        if(pCount == -1) {
            template.start(out);
            pCount = 0;
        }
        template.p(n);
        updatePCount();
        return this;
    }

    public TableFormatter p(long n) {
        if(pCount == -1) {
            template.start(out);
            pCount = 0;
        }
        template.p(n);
        updatePCount();
        return this;
    }

    public void done() {
        out.setColor(oldColor);
    }
}
