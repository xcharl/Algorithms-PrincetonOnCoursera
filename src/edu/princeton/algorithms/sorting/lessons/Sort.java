package edu.princeton.algorithms.sorting.lessons;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.Random;

public class Sort {
    private static final Map<String, Algorithm> algorithmLookup = Map.of(
            "bubble", Algorithm.Bubble,
            "selection", Algorithm.Selection,
            "insertion", Algorithm.Insertion);

    public static <T extends Comparable<T>> T[] bubble(T[] inputArray) {
        var arrayToSort = inputArray.clone();

        for (var i = 0; i < arrayToSort.length - 1; i++) {
            for (var j = 0; j < arrayToSort.length - i - 1; j++) {
                if (arrayToSort[j].compareTo(arrayToSort[j + 1]) > 0) {
                    var temp = arrayToSort[j];
                    arrayToSort[j] = arrayToSort[j + 1];
                    arrayToSort[j + 1] = temp;
                }
            }
        }

        return arrayToSort;
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
            case Insertion:
            case Selection:
            default:
                throw new Exception();
        }

        System.out.println(String.join(" ", convertGenericToStringArray(randomArray)));
        System.out.println(String.join(" ", convertGenericToStringArray(sortedArray)));
        System.in.read();
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
