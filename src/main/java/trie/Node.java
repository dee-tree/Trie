package trie;

import java.util.HashMap;
import java.util.Map;

/* package private */ class Node {

    static final char EMPTY_KEY = '\0';

    private final char key;
    private final Map<Character, Node> children;

    private boolean isEndOfString = false;


    /* package private */ Node(char key, String string) {
        this.key = key;
        children = new HashMap<>();

        if (string.isEmpty()) {
            isEndOfString = true;
        } else {
            add(string);
        }
    }

    /* package private */ boolean add(String string) {
        if (string.isEmpty()) {
            boolean contained = isEndOfString;
            isEndOfString = true;

            return !contained;
        }

        char nextKey = string.charAt(0);

        if (children.containsKey(nextKey)) {
            return children.get(nextKey).add(string.substring(1));
        } else {
            children.put(nextKey, new Node(nextKey, string.substring(1)));
            return true;
        }
    }

    // "Ленивое" удаление
    /* package private */ boolean remove(String string) {
        if (string.isEmpty()) {
            boolean contained = isEndOfString;

            isEndOfString = false;
            return contained;
        }

        char nextKey = string.charAt(0);
        if (!children.containsKey(nextKey))
            return false;

        return children.get(nextKey).remove(string.substring(1));
    }


    /* package private */ boolean find(String string) {
        if (string.isEmpty())
            return isEndOfString;

        char nextKey = string.charAt(0);

        if (children.containsKey(nextKey)) {
            return children.get(nextKey).find(string.substring(1));
        } else {
            return false;
        }
    }

    /* package private */ boolean isLeaf() {
        return children.isEmpty();
    }

}
