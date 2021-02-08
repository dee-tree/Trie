package trie;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, описывающий узел префиксного дерева {@link Trie}
 *
 * @author Dmitriy Sokolov
 */
/* package private */ class Node {

    // Ключ корня
    static final char EMPTY_KEY = '\0';

    final char key;

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
     */
    private Node(char key, @NotNull String string) {
        this.key = key;
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
        return new Node(EMPTY_KEY, "");
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
            children.put(nextKey, new Node(nextKey, string.substring(1)));
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
     * @return {@link Nullable} узел префиксного дерева {@link Trie} по данному префиксу или <tt>null</tt>, если такой префикс в дереве не содержится
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
     * Добавляет все строки по этой ветке префиксного дерева {@link Trie} в коллекцию strings с префиксом prefix
     * и возвращает ссылку на эту коллекцию
     *
     * @param strings {@link NotNull} коллекция, в которую будут добавлены все строки по этой ветке префиксного дерева {@link Trie}
     * @param prefix  {@link NotNull} префикс для данного узла дерева {@link Trie}
     * @return {@link NotNull} коллекцию всех строк по этой ветке префиксного дерева {@link Trie}
     */
    @NotNull Collection<String> getAllStringsForThisBranch(@NotNull Collection<String> strings, @NotNull StringBuilder prefix) {
        prefix.append(key);
        if (isEndOfString) {
            strings.add(prefix.toString());
        }

        for (Node node : children.values()) {
            node.getAllStringsForThisBranch(strings, prefix);
        }
        prefix.deleteCharAt(prefix.length() - 1);

        return strings;
    }
}
