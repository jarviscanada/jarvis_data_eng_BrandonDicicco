package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayMinMax {

  public static void main(String[] args) {
    int[] arr = {1,2,3};
    ArrayMinMax arrayMinMax = new ArrayMinMax();

    arrayMinMax.minMaxLoop(arr);
    arrayMinMax.minMaxStream(arr);
    arrayMinMax.minMaxCollections(arr);
  }
  public void minMaxLoop(int[] array) {
    int max = Integer.MIN_VALUE;
    int min = Integer.MAX_VALUE;

    for (int num : array) {
      if (num > max) {
        max = num;
      }

      if (num < min) {
        min = num;
      }
    }

    System.out.println("Max = " + max + ", Min = " + min);
  }

  public void minMaxStream(int[] array) {
    int min = Arrays.stream(array).min().getAsInt();
    int max = Arrays.stream(array).max().getAsInt();

    System.out.println("Max = " + max + ", Min = " + min);
  }

  public void minMaxCollections(int[] array) {
    List<Integer> list = Arrays.stream(array).boxed().collect(Collectors.toList());
    int max = Collections.max(list);
    int min = Collections.min(list);

    System.out.println("Max = " + max + ", Min = " + min);
  }
}
