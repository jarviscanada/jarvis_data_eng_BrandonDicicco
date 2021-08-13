package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/jarvisdev/Reverse-Linked-List-adea49e87a994e76975c3bf0fcbdc263
 */
public class reverseLinkedList {
  public static class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

  /**
   * Big-O: O(n)
   * Justification: Traverses the entire linked list of n nodes and performs simple swaps.
   * @param head the head of the initial linked list
   * @return the head of the reverse linked list
   */
  public ListNode reverseIter(ListNode head) {
    if (head.next == null) {
      return head;
    }

    ListNode prev = null;
    ListNode current = head;
    ListNode next;

    while (current != null) {
      next = current.next;
      current.next = prev;
      prev = current;
      current = next;
    }

    return prev;
  }

  /**
   * Big-O: O(n)
   * Justification: Visits every node in the linked list once
   */
  public ListNode reverseRecur(ListNode head) {
    return reverseRecurHelper(head, null);
  }

  private ListNode reverseRecurHelper(ListNode head, ListNode prev) {
    if (head == null) {
      return prev;
    }

    ListNode next = head.next;
    head.next = prev;
    return reverseRecurHelper(next, head);
  }
}


