package edu.princeton.algorithms.stacks_queues.assignment;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int count;

    // Construct an empty randomized queue.
    public RandomizedQueue() {
        this.queue = (Item[]) new Object[1];
    }

    // Is the randomized queue empty?
    public boolean isEmpty() {
        return this.count == 0;
    }

    // Return the number of items on the randomized queue.
    public int size() {
        return this.count;
    }

    // Add an item.
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can't queue a null item.");
        }

        this.resizeIfRequired(this.count + 1);
        this.queue[this.count] = item;
        this.count++;
    }

    // Remove and return a random item.
    public Item dequeue() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("No elements in the queue.");
        }

        this.resizeIfRequired(this.count - 1);
        var randomIndex = StdRandom.uniform(0, this.count);
        var returnedItem = this.queue[randomIndex];

        // Move the last item to the now-empty space.
        this.queue[randomIndex] = this.queue[this.count - 1];
        this.queue[this.count - 1] = null;
        this.count--;

        return returnedItem;
    }

    // Return a random item (but do not remove it).
    public Item sample() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("No elements in the queue.");
        }

        var randomIndex = StdRandom.uniform(0, this.count);
        return this.queue[randomIndex];
    }

    // Return an independent iterator over items in random order.
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<>(this.queue, this.count);
    }

    // unit testing (required)
    // Obviously this isn't actually unit testing, but is a requirement of the assignment.
    public static void main(String[] args) {
        var testQueue = new RandomizedQueue<String>();

        System.out.println("Enqueues:");
        testQueue.enqueue("1");
        testQueue.enqueue("2");
        testQueue.enqueue("3");
        testQueue.enqueue("4");
        testQueue.enqueue("5");
        testQueue.enqueue("6");
        testQueue.enqueue("7");
        testQueue.enqueue("8");
        testQueue.enqueue("9");
        testQueue.enqueue("10");

        System.out.println("Dequeue/Sample:");
        System.out.println(testQueue.dequeue());
        System.out.println(testQueue.sample());

        System.out.println("Iterator:");
        for (var item : testQueue) {
            System.out.println(item);
        }

        System.out.println("Sizes:");
        System.out.println(testQueue.isEmpty());
        System.out.println(testQueue.size());

        System.out.println("Dequeues:");
        System.out.println(testQueue.dequeue());
        System.out.println(testQueue.dequeue());
        System.out.println(testQueue.dequeue());
        System.out.println(testQueue.dequeue());
        System.out.println(testQueue.dequeue());
        System.out.println(testQueue.dequeue());
        System.out.println(testQueue.dequeue());
        System.out.println(testQueue.dequeue());
        System.out.println(testQueue.dequeue());
    }

    private void resizeIfRequired(int capacityRequired) {
        if (this.count == 0 || capacityRequired == 0) {
            this.resizeArray(1);
        } else if (this.queue.length < capacityRequired) {
            this.resizeArray(this.queue.length * 2);
        } else if (this.queue.length / 4 >= capacityRequired) {
            this.resizeArray(this.queue.length / 2);
        }
    }

    private void resizeArray(int newCapacity) {
        var newArray = (Item[]) new Object[newCapacity];

        for (var i = 0; i < this.count; i++) {
            newArray[i] = this.queue[i];
        }

        this.queue = newArray;
    }

    private class RandomizedQueueIterator<T> implements Iterator<T> {

        private final T[] randomisedQueue;
        private int currentIndex = 0;

        public RandomizedQueueIterator(T[] queue, int trueQueueCount) {
            var populatedQueue = (T[]) new Object[trueQueueCount];
            for (var i = 0; i < trueQueueCount; i++) {
                populatedQueue[i] = queue[i];
            }

            StdRandom.shuffle(populatedQueue);
            this.randomisedQueue = populatedQueue;
        }

        @Override
        public boolean hasNext() {
            return this.currentIndex < this.randomisedQueue.length;
        }

        @Override
        public T next() {
            if (this.currentIndex >= this.randomisedQueue.length) {
                throw new NoSuchElementException("No more items in the queue.");
            }

            return this.randomisedQueue[currentIndex++];
        }
    }
}
