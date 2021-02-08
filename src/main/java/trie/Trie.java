package trie;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;

/**
 * Класс, описывающий префиксное дерево
 *
 * @author Dmitriy Sokolov
 */
public class Trie {
    private final Node root;

    public Trie() {
        this.root = Node.rootNode();
    }

    /**
     * Добавляет строку в это дерево
     *
     * @param string {@link NotNull} добавляемая строка в дерево
     * @return <tt>true</tt>, если дерево не содержало данную строку ранее
     */
    public boolean add(@NotNull String string) {
        if (string.isEmpty())
            return false;

        return root.add(string);
    }


    /**
     * Удаляет строку из этого дерева
     *
     * @param string {@link NotNull} удаляемая строка из дерева
     * @return <tt>true</tt>, если дерево содержало данную строку
     */
    public boolean remove(@NotNull String string) {
        if (string.isEmpty())
            return true;

        return root.remove(string);
    }

    /**
     * Проверяет наличие строки в этом дереве
     *
     * @param string {@link NotNull} искомая строка в дереве
     * @return <tt>true</tt>, если дерево содержит данную строку
     */
    public boolean find(@NotNull String string) {
        return getNodeByPrefix(string) != null;
    }

    /**
     * Возвращает все строки этого дерева, начинающиеся с данного префикса
     *
     * @param prefix {@link NotNull} префикс поиска строк в дереве
     * @return коллекция {@link Collection} всех строк дерева, начинающегося с данного префикса
     */
    @NotNull
    public Collection<String> findAll(@NotNull String prefix) {
        Node prefixedNode = getNodeByPrefix(prefix);
        if (prefixedNode == null || prefixedNode == root)
            return new HashSet<>();

        return prefixedNode.getAllStringsForThisBranch(new HashSet<>(), new StringBuilder(prefix.substring(0, prefix.length() - 1)));
    }


    /**
     * Возвращает {@link Node} или null по данному префиксу
     *
     * @param prefix {@link NotNull} префикс, которому должен соответствовать искомый {@link Node}
     * @return {@link Node}, если таковой существует по искомому префиксу, в противном случае <tt>null</tt>
     */
    @Nullable
    private Node getNodeByPrefix(@NotNull String prefix) {
        return root.findChildByPrefix(prefix);
    }
}
