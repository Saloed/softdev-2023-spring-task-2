import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

class GrepTest {

    @Test
    void testAllFalse() throws IOException {
        Grep grep = new Grep();
        ArrayList result = grep.grep(false, false, false, "pipi", "test");
        assertEquals(2, result.size());
        assertEquals("Word - pipi.", result.get(0));
        assertEquals("All args false string pipi.", result.get(1));
    }

    @Test
    void testInverted() throws IOException {
        Grep grep = new Grep();
        ArrayList result = grep.grep(true, false, false, "pipi", "test");
        assertEquals(4, result.size());
        assertEquals("Thelinewhereeverythingisconnectedpipi.", result.get(0));
        assertEquals("IgNoReCasE true string piPI.", result.get(1));
        assertEquals("A_StRing_WHeRE_eing_is_merGed_aND_diFFERERERERRERErent_case_PiPI", result.get(2));
        assertEquals("123", result.get(3));
    }

    @Test
    void testRegex() throws IOException {
        Grep grep = new Grep();
        ArrayList result = grep.grep(false, true, false, "pipi", "test");
        assertEquals(3, result.size());
        assertEquals("Word - pipi.", result.get(0));
        assertEquals("All args false string pipi.", result.get(1));
        assertEquals("Thelinewhereeverythingisconnectedpipi.", result.get(2));
    }

    @Test
    void testIgnoreCase() throws IOException {
        Grep grep = new Grep();
        ArrayList result = grep.grep(false, false, true, "PIPI", "test");
        assertEquals(3, result.size());
        assertEquals("Word - pipi.", result.get(0));
        assertEquals("All args false string pipi.", result.get(1));
        assertEquals("IgNoReCasE true string piPI.", result.get(2));
    }

    @Test
    void testAllTrue() throws IOException {
        Grep grep = new Grep();
        ArrayList result = grep.grep(true, true, true, "pipi", "test");
        assertEquals(1, result.size());
        assertEquals("123", result.get(0));
    }

}