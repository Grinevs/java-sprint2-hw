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
            prevItem.setNext(newNode);
        size++;
        add(newNode);
    }

    public void add(Node<E> node) {
        int id = ((Task) node.getItem()).getId();
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
        final Node<E> next = item.getNext();
        final Node<E> prev = item.getPrev();
        if (prev == null) {
            first = next;
        } else {
            prev.setNext(next);
            item.setPrev(null);
        }
        if (next == null) {
            last = prev;
        } else {
            next.setPrev(prev);
            item.setNext(null);
        }
        item.setItem(null);
        size--;
    }

    public int size() {
        return this.size;
    }

    public List<E> getTasks() {
        List<E> tasks = new ArrayList<>();

        for (Node<E> item = first; item != null; ) {
            Node<E> next = item.getNext();
            tasks.add(item.getItem());
            item = next;
        }
        return tasks;
    }

    public void clear() {
        for (Node<E> x = first; x != null; ) {
            Node<E> next = x.getNext();
            x.setItem(null);
            x.setNext(null);
            x.setPrev(null);
            x = next;
        }
        first = last = null;
        size = 0;
        indexMap.clear();
    }

}


