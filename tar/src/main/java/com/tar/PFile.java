package tar.src.main.java.com.tar;

public class PFile {
    private String filename;


    private long size;
    private long offset;

    public PFile(String filename, long size) {
        this.filename = filename;
        this.size = size;

    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getOffset() {
        return offset;
    }

    public long getSize() {
        return size;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}
