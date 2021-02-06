package trie;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Класс, описывающий узел префиксного дерева {@link Trie}
 *
 * @author Dmitriy Sokolov
 */
/* package private */ class Node {

    // Ключ корня
    static final char EMPTY_KEY = '\0';

    /**
     * Родительский узел. Для корня <tt>null</tt>
     */
    private final Node parent;

    private final char key;

    /**
     * Дочерние узлы. Для листа <tt>#isEmpty() == true</tt>
     */
    private final Map<Character, Node> children;

    /**
     * Маркер, указывающий на то, является ли данный узел завершением строки
     */
    private boolean isEndOfString = false;

    /**
     * Конструктор узла префиксного дерева {@link Trie}
     *
     * @param key    ключ корня
     * @param string {@link NotNull} строка, добавляемая в префиксное дерево {@link Trie}
     * @param parent {@link Nullable} родительский узел. Для корневого узла <tt>null</tt>
     */
    private Node(char key, @NotNull String string, @Nullable Node parent) {
        this.key = key;
        this.parent = parent;
        children = new HashMap<>();

        if (string.isEmpty()) {
            isEndOfString = true;
        } else {
            add(string);
        }
    }

    /**
     * Создает корневой узел префиксного дерева {@link Trie}
     *
     * @return корневой узел префиксного дерева {@link Trie}
     */
    static Node rootNode() {
        return new Node(EMPTY_KEY, "", null);
    }

    /**
     * Добавляет данную строку в виде дочерних узлов данного узла в префиксное дерево {@link Trie}
     *
     * @param string добавляемая в префиксное дерево {@link NotNull} строка {@link Trie}
     * @return <tt>true</tt>, если префиксное дерево {@link Trie} не содержало данную строку ранее
     */
    /* package private */ boolean add(@NotNull String string) {
        if (string.isEmpty()) {
            boolean contained = isEndOfString;
            isEndOfString = true;

            return !contained;
        }

        char nextKey = string.charAt(0);

        if (children.containsKey(nextKey)) {
            return children.get(nextKey).add(string.substring(1));
        } else {
            children.put(nextKey, new Node(nextKey, string.substring(1), this));
            return true;
        }
    }

    /**
     * Лениво удаляет данную строку из дочерних узлов данного узла префиксного дерева {@link Trie}
     *
     * @param string удаляемая {@link NotNull} строка из префиксного дерева {@link Trie}
     * @return <tt>true</tt>, если префиксное дерево {@link Trie} содержало данную строку
     */
    /* package private */ boolean remove(@NotNull String string) {
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


    /**
     * Осуществляет поиск дочернего узла префиксного дерева {@link Trie} по данному префиксу
     *
     * @param prefix {@link NotNull} префикс, по которому осуществляется поиск узла
     * @return узел префиксного дерева {@link Trie} по данному префиксу или <tt>null</tt>, если такой префикс в дереве не содержится
     */
    /* package private */
    @Nullable Node findChildByPrefix(@NotNull String prefix) {
        if (prefix.isEmpty())
            return this;
        else {
            char nextKey = prefix.charAt(0);

            if (!children.containsKey(nextKey))
                return null;

            return children.get(nextKey).findChildByPrefix(prefix.substring(1));
        }
    }

    /**
     * Возвращает префикс от root node до this node
     *
     * @param currentChain строка, хранящая восстановленный префикс к текущему моменту
     * @return префикс от root node до this node
     */
    private StringBuilder get(@NotNull StringBuilder currentChain) {
        if (parent != null) {
            return parent.get(currentChain).append(key);
        } else {
            return currentChain;
        }
    }

    /**
     * Добавлет все строки по этой ветке префиксного дерева {@link Trie} в данную коллекцию
     *
     * @param strings {@link NotNull} коллекция, в которую будут добавлены все строки по этой ветке префиксного дерева {@link Trie}
     * @return коллекцию всех строк по этой ветке префиксного дерева {@link Trie}
     */
    private Collection<String> appendAllStringsForThisBranch(@NotNull Collection<String> strings) {
        if (isEndOfString && parent != null) {
            strings.add(get(new StringBuilder()).toString());
        }

        for (Node node : children.values()) {
            node.appendAllStringsForThisBranch(strings);
        }

        return strings;
    }

    /**
     * Возвращает коллекцию всех строк по этой ветке префиксного дерева {@link Trie}
     *
     * @return коллекция всех строк по этой ветке префиксного дерева {@link Trie}
     */
    /* package private */ Collection<String> getAllStringsForThisBranch() {
        return appendAllStringsForThisBranch(new HashSet<>());
    }
}
