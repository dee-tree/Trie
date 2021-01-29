package trie;

import java.util.Collection;

import static trie.Node.EMPTY_KEY;

/**
 * Класс, описывающий префиксное дерево
 *
 * @author Dmitriy Sokolov
 */
public class Trie {
    private Node root;

    public Trie() {
        this.root = new Node(EMPTY_KEY, "");
    }

    /**
     * Добавляет строку в это дерево
     *
     * @param string добавляемая строка в дерево
     * @return <tt>true</tt>, если дерево не содержало данную строку ранее
     */
    public boolean add(String string) {
        if (string.isEmpty())
            return false;

        return root.add(string);
    }


    /**
     * Удаляет строку из этого дерева
     *
     * @param string удаляемая строка из дерева
     * @return <tt>true</tt>, если дерево содержало данную строку
     */
    public boolean remove(String string) {
        if (string.isEmpty())
            return true;

        return root.remove(string);
    }

    /**
     * Проверяет наличие строки в этом дереве
     *
     * @param string искомая строка в дереве
     * @return <tt>true</tt>, если дерево содержит данную строку
     */
    public boolean find(String string) {
        if (string.isEmpty())
            return true;

        return root.find(string);
    }

    /**
     * Возвращает все строки этого дерева, начинающиеся с данного префикса
     *
     * @param prefix префикс поиска строк в дереве
     * @return коллекция {@link Collection} всех строк дерева, начинающегося с данного префикса
     */
    public Collection<String> findAll(String prefix) {
        // TODO: implement findAll
        return null;
    }
}
