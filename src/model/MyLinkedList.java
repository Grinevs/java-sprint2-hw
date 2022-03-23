package model;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class MyLinkedList<T, E> {
    private Node<E> first;
    private Node<E> last;
    private int size = 0;
    private List<Task> tasks= new ArrayList<>();

    void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
    }

    private void linkFirst(E e) {
        final Node<E> f = first;
        final Node<E> newNode = new Node<>(null, e, f);
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
        size++;
    }

    public int size() {
        return this.size;
    }

    public List<Task> getTasks() {
        return tasks;
    }

}


