package pack_rle;

import org.kohsuke.args4j.*;

import java.io.*;

public class App {

    @Option(name = "-out", metaVar = "filename", usage = "output file name")
    private String outputFileName;

    @Option(name = "-z", usage = "encode file", forbids = {"-u"})
    private boolean encoding = false;

    @Option(name = "-u", usage = "decode file", forbids = {"-z"})
    private boolean decoding = false;


    @Argument(required = true, metaVar = "filename", usage = "input file name")
    private String inputFileName;

    static Convertor createConvertor(String outputFileName, boolean encoding) throws IOException {
        if (encoding) return new Encoder(outputFileName);
        else return new Decoder(outputFileName);
    }

    public static void main(String[] args) throws IOException {

        new App().launch(args);

    }

    static String setExtension(String filename, String extToRemove, String extToAdd) {
        if (filename.endsWith(extToRemove))
            filename = filename.substring(0, filename.length() - extToRemove.length());
        return filename + extToAdd;
    }

    static void process(String inputFileName, String outputFileName, boolean encoding) throws IOException {
        try (var source = new Source(inputFileName);
             var output = createConvertor(outputFileName, encoding)) {

            while (source.getCount() != 0) {
                source.advance(output.consume(source.getBuffer(), source.getCount()));
            }
        }
    }

    private void launch(String[] args) throws IOException {

        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            return;
        }

        if (!encoding && !decoding) {
            System.out.println("one of the options is required [-z|-u]");
            return;
        }

        if (outputFileName == null) {
            if (encoding) {
                outputFileName = setExtension(inputFileName, ".txt", ".rle");
            } else if (decoding) {
                outputFileName = setExtension(inputFileName, ".rle", ".txt");
            }
        }

        System.out.println(String.format("inputFileName: %s", inputFileName));
        System.out.println(String.format("outputFileName: %s", outputFileName));
        System.out.println(String.format("encoding: %b", encoding));
        System.out.println(String.format("decoding: %b", decoding));

        process(inputFileName, outputFileName, encoding);
    }

}