package ca.jrvs.practice.dataStructure.list;

import java.util.Collection;

public class LinkedJList<E> implements JList<E>{
  private static class Node<E> {
    E data;
    Node<E> next;
    Node<E> prev;

    Node (E data, Node<E> next, Node<E> prev) {
      this.data = data;
      this.next = next;
      this.prev = prev;
    }
  }

  transient int size = 0;
  transient Node<E> head;
  transient Node<E> tail;

  public LinkedJList() {
  }


  /**
   * Appends the specified element to the end of this list (optional operation).
   *
   * @param e element to be appended to this list
   * @return <tt>true</tt> (as specified by {@link Collection#add})
   * @throws NullPointerException if the specified element is null and this list does not permit
   *                              null elements
   */
  @Override
  public boolean add(E e) {
    if (e == null) {
      throw new NullPointerException("Cannot have null input");
    }

    Node<E> oldTail = tail;
    Node<E> newTail = new Node<>(e, null, oldTail);
    tail = newTail;

    if (oldTail == null) {
      head = newTail;
    } else {
      oldTail.next = newTail;
    }

    size++;
    return true;
  }

  /**
   * Returns an array containing all of the elements in this list in proper sequence (from first to
   * last element).
   *
   * <p>This method acts as bridge between array-based and collection-based
   * APIs.
   *
   * @return an array containing all of the elements in this list in proper sequence
   */
  @Override
  public Object[] toArray() {
    Object[] retArray = new Object[size];

    if (size == 0) {
      return retArray;
    }

    Node<E> traversalNode = head;
    int i = 0;

    while (traversalNode != null) {
      retArray[i++] = traversalNode.data;
      traversalNode = traversalNode.next;
    }

    return retArray;
  }

  /**
   * The size of the ArrayList (the number of elements it contains).
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Returns <tt>true</tt> if this list contains no elements.
   *
   * @return <tt>true</tt> if this list contains no elements
   */
  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Returns the index of the first occurrence of the specified element in this list, or -1 if this
   * list does not contain the element. More formally, returns the lowest index <tt>i</tt> such
   * that
   * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
   * or -1 if there is no such index.
   *
   * @param o
   */
  @Override
  public int indexOf(Object o) {
    if (size == 0) {
      return -1;
    }

    Node<E> traversalNode = head;
    int i = 0;

    while (traversalNode != null) {
      if (traversalNode.data == o)
        return i;

      i++;
      traversalNode = traversalNode.next;
    }

    return -1;
  }

  /**
   * Returns <tt>true</tt> if this list contains the specified element. More formally, returns
   * <tt>true</tt> if and only if this list contains at least one element <tt>e</tt> such that
   * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
   *
   * @param o element whose presence in this list is to be tested
   * @return <tt>true</tt> if this list contains the specified element
   * @throws NullPointerException if the specified element is null and this list does not permit
   *                              null elements
   */
  @Override
  public boolean contains(Object o) {
    if (o == null) {
      throw new NullPointerException("Null input not allowed");
    }

    return indexOf(o) != -1;
  }

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index index of the element to return
   * @return the element at the specified position in this list
   * @throws IndexOutOfBoundsException if the index is out of range (<tt>index &lt; 0 || index &gt;=
   *                                   size()</tt>)
   */
  @Override
  public E get(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Index out of bounds");
    }

    int i = 0;
    Node<E> traversalNode = head;

    while (i < index) {
      traversalNode = traversalNode.next;
      i++;
    }

    return traversalNode.data;
  }

  /**
   * Removes the element at the specified position in this list. Shifts any subsequent elements to
   * the left (subtracts one from their indices).
   *
   * @param index the index of the element to be removed
   * @return the element that was removed from the list
   * @throws IndexOutOfBoundsException {@inheritDoc}
   */
  @Override
  public E remove(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Index out of bounds");
    }

    Node<E> removedNode = head;
    Node<E> previousNode = null;
    Node<E> nextNode = null;
    E returnData = null;

    int i = 0;

    while (i < index) {
      removedNode = removedNode.next;
      i++;
    }

    previousNode = removedNode.prev;
    nextNode = removedNode.next;
    returnData = removedNode.data;

    previousNode.next = nextNode;
    nextNode.prev = previousNode;
    removedNode = null;
    size--;

    return returnData;
  }

  /**
   * Removes all of the elements from this list (optional operation). The list will be empty after
   * this call returns.
   */
  @Override
  public void clear() {
    Node<E> traversalNode = head;

    while (traversalNode != null) {
      // Get next node reference before cleared
      Node<E> nextNode = traversalNode.next;
      // Clear current node
      traversalNode.data = null;
      traversalNode.next = null;
      traversalNode.prev = null;
      // Continue
      traversalNode = nextNode;
    }

    head = tail = null;
    size = 0;

  }
}
