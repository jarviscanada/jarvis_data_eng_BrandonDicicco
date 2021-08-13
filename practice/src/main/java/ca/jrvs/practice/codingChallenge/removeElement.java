package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/jarvisdev/Remove-Element-5fb79f9949204ba0bd07f613623c6786
 */
public class removeElement {

  /**
   * Big-O: O(n)
   * Justification: Every element of the array is checked. Start + End = n
   * @param nums list of numbers
   * @param value value to be removed
   * @return amount of numbers that aren't value
   */
  public int removeValue(int[] nums, int value) {
    int start = 0;
    int end = nums.length;

    // Shift all values that aren't the given value to the front of the array
    while (start < end) {
      if (nums[start] == value) {
        nums[start] = nums[end - 1];
        end--;
      } else {
        start++;
      }
    }

    return end;
  }
}
