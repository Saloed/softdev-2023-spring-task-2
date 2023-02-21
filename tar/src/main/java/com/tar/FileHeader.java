package tar.src.main.java.com.tar;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import tar.src.main.java.com.tar.PFile;

public class FileHeader {
    ArrayList<PFile> files = new ArrayList<PFile>();
    long size = 8;
    long currentOffset = 0;

    public ArrayList<PFile> getFiles() {
        return files;
    }

    public void addFile(PFile file) {
        size += file.getFilename().getBytes().length + 12; // 4 bytes for filename size, 8 bytes for offset
        file.setOffset(currentOffset);
        currentOffset += file.getSize();
        files.add(file);
    }

    // Header length(long->8 bytes)?? Завсит от машины
    //
    // Int filename size (4 bytes),String filename of set size, long filename offset (8 bytes);
    // ...
    public FileHeader(byte[] header) {
        parseHeader(header);
    }

    public FileHeader(ArrayList<String> files) {
        for (String filename : files) {
            System.out.println(filename);
            PFile pfile = new PFile(filename.split("\\\\")[filename.split("\\\\").length - 1], new File(filename).length());
            System.out.println(pfile.getFilename());
            this.addFile(pfile);
        }
    }

    public byte[] getHeader() {
        byte[] header = new byte[(int) size]; // Может ли заголовок быть больше Int??
        int headerOffset = 0;
        for (byte i : getBytesLong(size)) {
            header[headerOffset] = i;
            headerOffset++;
        }
        for (PFile file : files) {
            for (byte i : getBytesInt(file.getFilename().getBytes().length)) {
                header[headerOffset] = i;
                headerOffset++;
            }
            for (byte i : file.getFilename().getBytes()) {
                header[headerOffset] = i;
                headerOffset++;
            }
            for (byte i : getBytesLong(file.getSize())) {
                header[headerOffset] = i;
                headerOffset++;
            }

        }
        return header;
    }


    public byte[] getBytesLong(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES); // Можно сделать static
        buffer.putLong(x);
        return buffer.array();
    }

    public byte[] getBytesInt(int x) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(x);
        return buffer.array();
    }

    public void parseHeader(byte[] bytes) {
        int pointer = 0;
        while (pointer < bytes.length) {
            int fileNameSize = 0;
            String filename;
            long fileSize = 0;
            byte[] b = new byte[4];
            for (int i = 0; i < 4; i++) {
                b[i] = bytes[pointer++];
            }
            fileNameSize = bytesToInt(b);

            b = new byte[fileNameSize];
            for (int i = 0; i < fileNameSize; i++) {
                b[i] = bytes[pointer++];
            }
            filename = new String(b);

            b = new byte[8];
            for (int i = 0; i < 8; i++) {
                b[i] = bytes[pointer++];
            }
            fileSize = bytesToLong(b);
            addFile(new PFile(filename, fileSize));
        }
    }

    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip(); // Потому что intel - little endian?? А что с другими платформами??
        return buffer.getLong();
    }

    public static int bytesToInt(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getInt();
    }
}
