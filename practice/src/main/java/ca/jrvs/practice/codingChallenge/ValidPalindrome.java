package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;

/**
 * Ticket: https://www.notion.so/jarvisdev/Valid-Palindrome-75c62e96d8504fbf835faa6ef082b416
 */
public class ValidPalindrome {

  /**
   * Big-O: O(n) where n is number of characters in s
   * Justification: checks every letter character in s once
   * @param s text to be checked
   * @return true if s is a palindrome, false otherwise
   */
  public boolean validPalindrome(String s) {
    if (s.length() == 1) {
      return true;
    }

    int start = 0;
    int end = s.length() - 1;

    while (start < end) {
      char currentStartCharacter = s.charAt(start);

      if (!Character.isLetterOrDigit(currentStartCharacter)) {
        start++;
        continue;
      }

      char currentEndCharacter = s.charAt(end);

      if (!Character.isLetterOrDigit(currentEndCharacter)) {
        end--;
        continue;
      }

      if (Character.toLowerCase(currentStartCharacter) != Character.toLowerCase(currentEndCharacter)) {
        return false;
      }

      start++;
      end--;
    }

    return true;
  }

  /**
   * Big-O: O(n) where n is the number of characters in s
   * Justification: compares every character in s once, but uses O(n) space for recursion
   */
  public boolean validPalindromeRecursive(String s) {
    return validPalindromeHelper(s, 0, s.length() - 1);
  }

  public boolean validPalindromeHelper(String s, int start, int end) {
    if (start >= end) {
      return true;
    }

    if (!Character.isLetterOrDigit(s.charAt(start))) {
      return validPalindromeHelper(s, start + 1, end);
    }

    if (!Character.isLetterOrDigit(s.charAt(end))) {
      return validPalindromeHelper(s, start, end - 1);
    }

    if (Character.toLowerCase(s.charAt(start)) == Character.toLowerCase(s.charAt(end))) {
      return validPalindromeHelper(s, start + 1, end - 1);
    }

    return false;
  }
}
