package model;

import java.util.*;

public class MyLinkedList<E> {
    private Node<E> first;
    private Node<E> last;
    private int size = 0;
    private Map<Integer, Node<E>> indexMap = new HashMap<>();

    public void linkLast(E item) {
        final Node<E> prevItem = last;
        final Node<E> newNode = new Node<>(prevItem, item, null);
        last = newNode;
        if (prevItem == null)
            first = newNode;
        else
            prevItem.next = newNode;
        size++;
        add(newNode);
    }

    public void add(Node<E> node) {
        int id = ((Task) node.item).getId();
        remove(id);
        indexMap.put(id, node);
    }

    public void remove(int id) {
        if (indexMap.containsKey(id)) {
            Node<E> removeNode = indexMap.get(id);
            removeNode(removeNode);
            indexMap.remove(id);
        }
    }

    public void removeNode(Node<E> item) {
        final Node<E> next = item.next;
        final Node<E> prev = item.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            item.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            item.next = null;
        }
        item.item = null;
        size--;
    }

    public int size() {
        return this.size;
    }

    public List<E> getTasks() {
        List<E> tasks = new ArrayList<>();

        for (Node<E> item = first; item != null; ) {
            Node<E> next = item.next;
            tasks.add(item.item);
            item = next;
        }
        return tasks;
    }

    public void clear() {
        for (Node<E> x = first; x != null; ) {
            Node<E> next = x.next;
            x.item = null;
            x.next = null;
            x.prev = null;
            x = next;
        }
        first = last = null;
        size = 0;
        indexMap.clear();
    }

}


