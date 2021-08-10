package ca.jrvs.practice.dataStructure.list;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ArrayJList<E> implements JList<E>{

  /**
   * Default initial capacity.
   */
  private static final int DEFAULT_CAPACITY = 10;

  /**
   * The array buffer into which the elements of the ArrayList are stored.
   * The capacity of the ArrayList is the length of this array buffer.
   */
  transient Object[] elementData; // non-private to simplify nested class access
  /**
   * The size of the ArrayList (the number of elements it contains).
   */
  private int size;

  /**
   * Constructs an empty list with the specified initial capacity.
   *
   * @param  initialCapacity  the initial capacity of the list
   * @throws IllegalArgumentException if the specified initial capacity
   *         is negative
   */
  public ArrayJList(int initialCapacity) {
    if (initialCapacity > 0) {
      this.elementData = new Object[initialCapacity];
    } else {
      throw new IllegalArgumentException("Illegal Capacity: " +
          initialCapacity);
    }
  }

  /**
   * Constructs an empty list with an initial capacity of ten.
   */
  public ArrayJList() {
    this(DEFAULT_CAPACITY);
  }


  @Override
  public boolean add(E e) {
    if (size == elementData.length) {
      elementData = Arrays.copyOf(elementData, 2 * size);
    }

    elementData[size++] = e;
    return true;
  }

  @Override
  public Object[] toArray() {
    return Arrays.copyOf(elementData, size);
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public int indexOf(Object o) {
    return IntStream.range(0, size).filter(i -> elementData[i] == o).findFirst().orElse(-1);
  }

  @Override
  public boolean contains(Object o) {
    return indexOf(o) != -1;
  }

  @Override
  public E get(int index) {
    if (index < 0 || index >= size || size == 0)
      throw new IndexOutOfBoundsException("Index out of bounds");

    return (E) elementData[index];
  }

  @Override
  public E remove(int index) {
    if (index < 0 || index >= size || size == 0)
      throw new IndexOutOfBoundsException("Index out of bounds");

    E removedItem = (E) elementData[index];
    int numMoved = size - index - 1;

    if (numMoved > 0) {
      System.arraycopy(elementData, index + 1, elementData, index, numMoved);
    }

    elementData[--size] = null;

    return removedItem;
  }

  @Override
  public void clear() {
    for (int i = 0; i < size; i++) {
      elementData[i] = null;
    }

    size = 0;
  }
}
