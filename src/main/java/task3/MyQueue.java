package task3;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class MyQueue<T> implements Queue<T> {
    private final LinkedList<T> queue;

    public MyQueue() {
        this.queue = new LinkedList<>();
    }
    public MyQueue(LinkedList<T> queue) {
        this.queue = queue;
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return queue.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return queue.iterator();
    }

    @Override
    public Object[] toArray() {
        return queue.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return queue.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return offer(t);
    }

    @Override
    public boolean remove(Object o) {
        return queue.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return queue.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return queue.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        queue.removeAll(c);
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return queue.retainAll(c);
    }

    @Override
    public void clear() {
        queue.clear();
    }

    @Override
    public boolean offer(T t) {
        queue.addLast(t);
        return true;
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            T tmp = queue.getFirst();
            queue.removeFirst();
            return tmp;
        }
    }

    @Override
    public T poll() {
        if (isEmpty()) {
            return null;
        } else {
            T tmp = queue.getFirst();
            queue.removeFirst();
            return tmp;
        }
    }

    @Override
    public T element() {
        if (isEmpty())
            throw new NoSuchElementException();

            return queue.getFirst();


    }

    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        } else {
            return queue.getFirst();
        }
    }
}
