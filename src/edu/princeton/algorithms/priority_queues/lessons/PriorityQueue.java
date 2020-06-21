package edu.princeton.algorithms.priority_queues.lessons;

/**
 * Simple implementation of a priority queue using a binary heap.
 * Lacking things like, e.g., input verification, but it at least demonstrates the concept.
 */
public class PriorityQueue<Key extends Comparable<Key>> {

    // Java doesn't like generic arrays - there's almost certainly
    // a better way to do this but casts will do for now.
    private Object[] pq;
    private int size;

    public PriorityQueue() {
        this.pq = new Object[4];
    }

    public PriorityQueue(int initCapacity) {
        this.pq = new Object[initCapacity];
    }

    public PriorityQueue(Key[] collection) {
        this.pq = collection.clone();
    }

    public void insert(Key key) {
        this.resizeIfRequired(this.size + 1);
        this.pq[this.size] = key;
        this.swim(this.size++);
    }

    public Key delMax() {
        var max = this.pq[0];
        swap(0, --this.size);
        this.sink(0);
        this.pq[this.size] = null;
        this.resizeIfRequired(this.size);
        return (Key)max;
    }

    public Key max() {
        return (Key)this.pq[0];
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public int size() {
        return this.size;
    }

    private void swim(int childInd) {
        var parentInd = this.getParent(childInd);

        while (childInd > 0
                && ((Key)this.pq[childInd]).compareTo((Key)this.pq[parentInd]) > 0) {
            this.swap(childInd, parentInd);
            childInd = parentInd;
            parentInd = this.getParent(childInd);
        }
    }

    private void sink(int parentInd) {
        var childInds = this.getChildren(parentInd);

        while (parentInd < this.size / 2) {
            var childInd1 = childInds[0];
            var childInd2 = childInds[1];
            var child1 = (Key)this.pq[childInd1];
            var child2 = (Key)this.pq[childInd2];

            if (child1.compareTo(child2) > 0) {
                // Child1 bigger than Child2.
                this.swap(childInd1, parentInd);
                parentInd = childInd1;
            } else if (((Key)this.pq[parentInd]).compareTo(child2) > 0) {
                // Parent bigger than both children - finished sinking.
                break;
            } else {
                // Child2 bigger than/equal to Child1.
                this.swap(childInd2, parentInd);
                parentInd = childInd2;
            }

            childInds = this.getChildren(parentInd);
        }
    }

    private int getParent(int childIndex) {
        return (childIndex - 1) / 2;
    }

    private int[] getChildren(int parentIndex) {
        var childIndexes = new int[2];
        childIndexes[0] = (parentIndex * 2) + 1;
        childIndexes[1] = (parentIndex * 2) + 2;
        return childIndexes;
    }

    private void swap(int ind1, int ind2) {
        var temp = this.pq[ind1];
        this.pq[ind1] = this.pq[ind2];
        this.pq[ind2] = temp;
    }

    private void resizeIfRequired(int capacityRequired) {
        if (this.size == 0 || capacityRequired == 0) {
            this.resizeArray(1);
        } else if (this.pq.length < capacityRequired) {
            this.resizeArray(this.pq.length * 2);
        } else if (this.pq.length / 4 >= capacityRequired) {
            this.resizeArray(this.pq.length / 2);
        }
    }

    private void resizeArray(int newCapacity) {
        var newArray = new Object[newCapacity];
        for (var i = 0; i < this.size; i++) {
            newArray[i] = this.pq[i];
        }

        this.pq = newArray;
    }
}
