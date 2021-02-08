import org.junit.Before;
import org.junit.Test;
import trie.Trie;

import java.util.Collection;

import static org.junit.Assert.*;

public class TrieTest {

    private Trie trie;

    @Before
    public void init() {
        trie = new Trie();
    }

    @Test
    public void add() {
        assertFalse(trie.add(""));

        assertTrue(trie.add("hello"));
        assertFalse(trie.add("hello"));

        trie.remove("hello");
        assertTrue(trie.add("hello"));
    }

    @Test
    public void find() {
        assertTrue(trie.find(""));
        assertFalse(trie.find("notfound"));

        trie.add("contains");
        assertTrue(trie.find("contains"));
    }

    @Test
    public void remove() {
        assertTrue(trie.remove(""));
        assertFalse(trie.remove("hello"));

        trie.add("hello");
        assertTrue(trie.remove("hello"));
    }

    @Test
    public void findAll() {
        assertEquals(0, trie.findAll("").size());
        assertEquals(0, trie.findAll("h").size());

        trie.add("hello");
        assertEquals(1, trie.findAll("h").size());
        assertContentEquals(trie.findAll("h"), "hello");
        assertEquals(1, trie.findAll("he").size());
        assertContentEquals(trie.findAll("he"), "hello");


        assertEquals(1, trie.findAll("hello").size());
        assertContentEquals(trie.findAll("hello"), "hello");

        trie.add("hover");
        assertEquals(2, trie.findAll("h").size());
        assertContentEquals(trie.findAll("h"), "hello", "hover");
        assertEquals(1, trie.findAll("ho").size());
        assertContentEquals(trie.findAll("ho"), "hover");

    }

    private void assertContentEquals(Collection<String> actual, String... expected) {
        assertEquals("Lengths differ,", actual.size(), expected.length);

        for (String expectedString : expected) {
            assertTrue("Actual (" + actual + ") does not contain expected value: " + expectedString, actual.contains(expectedString));
        }
    }
}
