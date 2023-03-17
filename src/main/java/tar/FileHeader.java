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
    private final List<TFile> files;
    long size = 8;
    long currentOffset = 0;


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
        ByteBuffer header = ByteBuffer.allocate((int) size);

        header.put(getBytesLong(size));

        for (TFile file : files) {
            header.putInt(file.getFilename().getBytes().length);
            header.put(file.getFilename().getBytes());
            header.put(getBytesLong(file.getSize()));
        }
        fos.write(header.array());
    }


    public byte[] getBytesLong(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }



    public static FileHeader parseHeader(FileInputStream fis) throws IOException {
        List<TFile> headerFiles = new ArrayList<>();

        byte[] headerLengthBytes = new byte[Long.BYTES];
        fis.read(headerLengthBytes);
        long headerLength = FileHeader.bytesToLong(headerLengthBytes);
        byte[] headerBytes = new byte[(int) headerLength - Long.BYTES];
        fis.read(headerBytes);
        ByteBuffer header = ByteBuffer.wrap(headerBytes);

        while (header.hasRemaining()) {
            int fileNameSize = header.getInt();
            byte[] b = new byte[fileNameSize];
            header.get(b, 0, fileNameSize);
            String filename = new String(b);
            long fileSize = header.getLong();
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

}
