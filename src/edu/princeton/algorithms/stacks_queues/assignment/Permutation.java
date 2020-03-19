package edu.princeton.algorithms.stacks_queues.assignment;

import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    public static void main(String[] args) {
        var numStringsToOutput = Integer.parseInt(args[0]);
        var randomisedQueue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            randomisedQueue.enqueue(StdIn.readString());
        }

        for (var i = 0; i < numStringsToOutput; i++) {
            System.out.println(randomisedQueue.dequeue());
        }
    }
}
