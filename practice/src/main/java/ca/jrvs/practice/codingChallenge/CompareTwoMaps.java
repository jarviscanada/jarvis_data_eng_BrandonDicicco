package ca.jrvs.practice.codingChallenge;

import java.util.Map;
import java.util.Map.Entry;

/**
 * Ticket: https://www.notion.so/jarvisdev/How-to-compare-two-maps-4162f4bccfb0493ea0d9d055742fa270
 */
public class CompareTwoMaps {

  /** Big-O: O()
   *  Justification: O(n). Compares entry set of each map which contains n entries.
   * @param m1 map 1
   * @param m2 map 2
   * @param <K> key
   * @param <V> value
   * @return if maps are equal
   */
  public <K,V> boolean compareMapsApi(Map<K,V> m1, Map<K,V> m2){
    return m1.equals(m2);
  }

  /**
   * Big-O: O(n)
   * Justification: goes through n entries and performs O(1) operations to check equality
   */
  public <K,V> boolean compareMaps(Map<K,V> m1, Map<K,V> m2) {
    // Base cases
    // Same reference
    if (m1 == m2) {
      return true;
    }

    // Different sizes
    if (m1.size() != m2.size()) {
      return false;
    }

    // Compare every entry in the maps if same size
    for (Entry<K,V> entry : m1.entrySet()) {
      K currentKey = entry.getKey();
      V currentValue = entry.getValue();

      // Only equal if both maps have the same key and same value for that key
      if (m2.containsKey(currentKey)) {
        // Key in both maps. Value null in both.
        if (currentValue == null && m2.get(currentKey) == null) {
          continue;
        }
        // Key in both but different values.
        if (!m2.get(currentKey).equals(currentValue) || m2.get(currentKey) == null) {
          return false;
        }
      } else {
        return false;
      }
    }

    return true;
  }
}
