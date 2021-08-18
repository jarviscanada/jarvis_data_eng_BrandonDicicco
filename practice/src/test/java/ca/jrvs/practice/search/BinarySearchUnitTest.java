package ca.jrvs.practice.search;

import static org.junit.Assert.*;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

public class BinarySearchUnitTest {

  Integer[] sortedArr;
  Integer[] emptyArr;
  private BinarySearch binarySearcher;

  @Before
  public void setUp() throws Exception {
    sortedArr = new Integer[]{1,2,3,4,5,6,7};
    emptyArr = new Integer[0];
    binarySearcher = new BinarySearch();
  }

  @Test
  public void binarySearchRecursion() {
    // Search empty
    assertFalse(binarySearcher.binarySearchRecursion(emptyArr, 1).isPresent());

    // Search not in sorted
    assertFalse(binarySearcher.binarySearchRecursion(sortedArr, 0).isPresent());
    assertFalse(binarySearcher.binarySearchRecursion(sortedArr, 10).isPresent());

    // Search is in sorted
    assertTrue(binarySearcher.binarySearchRecursion(sortedArr, 1).isPresent());
    assertTrue(binarySearcher.binarySearchRecursion(sortedArr, 4).isPresent());
    assertTrue(binarySearcher.binarySearchRecursion(sortedArr, 7).isPresent());

  }

  @Test
  public void binarySearchIteration() {
    // Search empty
    assertFalse(binarySearcher.binarySearchIteration(emptyArr, 1).isPresent());

    // Search not in sorted
    assertFalse(binarySearcher.binarySearchIteration(sortedArr, 0).isPresent());
    assertFalse(binarySearcher.binarySearchIteration(sortedArr, 10).isPresent());

    // Search is in sorted
    assertTrue(binarySearcher.binarySearchIteration(sortedArr, 1).isPresent());
    assertTrue(binarySearcher.binarySearchIteration(sortedArr, 4).isPresent());
    assertTrue(binarySearcher.binarySearchIteration(sortedArr, 7).isPresent());
  }
}