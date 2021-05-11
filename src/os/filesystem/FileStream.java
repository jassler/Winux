package os.filesystem;

public class FileStream {

    private File f;
    private int ptr;
    private char c;

    public FileStream(File f) {
        this.f = f;
        this.ptr = 0;
    }

    public boolean next() {
        if(ptr >= f.size())
            return false;

        c = f.content[ptr++];
        return true;
    }

    @SJC.Inline
    public char getChar() {
        return c;
    }

}
