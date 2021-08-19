package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class CompareTwoMapsUnitTest {

  Map<Integer, Integer> m1;
  Map<Integer, Integer> m2;
  Map<Integer, Integer> m3;
  Map<Integer, Integer> m4;
  CompareTwoMaps compare;
  @Before
  public void setUp() throws Exception {
    m1 = new HashMap<>();
    m2 = new HashMap<>();
    m3 = new HashMap<>();
    m4 = new HashMap<>();
    m1.put(1,1);
    m1.put(2,2);
    m1.put(3,3);
    m2.putAll(m1);
    m3.putAll(m2);
    m3.put(4,4);
    m4.put(1,1);
    m4.put(2,2);
    m4.put(3,4);
    compare = new CompareTwoMaps();
  }

  @Test
  public void compareMaps() {
    // Equal maps
    assertTrue(compare.compareMaps(m1, m2));

    // Unequal maps (size)
    assertFalse(compare.compareMaps(m1, m3));

    // Unequal maps (different values)
    assertFalse(compare.compareMaps(m1, m4));
  }
}