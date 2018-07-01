package PA4;

import java.util.NoSuchElementException;

/**
 * @author Your Name Here
 * INFS 519
 * Fall 2016
 */

/**
 * MergeSort class sorts items of type Comparable through recursively calling
 * mergeSort function to divide the array into halves everytime it is called
 * until the start index becomes equal to end index
 * 
 * There are three major functions in this class, sort() which is called by the
 * main() function. sort calls the mergeSort(). mergeSort() calls itself
 * recursively and then calls merge() in order to merge the divided array into
 * the output array
 */

public class MergeSort {
	/**
	 * Sorts the items ascending, from smallest to largest.
	 * 
	 * @param items
	 *            (modified)
	 * @throws NullPointerException
	 *             if items is null
	 */
	public static void sort(Comparable[] items) {
		// ADD CODE

		if (items != null) {
			Comparable[] auxArray = new Comparable[items.length];
			mergeSort(items, auxArray, 0, items.length - 1);

		}

		else {
			throw new NoSuchElementException("Input empty");
		}
		// Postcondition
		assert SortUtil.isSorted(items, 0, items.length - 1);
	}

	/**
	 * This function takes in parameters like the array of items to be sorted of
	 * type Comparable, an auxiliary array, the start point of array , the mid
	 * point of array and the end point of array. This function calls itself
	 * recursively in order to break down the array into halves till the point
	 * where start index is lower than end end index after yhr break down of
	 * array into halves, merge function is called in order to sort and merge
	 * the elements
	 * 
	 * @param input
	 *            array of Comparable type, Auxiliary array, start index of
	 *            array, mid index of array and end index of array
	 *
	 */
	public static void mergeSort(Comparable[] items, Comparable[] aux, int lo, int hi) {
		// ADD CODE
		if (lo < hi) {

			int center = (lo + hi) / 2;

			mergeSort(items, aux, lo, center);
			mergeSort(items, aux, center + 1, hi);
			merge(items, aux, lo, center + 1, hi);
		}

	}

	/**
	 * This function takes in parameters like the array of items to be sorted of
	 * type Comparable, an auxiliary array, the start point of array , the mid
	 * point of array and the end point of array. This sort merges the elements
	 * of array into aux array one by one after all the elements are sorted
	 * 
	 * @param input
	 *            array of Comparable type, Auxiliary array, start index of
	 *            array, mid index of array and end index of array
	 *
	 */
	public static void merge(Comparable[] items, Comparable[] aux, int lo, int mid, int hi) {
		int le = mid - 1;
		int temp = lo;
		int num = hi - lo + 1;

		while ((lo <= le) && (mid <= hi)) {

			if (less(items[lo], items[mid])) {

				aux[temp++] = items[lo++];

			} else
				aux[temp++] = items[mid++];

		}
		while (lo <= le) {
			aux[temp++] = items[lo++];
		}
		while (mid <= hi) {
			aux[temp++] = items[mid++];
		}
		for (int k = 1; k <= num; k++, hi--) {
			items[hi] = aux[hi];
		}
		// Preconditions using java assert, can also use junit tests
		assert SortUtil.isSorted(items, lo, mid);
		assert SortUtil.isSorted(items, mid + 1, hi);

		// ADD CODE

		// Postcondition
		assert SortUtil.isSorted(items, lo, hi);
	}

	// --------------------- DO NOT MODIFY BELOW THIS -----------------------//

	/**
	 * USE IS OPTIONAL, NOT NECESSARY Utility method that compares a and b.
	 * Conceptually return a &lt b.
	 * 
	 * @param a
	 * @param b
	 * @return true if a strictly less than b, false otherwise
	 */
	public static boolean less(Comparable a, Comparable b) {
		return a.compareTo(b) < 0;
	}

	/**
	 * Unit tests MergeSort.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MergeSort mer = new MergeSort();
		Comparable[] items = { 45, 56, 23, 46, 70 };
		// items = {34,45,25,89};
		mer.sort(items);
		for (int i = 0; i <= 4; i++)
			System.out.println(items[i]);
	}

}
