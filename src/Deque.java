import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int count = 0;
    private Node firstNode;
    private Node lastNode;

    // Construct an empty deque.
    public Deque() {
    }

    // Is the deque empty?
    public boolean isEmpty() {
        return this.count == 0;
    }

    // Return the number of items on the deque.
    public int size() {
        return this.count;
    }

    // Add the item to the front.
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        var newNode = new Node(item);

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

    // Add the item to the back.
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        var newNode = new Node(item);

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

    // Remove and return the item from the front.
    public Item removeFirst() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        var oldFirstNode = this.firstNode;
        this.firstNode = oldFirstNode.next;

        // Prevent loitering.
        if (this.firstNode == null) {
            // If firstNode is now null, it means the deque is empty.
            this.lastNode = null;
        } else {
            // Else just remove references to the old last node.
            this.firstNode.previous = null;
        }

        this.count--;
        return oldFirstNode.item;
    }

    // Remove and return the item from the back.
    public Item removeLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        var oldLastNode = this.lastNode;
        this.lastNode = oldLastNode.previous;

        // Prevent loitering.
        if (this.lastNode == null) {
            // If lastNode is now null, it means the deque is empty.
            this.firstNode = null;
        } else {
            // Else just remove references to the old last node.
            this.lastNode.next = null;
        }

        this.count--;
        return oldLastNode.item;
    }

    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator(this.firstNode);
    }

    // unit testing (required)
    public static void main(String[] args) {
        var testDeque = new Deque<String>();

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

        var td2 = new Deque<String>();
        td2.addFirst("10");
        td2.addFirst("9");
        td2.addFirst("8");
        td2.addFirst("7");
        td2.addFirst("6");
        td2.addFirst("5");
        td2.addFirst("4");
        td2.addFirst("3");
        td2.addFirst("2");
        td2.addFirst("1");

        var z = 0;
        for (var item : td2) {
            System.out.println(item);
            z++;
        }

        System.out.println(z);
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current;

        public DequeIterator(Node firstNode) {
            this.current = firstNode;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("No elements in the deque.");
            }

            var item = current.item;
            current = current.next;
            return item;
        }
    }

    private class Node {

        public Node(Item item) {
            this.item = item;
        }

        public Node next;
        public Node previous;
        public final Item item;
    }
}
