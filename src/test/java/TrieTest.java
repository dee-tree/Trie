import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import trie.Trie;

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
}
