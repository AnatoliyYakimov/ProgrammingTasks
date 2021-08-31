package task2;

import javax.swing.*;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Имплементим {@link Iterable} чтобы мы могли использовать for-each loop
 * Наследуем {@link AbstractListModel} чтобы было удобнее работать со Swing (стандартная GUI библиотека)
 * @param <T>
 */
public class CustomList<T> extends AbstractListModel<T> implements Iterable<T> {

    /**
     * Внутренняя нода списка с указателям на следующий/предыдущий элементы.
     * @param <T>
     */
    private static class Node<T> {

        private final T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value) {
            this.value = value;
        }

    }

    private Node<T> root;

    public CustomList() {
    }

    public CustomList(Iterable<T> iterable) {
        for (T t : iterable) {
            add(t);
        }
    }

    public void clear() {
        int length = length();
        this.root = null;
        fireIntervalRemoved(this, 0, length);
    }

    public void add(T value) {
        if (root == null) {
            root = new Node<>(value);
        } else {
            Node<T> pointer = root;
            while (pointer.next != null) {
                pointer = pointer.next;
            }
            Node<T> node = new Node<>(value);
            pointer.next = node;
            node.prev = pointer;
        }
        int len = length();
        //Для Swing
        fireIntervalAdded(this, len, len);
    }

    public void addAll(Iterable<T> iterable) {
        for (T s : iterable) {
            add(s);
        }
    }

    public int length() {
        if (root == null) {
            return 0;
        }
        int i = 1;
        Node<T> pointer = root;
        while (pointer.next != null) {
            pointer = pointer.next;
            i++;
        }
        return i;
    }

    public T get(int idx) {
        return getNode(idx).value;
    }

    public T remove(int idx) {
        Node<T> nodeToRemove = getNode(idx);
        Node<T> next = nodeToRemove.next;
        Node<T> prev = nodeToRemove.prev;
        next.prev = prev;
        prev.next = next;
        //Для Swing
        fireIntervalRemoved(this, idx, idx);
        return nodeToRemove.value;
    }

    public void swap(int i, int j) {
        Node<T> iNode = getNode(i);
        Node<T> jNode = getNode(j);

        Node<T> tmp;

        if (jNode.next != null) {
            jNode.next.prev = iNode;
        }
        if (iNode.next != null) {
            iNode.next.prev = jNode;
        }

        tmp = iNode.next;
        iNode.next = jNode.next;
        jNode.next = tmp;


        if (iNode.prev != null) {
            iNode.prev.next = jNode;
        }
        if (jNode.prev != null) {
            jNode.prev.next = iNode;
        }

        tmp = iNode.prev;
        iNode.prev = jNode.prev;
        jNode.prev = tmp;

        if (root == iNode) {
            root = jNode;
        } else if (root == jNode) {
            root = iNode;
        }
        //Для Swing
        fireContentsChanged(this, i, i);
        fireContentsChanged(this, j, j);

    }


    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private Node<T> iter = root;

            @Override
            public boolean hasNext() {
                return iter != null;
            }

            @Override
            public T next() {
                T val = iter.value;
                iter = iter.next;
                return val;
            }
        };
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        for (T t : this) {
            action.accept(t);
        }
    }

    /**
     * Достаточно сложный в реализации метод, не требуется для нашей задачи.
     * @throws {@link  RuntimeException}
     */
    @Override
    public Spliterator<T> spliterator() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public int getSize() {
        return length();
    }

    @Override
    public T getElementAt(int index) {
        return get(index);
    }

    protected Node<T> getNode(int idx) {
        int len = length();
        if (idx >= len) {
            throw new IndexOutOfBoundsException(String.format("Index %d out of bound. Length: %d", idx, len));
        }
        Node<T> pointer = root;
        for (int i = 0; i < idx; i++) {
            pointer = pointer.next;
        }
        return pointer;
    }

}
