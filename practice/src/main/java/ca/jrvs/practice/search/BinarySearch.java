package ca.jrvs.practice.search;

import java.util.Optional;

public class BinarySearch {
  /**
   * find the the target index in a sorted array
   *
   * @param arr input arry is sorted
   * @param target value to be searched
   * @return target index or Optional.empty() if not ound
   */
  public <E extends Comparable<E>> Optional<Integer> binarySearchRecursion(E[] arr, E target) {
    return binaryRecursionHelper(arr, target, 0, arr.length - 1);
  }

  private <E extends Comparable<E>> Optional<Integer> binaryRecursionHelper(E[] arr, E target, int left, int right) {
    // Base case
    if (right >= left) {
      int mid = (left + right) / 2;
      int compare = target.compareTo(arr[mid]);

      if (compare == 0) {
        return Optional.of(mid);
      }

      // Target > middle element, therefore must be on the right
      if (compare > 0) {
        return binaryRecursionHelper(arr, target, mid + 1, right);
      }

      // Target < middle element, must be on the left
      else {
        return binaryRecursionHelper(arr, target, left, mid - 1);
      }
    }

    // Reached when target is not present
    return Optional.empty();

  }
  /**
   * find the the target index in a sorted array
   *
   * @param arr input arry is sorted
   * @param target value to be searched
   * @return target index or Optional.empty() if not ound
   */
  public <E extends Comparable<E>> Optional<Integer> binarySearchIteration(E[] arr, E target) {
    int arrSize = arr.length;
    int left = 0;
    int right = arrSize - 1;

    while (left <= right) {
      int mid = (left + right) / 2;
      int compare = target.compareTo(arr[mid]);

      if (compare == 0) {
        return Optional.of(mid);
      }

      if (compare > 0) {
        left = mid + 1;

      } else {
        right = mid - 1;
      }
    }

    return Optional.empty();
  }
}
