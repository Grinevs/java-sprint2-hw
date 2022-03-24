package model;

import java.util.*;

public class MyLinkedList<E> {
    private Node<E> first;
    private Node<E> last;
    private int size = 0;
    private List<E> tasks = new ArrayList<>();
    private Map<Integer, Node<E>> indexMap = new HashMap<>();

    public void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
        add(newNode);
    }

    public void add(Node<E> node) {
        int id = ((Task) node.item).getId();
        remove(id);
        indexMap.put(id, node);
    }

    public void removeNode(Node<E> x) {
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }
        x.item = null;
        size--;
    }

    public int size() {
        return this.size;
    }

    public List<E> getTasks() {
        for (Node<E> x = first; x != null; ) {
            Node<E> next = x.next;
            tasks.add(x.item);
            x = next;
        }
        return tasks;
    }

    public void remove(int id) {
        if (indexMap.containsKey(id)) {
            Node<E> removeNode = indexMap.get(id);
            removeNode(removeNode);
        }
    }
}


