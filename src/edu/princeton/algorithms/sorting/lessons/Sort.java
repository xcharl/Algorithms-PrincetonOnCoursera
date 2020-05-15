package edu.princeton.algorithms.sorting.lessons;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Map;
import java.util.Random;

public class Sort {
    private static final Map<String, Algorithm> algorithmLookup = Map.of(
            "bubble", Algorithm.Bubble,
            "selection", Algorithm.Selection,
            "insertion", Algorithm.Insertion,
            "merge", Algorithm.Merge,
            "quick", Algorithm.Quick);

    public static void main(String[] args) throws Exception {
        validateInput(args);

        var algorithm = getAlgorithm(args);
        var numberOfElements = getNumberOfElements(args);

        var rng = new Random();
        var randomArray = new Integer[numberOfElements];
        for (var i = 0; i < numberOfElements; i++) {
            randomArray[i] = rng.nextInt(50);
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
            case Merge:
                sortedArray = Sort.merge(randomArray);
                break;
            case Quick:
                sortedArray = Sort.quick(randomArray);
                break;
            default:
                throw new Exception();
        }

        System.out.println(String.join(", ", convertGenericToStringArray(randomArray)));
        System.out.println(String.join(", ", convertGenericToStringArray(sortedArray)));
    }

    // Sorting methods

    public static <T extends Comparable<T>> T[] bubble(T[] inputArray) {
        validateInputArray(inputArray);
        var array = inputArray.clone();

        for (var i = 0; i < array.length - 1; i++) {
            for (var j = 0; j < array.length - i - 1; j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    swap(array, j, j + 1);
                }
            }
        }

        assert isSorted(array);
        return array;
    }

    public static <T extends Comparable<T>> T[] selection(T[] inputArray) {
        validateInputArray(inputArray);
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

        assert isSorted(array);
        return array;
    }

    public static <T extends Comparable<T>> T[] insertion(T[] inputArray) {
        validateInputArray(inputArray);
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

        assert isSorted(array);
        return array;
    }

    public static <T extends Comparable<T>> T[] quick(T[] inputArray) {
        var array = inputArray.clone();
        shuffle(array); // To guarantee performance (e.g. for nearly sorted lists).
        quickSort(array, 0, array.length - 1);
        return array;
    }

    private static <T extends Comparable<T>> void quickSort(T[] array, int low, int high) {
        if (low >= high) {
            return;
        }

        var pivot = partition(array, low, high);
        quickSort(array, low, pivot - 1); // Sort LHS
        quickSort(array, pivot + 1, high); // Sort RHS
    }

    private static <T extends Comparable<T>> void shuffle(T[] array) {
        var random = new Random();
        for (var i = 0; i < array.length; i++) {
            var randIndex = i + random.nextInt(array.length - i);
            swap(array, i, randIndex);
        }
    }

    private static <T extends Comparable<T>> int partition(T[] array, int low, int high) {
        var i = low + 1;
        var j = high;
        var pivot = low;

        while (true) {
            // Move left pointer.
            while (array[i].compareTo(array[pivot]) < 0) {
                i++;
                if (i >= high) {
                    break;
                }
            }

            // Move right pointer.
            while (array[j].compareTo(array[pivot]) > 0) {
                j--;
                if (j <= low) {
                    break;
                }
            }

            if (i >= j) {
                break;
            }

            swap(array, i, j);
        }

        swap(array, low, j);
        return j;
    }

    public static <T extends Comparable<T>> T[] merge(T[] inputArray) {
        validateInputArray(inputArray);
        var array = inputArray.clone();

        // auxArray is for copying across when merging.
        var auxArray = inputArray.clone();
        mergeSort(array, auxArray, 0, array.length - 1);
        assert isSorted(array);
        return array;
    }

    private static <T extends Comparable<T>> void mergeSort(T[] array, T[] auxArray, int low, int high) {
        if (low >= high) {
            return;
        }

        var mid = (high + low) / 2;
        mergeSort(array, auxArray, low, mid); // First half
        mergeSort(array, auxArray, mid + 1, high); // Second half
        merge(array, auxArray, low, mid, high);

        assert isSorted(array);
    }

    private static <T extends Comparable<T>> void merge(
            T[] array,
            T[] auxArray,
            int low,
            int mid,
            int high) {

        for (var i = low; i <= high; i++) {
            auxArray[i] = array[i];
        }

        var lowInd = low;
        var highInd = mid + 1;

        for (var i = low; i <= high; i++) {
            if (lowInd > mid) {
                // Low has no more elements
                array[i] = auxArray[highInd++];
            } else if (highInd > high) {
                // High has no more elements
                array[i] = auxArray[lowInd++];
            } else if (auxArray[lowInd].compareTo(auxArray[highInd]) > 0) {
                // Low is larger than high
                array[i] = auxArray[highInd++];
            } else {
                // Elements have same value, or low is smaller - take low
                array[i] = auxArray[lowInd++];
            }
        }
    }

    // Sorting helper methods

    private static <T> void swap(T[] array, int index1, int index2) {
        var temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    private static <T extends Comparable<T>> boolean isSorted(T[] array) {
        return isSorted(array, 0, array.length - 1);
    }

    private static <T extends Comparable<T>> boolean isSorted(T[] array, int low, int high) {
        for (var i = low; i < high - 1; i++) {
            if (array[i].compareTo(array[i + 1]) > 0) {
                return false;
            }
        }

        return true;
    }

    private static <T extends Comparable<T>> void validateInputArray(T[] inputArray) {
        if (inputArray == null) {
            throw new IllegalArgumentException("Null array passed to method.");
        }

        if (inputArray.length == 0) {
            throw new IllegalArgumentException("Empty array passed to method.");
        }
    }

    // Main method helpers

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
        Merge,
        Quick,
        Insertion
    }
}
