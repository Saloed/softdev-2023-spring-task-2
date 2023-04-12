package packrle;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

class Source implements Closeable {
    InputStream input;
    byte[] buffer = new byte[4096];
    int count = 0;

    public Source(String filename) throws IOException {
        input = new FileInputStream(filename);
        loadData();
    }

    @Override
    public void close() throws IOException {
        input.close();
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public int getCount() {
        return count;
    }

    public void advance(int len) throws IOException {
        assert len >= 0 && len <= count;
        var remaining = count - len;
        System.arraycopy(buffer, len, buffer, 0, remaining);
        count = remaining;
        loadData();
    }

    private void loadData() throws IOException {
        while (count < buffer.length) {
            int len = input.read(buffer, count, buffer.length - count);
            if (len <= 0) break;
            count += len;
        }
    }
}