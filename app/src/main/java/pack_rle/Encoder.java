package pack_rle;

import java.io.IOException;

public class Encoder extends Convertor {

    public Encoder(String filename) throws IOException {
        super(filename);
    }

    @Override
    int consume(byte[] bytes, int count) throws IOException {
        int num = 1;
        int len = Math.min(count, 128);
        while (num < len) {
            if (bytes[0] != bytes[num]) break;
            num++;
        }
        if (num > 1) {
            output.write(0x80 | (num - 1));
            output.write(bytes[0]);
        } else {
            while (num < len) {
                if (bytes[num] == bytes[num + 1]) break;
                num++;
            }
            output.write(num - 1);
            output.write(bytes, 0, num);
        }
        return num;
    }
}
