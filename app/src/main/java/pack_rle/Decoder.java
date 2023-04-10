package pack_rle;

import java.io.IOException;

public class Decoder extends Convertor {

    public Decoder(String filename) throws IOException {
        super(filename);
    }

    @Override
    int consume(byte[] bytes, int count) throws IOException {
        if (count < 2) return 0;
        boolean encoded = (bytes[0] & 0x80) != 0;
        int num = (bytes[0] & 0x7f) + 1;

        if (encoded) {
            while (num-- > 0) {
                output.write(bytes[1]);
            }
            return 2;
        } else {
            if (count <= num) return 0;
            output.write(bytes, 1, num);
            return num + 1;
        }
    }
}
