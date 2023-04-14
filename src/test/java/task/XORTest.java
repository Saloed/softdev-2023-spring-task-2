package task;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XORTest {
    @Test
    public void cipher() {
        String input = "Hello! This is test for task #2!";
        byte[] inputBytes = input.getBytes();
        String key = "afd6219abc726103";
        byte[] encoded = XOR.ciph(inputBytes, key);
        byte[] outputBytes = XOR.ciph(encoded, key);
        String output = new String(outputBytes, StandardCharsets.UTF_8);
        assertEquals(input, output);

    }
}
