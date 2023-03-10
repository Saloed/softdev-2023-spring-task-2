package tar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class FileHeader {
    private final static int MIN_HEADER_SIZE = 12;
    private List<TFile> files = new ArrayList<>();
    long size = 8;
    long currentOffset = 0;

    private FileHeader() {

    }

    public List<TFile> getFiles() {
        return files;
    }

    private FileHeader(List<TFile> files) {
        this.files = files;// Нет ли шанса тут поймать nullpointer?
        for (TFile file : files) {
            size += file.getFilename().getBytes().length + MIN_HEADER_SIZE;
            currentOffset += file.getSize();
        }
    }

    public static FileHeader getHeader(List<String> files) {
        return new FileHeader(createFileList(files));
    }

    public static List<TFile> createFileList(List<String> files) {
        List<TFile> fl = new ArrayList<>();
        for (String filename : files) {
            File f = new File(filename);
            TFile file = new TFile(f.getName(), f.length(), filename);
            fl.add(file);
        }
        return fl;
    }

    public void writeHeader(FileOutputStream fos) throws IOException {
        byte[] header = new byte[(int) size];
        int headerOffset = 0;
        for (byte i : getBytesLong(size)) {
            header[headerOffset] = i;
            headerOffset++;
        }
        for (TFile file : files) {
            for (byte i : getBytesInt(file.getFilename().getBytes().length)) { // DataInputStream использовать сложно, так как все байты уже считаны в один массив byte[];
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
        fos.write(header);
    }


    public byte[] getBytesLong(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    public byte[] getBytesInt(int x) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(x);
        return buffer.array();
    }


    public static FileHeader parseHeader(FileInputStream fis) throws IOException {
        List<TFile> headerFiles = new ArrayList<>();

        byte[] headerLengthBytes = new byte[8];
        fis.read(headerLengthBytes);
        long headerLength = FileHeader.bytesToLong(headerLengthBytes);
        byte[] bytes = new byte[(int) headerLength-8];
        fis.read(bytes);
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
            headerFiles.add(new TFile(filename, fileSize));
        }
        return new FileHeader(headerFiles);
    }

    public static long bytesToLong(byte[] bytes) {
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
