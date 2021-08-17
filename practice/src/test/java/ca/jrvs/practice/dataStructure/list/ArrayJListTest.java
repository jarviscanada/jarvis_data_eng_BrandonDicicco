package ca.jrvs.practice.dataStructure.list;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArrayJListTest {

  @Test
  public void add() {
    JList<String> testList = new ArrayJList<>();
    testList.add("Hello");

    assertEquals("Hello", testList.get(0));
  }

  @Test
  public void toArray() {
    JList<String> testList = new ArrayJList<>();
    testList.add("Hello");
    String[] testArr = {"Hello"};

    assertArrayEquals(testArr, testList.toArray());
  }

  @Test
  public void size() {
    JList<String> testList = new ArrayJList<>();
    testList.add("Hello");

    assertEquals(1, testList.size());
  }

  @Test
  public void isEmpty() {
    JList<String> testList = new ArrayJList<>();
    JList<String> testList2 = new ArrayJList<>();
    testList.add("Hello");

    assertFalse(testList.isEmpty());
    assertTrue(testList2.isEmpty());
  }

  @Test
  public void indexOf() {
    JList<String> testList = new ArrayJList<>();
    testList.add("Hello");
    testList.add("World");

    assertEquals(1, testList.indexOf("World"));
  }

  @Test
  public void contains() {
    JList<String> testList = new ArrayJList<>();
    testList.add("Hello");

    assertTrue(testList.contains("Hello"));
  }

  @Test
  public void get() {
    JList<String> testList = new ArrayJList<>();
    testList.add("Hello");

    assertEquals("Hello", testList.get(0));
  }

  @Test
  public void remove() {
    JList<String> testList = new ArrayJList<>();
    testList.add("Hello");

    assertEquals("Hello", testList.remove(0));
    assertEquals(0, testList.size());
  }

  @Test
  public void clear() {
    JList<String> testList = new ArrayJList<>();
    testList.add("Hello");

    assertTrue(testList.size() > 0);
    testList.clear();
    assertEquals(0, testList.size());
  }
}