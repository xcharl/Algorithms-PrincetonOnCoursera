import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int count = 0;
    private Node<Item> firstNode;
    private Node<Item> lastNode;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        var newNode = new Node<>(item);

        // If list is empty, add first AND last node
        if (this.isEmpty()) {
            this.firstNode = newNode;
            this.lastNode = newNode;
        } else {
            newNode.next = this.firstNode;
            this.firstNode.previous = newNode;
            this.firstNode = newNode;
        }

        this.count++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        var newNode = new Node<>(item);

        // If list is empty, add first AND last node
        if (this.isEmpty()) {
            this.firstNode = newNode;
            this.lastNode = newNode;
        } else {
            this.lastNode.next = newNode;
            newNode.previous = this.lastNode;
            this.lastNode = newNode;
        }

        this.count++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        var firstNode = this.firstNode;
        this.firstNode = this.firstNode.next;
        this.count--;
        return firstNode.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        var lastNode = this.lastNode;
        this.lastNode = this.lastNode.previous;
        this.count--;
        return lastNode.item;
    }

    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator<>(this.firstNode);
    }

    // unit testing (required)
    public static void main(String[] args) {
        var testDeque = new Deque<String>();
        System.out.println(testDeque.isEmpty());

        testDeque.addFirst("5");
        testDeque.addFirst("4");
        testDeque.addFirst("3");
        testDeque.addFirst("2");
        testDeque.addFirst("1");
        testDeque.addLast("6");

        System.out.println(testDeque.removeFirst());
        System.out.println(testDeque.removeLast());

        System.out.println(testDeque.isEmpty());
        System.out.println(testDeque.size());

        for (var item : testDeque) {
            System.out.println(item);
        }
    }

    private class DequeIterator<T> implements Iterator<T> {

        private Node<T> current;

        public DequeIterator(Node<T> firstNode) {
            this.current = firstNode;
        }

        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public T next() {
            var item = current.item;
            current = current.next;
            return item;
        }
    }

    private class Node<U> {

        public Node(U item) {
            this.item = item;
        }

        public Node<U> next;
        public Node<U> previous;
        public final U item;
    }
}
