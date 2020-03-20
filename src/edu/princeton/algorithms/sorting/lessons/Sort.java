package edu.princeton.algorithms.sorting.lessons;

import java.util.Map;
import java.util.Random;

public class Sort {
    private static final Map<String, Algorithm> algorithmLookup = Map.of(
            "bubble", Algorithm.Bubble,
            "selection", Algorithm.Selection,
            "insertion", Algorithm.Insertion);

    public static <T extends Comparable<T>> T[] bubble(T[] inputArray) {
        var array = inputArray.clone();

        for (var i = 0; i < array.length - 1; i++) {
            for (var j = 0; j < array.length - i - 1; j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    swap(array, j, j + 1);
                }
            }
        }

        return array;
    }

    public static <T extends Comparable<T>> T[] selection(T[] inputArray) {
        var array = inputArray.clone();

        for (var i = 0; i < array.length; i++) {
            var currentMin = i;
            for (var j = i; j < array.length; j++) {
                if (array[j].compareTo(array[currentMin]) < 0) {
                    currentMin = j;
                }
            }

            swap(array, i, currentMin);
        }

        return array;
    }

    public static <T extends Comparable<T>> T[] insertion(T[] inputArray) {
        var array = inputArray.clone();

        for (var i = 1; i < array.length; i++) {
            for (var j = i; j > 0; j--) {
                if (array[j - 1].compareTo(array[j]) > 0) {
                    swap(array, j, j - 1);
                } else {
                    break;
                }
            }
        }

        return array;
    }

    public static void main(String[] args) throws Exception {
        validateInput(args);

        var algorithm = getAlgorithm(args);
        var numberOfElements = getNumberOfElements(args);

        var rng = new Random();
        var randomArray = new Integer[numberOfElements];
        for (var i = 0; i < numberOfElements; i++) {
            randomArray[i] = rng.nextInt(100);
        }

        Integer[] sortedArray;
        switch (algorithm) {
            case Bubble:
                sortedArray = Sort.bubble(randomArray);
                break;
            case Selection:
                sortedArray = Sort.selection(randomArray);
                break;
            case Insertion:
                sortedArray = Sort.insertion(randomArray);
                break;
            default:
                throw new Exception();
        }

        System.out.println(String.join(" ", convertGenericToStringArray(randomArray)));
        System.out.println(String.join(" ", convertGenericToStringArray(sortedArray)));
        //noinspection ResultOfMethodCallIgnored
        System.in.read();
    }

    private static <T> void swap(T[] array, int index1, int index2) {
        var temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    private static void validateInput(String[] args) {
        if (args == null || args.length == 0) {
            printUsageMessage();
            throw new IllegalArgumentException("No arguments passed to the program.");
        }

        if (args.length > 2) {
            printUsageMessage();
            throw new IllegalArgumentException("Too many arguments passed to the program.");
        }
    }

    private static Algorithm getAlgorithm(String[] args) {
        Algorithm algorithm;
        if (!algorithmLookup.containsKey(args[0].toLowerCase())) {
            printUsageMessage();
            throw new IllegalArgumentException("Algorithm name not recognised. Available values: "
                    + String.join(", ", algorithmLookup.keySet()));
        } else {
            algorithm = algorithmLookup.get(args[0].toLowerCase());
        }
        return algorithm;
    }

    private static int getNumberOfElements(String[] args) {
        int numberOfElements;
        try {
            numberOfElements = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            printUsageMessage();
            throw new IllegalArgumentException("Cannot parse number of random elements.");
        }
        return numberOfElements;
    }

    private static void printUsageMessage() {
        System.out.println("Usage: \"java Sort [Algorithm] [Number of Random Elements]\"");
    }

    private static <T> String[] convertGenericToStringArray(T[] genericArray) {
        var outputStrings = new String[genericArray.length];
        for (var i = 0; i < genericArray.length; i++) {
            outputStrings[i] = genericArray[i].toString();
        }

        return outputStrings;
    }

    private enum Algorithm {
        Bubble,
        Selection,
        Insertion
    }
}
