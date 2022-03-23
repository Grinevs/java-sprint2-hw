package model;

import java.util.*;

public class MyLinkedList<Task> {
    private Node<Task> first;
    private Node<Task> last;
    private int size = 0;
    private List<Task> tasks= new ArrayList<>();
    private Map<Integer, Node<Task>> indexMap = new HashMap<>();

    public void linkLast(Task e) {
        final Node<Task> l = last;
        final Node<Task> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
        tasks.add(e);
        indexMap.put(e.getId, last);
    }

    public void linkFirst(Task e) {
        final Node<Task> f = first;
        final Node<Task> newNode = new Node<>(null, e, f);
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
        size++;
    }

    public void removeNode(Node<Task> x) {
        if (tasks.contains(x.item)) {
            final Node<Task> next = x.next;
            final Node<Task> prev = x.prev;

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
    }

    public int size() {
        return this.size;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void remove(int id) {
    }
}


