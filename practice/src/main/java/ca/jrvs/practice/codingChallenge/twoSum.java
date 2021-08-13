package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Ticket: https://www.notion.so/jarvisdev/Two-Sum-b6f9dc7a32ef4b26821daaf997d66a94
 */
public class twoSum {

  /**
   * Big-O: O(n^2)
   * Justification: Looping through the array O(n) times for every element (n * n)
   * @param nums input array
   * @param target target number to sum to
   * @return indices of pair of numbers
   */
  public int[] bruteForceTwoSum(int[] nums, int target) {
    int numRemaining;
    int currentNumber;

    for (int i = 0; i < nums.length; i++) {
      currentNumber = nums[i];
      numRemaining = target - currentNumber;

      for (int j = i + 1; j < nums.length; j++) {
        if (nums[j] == numRemaining) {
          return new int[]{i, j};
        }
      }
    }

    return new int[2];
  }

  /**
   * Big-O: O(nlogn)
   * Justification: Sorting the array is done in O(nlogn) time. It also does binary search (O(n)) n times, which also gives O(nlogn).
   */
  public int[] sortedTwoSum(int[] nums, int target) {
    Arrays.sort(nums);

    int numRemaining;
    int currentNumber;
    int found;

    for (int i = 0; i < nums.length; i++) {
      currentNumber = nums[i];
      numRemaining = target - currentNumber;
      found = Arrays.binarySearch(nums, i+1, nums.length, numRemaining);

      if (found > 0) {
        return new int[]{i, found};
      }
    }

    return new int[2];
  }

  /**
   * Big-O: O(n)
   * Justification: Inserting into and accessing the hashmap n times.
   */
  public int[] mapTwoSum(int[] nums, int target) {
    Map<Integer, Integer> valuesMap = new HashMap<>();
    int remainder;
    int currentNumber;

    for (int i = 0; i < nums.length; i++) {
      currentNumber = nums[i];
      remainder = target - currentNumber;

      // For duplicate elements (i.e. [2,2], target = 4), need to check index. Since this doesn't put the same key twice, it works
      if (valuesMap.containsKey(remainder) && i != valuesMap.get(remainder)) {
        return new int[]{valuesMap.get(remainder), i};
      }

      valuesMap.put(currentNumber, i);

    }

    return new int[2];
  }
}
