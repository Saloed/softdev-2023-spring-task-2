package tar;

public class TFile {
    private final String filename;

    private final String filepath;
    private final long size;


    public TFile(String filename, long size) {
        this(filename, size, filename);
    }

    public TFile(String filename, long size, String filepath) {
        this.filename = filename;
        this.size = size;
        this.filepath = filepath;
    }

    
    public long getSize() {
        return size;
    }

    public String getFilename() {
        return filename;
    }

    public String getFilepath() {
        return filepath;
    }

}
