package model;

import java.util.*;

public class MyLinkedList<E> {
    private Node<E> first;
    private Node<E> last;
    private int size = 0;
    private List<E> tasks = new ArrayList<>();
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
        for (Node<E> item = first; item != null; ) {
            Node<E> next = item.next;
            tasks.add(item.item);
            item = next;
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


