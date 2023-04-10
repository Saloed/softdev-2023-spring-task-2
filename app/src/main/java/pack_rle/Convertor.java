package pack_rle;

import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class Convertor implements Closeable {
    protected final OutputStream output;

    abstract int consume(byte[] bytes, int count) throws IOException;

    public Convertor(String filename) throws IOException {
        output = new FileOutputStream(filename);
    }

    @Override
    public void close() throws IOException {
        output.close();
    }
}
