package task;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;


import java.io.FileInputStream;
import java.io.FileOutputStream;


import java.io.IOException;


public class XOR {
    @Option(name = "-c", usage = "key for encryption file", forbids = "-d")
    private String encryptionKey;
    @Option(name = "-d", usage = "key for decryption file", forbids = "-c")
    private String decryptionKey;
    @Option(name = "-o")
    private String outputFileName;
    @Argument(required = true)
    private String inputFileName;

    public static void main(String[] args) {
        new XOR().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            return;
        }

        try {
            if (outputFileName.isEmpty()) outputFileName = inputFileName + ".ciph";
            try (FileInputStream inputStream = new FileInputStream(inputFileName); FileOutputStream outputStream = new FileOutputStream(outputFileName)) {
                byte[] buffer = new byte[4096];
                byte[] byteOutput;
                int n;
                while ((n = inputStream.read(buffer)) >= 0) {
                    if (encryptionKey == null) byteOutput = ciph(buffer, decryptionKey);
                    else byteOutput = ciph(buffer, encryptionKey);
                    outputStream.write(byteOutput, 0, n);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    public static byte[] ciph(byte[] input, String key) {
        byte[] byteKey = key.getBytes();
        byte[] result = new byte[input.length];
        int keySize = byteKey.length;
        for (int i = 0; i < input.length; i++) {
            result[i] = (byte) (input[i] ^ byteKey[i % keySize]);
        }
        return result;
    }
}


