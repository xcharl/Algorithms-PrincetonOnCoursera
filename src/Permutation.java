import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    public static void main(String[] args) {
        var numStringsToOutput = Integer.parseInt(args[0]);
        var randomisedQueue = new RandomizedQueue<String>();

        var inputStrings = StdIn.readAllStrings();

        for (var inputString : inputStrings) {
            randomisedQueue.enqueue(inputString);
        }

        for (var i = 0; i < numStringsToOutput; i++) {
            System.out.println(randomisedQueue.dequeue());
        }
    }
}
