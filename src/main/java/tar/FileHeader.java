package tar;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class FileHeader {
    ArrayList<TFile> files = new ArrayList<TFile>();
    long size = 8;
    long currentOffset = 0;

    public ArrayList<TFile> getFiles() {
        return files;
    }

    public void addFile(TFile file) {
        size += file.getFilename().getBytes().length + 12;
        file.setOffset(currentOffset);
        currentOffset += file.getSize();
        files.add(file);
    }

    public FileHeader(byte[] header) {
        parseHeader(header);
    }

    public FileHeader(ArrayList<String> files) {
        for (String filename : files) {
            File f = new File(filename);
            TFile file = new TFile(f.getName(), f.length());
            file.setFilepath(filename);
            this.addFile(file);
        }
    }

    public byte[] getHeader() {
        byte[] header = new byte[(int) size];
        int headerOffset = 0;
        for (byte i : getBytesLong(size)) {
            header[headerOffset] = i;
            headerOffset++;
        }
        for (TFile file : files) {
            for (byte i : getBytesInt(file.getFilename().getBytes().length)) { // DataInputStream использовать сложно, так как все байты уже считаны в byte[];
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
            for (int i = 0; i < fileNameSize; i++) { // memcpy (C)
                b[i] = bytes[pointer++];
            }
            filename = new String(b);

            b = new byte[8];
            for (int i = 0; i < 8; i++) {
                b[i] = bytes[pointer++];
            }
            fileSize = bytesToLong(b);
            addFile(new TFile(filename, fileSize));
        }
    }

    public static long bytesToLong(byte[] bytes) { // Заменить на DataInputStream
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getLong();
    }

    public static int bytesToInt(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getInt();
    }
}
