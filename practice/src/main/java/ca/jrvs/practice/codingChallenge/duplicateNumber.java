package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class duplicateNumber {

  /**
   * Big-O: O(nlogn)
   * Justification: Sort requires O(nlogn). The search afters is linear and is O(n).
   * @param nums list of numbers
   * @return the duplicate number
   */
  public int findDuplicateSort(int[] nums) {
    Arrays.sort(nums);

    int prev, curr;

    for (int i = 1; i < nums.length; i++) {
      prev = nums[i-1];
      curr = nums[i];

      if (prev == curr) {
        return prev;
      }
    }

    return -1;

  }

  /**
   * Big-O: O(n)
   * Justification: A hashset can insert and look up in constant time. This is done at most n times, which is O(n).
   */
  public int findDuplicateSet(int[] nums) {
    Set<Integer> noDuplicates = new HashSet<>();

    for (int currNum : nums) {
      if (noDuplicates.contains(currNum)) {
        return currNum;
      }

      noDuplicates.add(currNum);
    }

    return -1;
  }
}
