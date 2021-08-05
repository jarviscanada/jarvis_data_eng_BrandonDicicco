package ca.jrvs.apps.practice;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;

class LambdaStreamExcImpTest {
  private LambdaStreamExcImp lse = new LambdaStreamExcImp();
  String[] strings = {"test1", "test2", "test3"};
  int[] nums = {1,2,3,4,5};

  @BeforeEach
  void setUp() {
    lse = new LambdaStreamExcImp();
  }

  @org.junit.jupiter.api.Test
  void createStrStream() {
    List<String> expected = Arrays.asList("test1", "test2", "test3");
    List<String> actual = lse.createStrStream(strings).collect(Collectors.toList());
    assertEquals(expected, actual);
  }

  @org.junit.jupiter.api.Test
  void toUpperCase() {
    List<String> actual = lse.toUpperCase(strings).collect(Collectors.toList());
    List<String> expected = Arrays.asList("TEST1", "TEST2", "TEST3");
    assertEquals(expected, actual);
  }

  @org.junit.jupiter.api.Test
  void filter() {
    List<String> expected = Arrays.asList("test1", "test2");
    List<String> actual = lse.filter(lse.createStrStream(strings), "3").collect(Collectors.toList());
    assertEquals(expected, actual);
  }

  @org.junit.jupiter.api.Test
  void createIntStream() {
    List<Integer> expected = Arrays.asList(1,2,3,4,5);
    List<Integer> actual = lse.createIntStream(nums).boxed().collect(Collectors.toList());
    assertEquals(expected, actual);
  }

  @org.junit.jupiter.api.Test
  void toList() {
    List<String> expected = Arrays.asList("test1", "test2", "test3");
    List<String> actual = lse.toList(lse.createStrStream(strings));
    assertEquals(expected, actual);
  }

  @org.junit.jupiter.api.Test
  void IntToList() {
    List<Integer> expected = Arrays.asList(1,2,3,4,5);
    List<Integer> actual = lse.toList(lse.createIntStream(nums));
    assertEquals(expected, actual);
  }

  @org.junit.jupiter.api.Test
  void testCreateIntStream() {
    lse.createIntStream(1, 5).forEach(System.out::println);

  }

  @org.junit.jupiter.api.Test
  void squareRootIntStream() {
    lse.squareRootIntStream(lse.createIntStream(nums)).forEach(System.out::println);
  }

  @org.junit.jupiter.api.Test
  void getOdd() {
    lse.getOdd(lse.createIntStream(nums)).forEach(System.out::println);
  }

  @org.junit.jupiter.api.Test
  void printMessages() {
    lse.printMessages(strings, lse.getLambdaPrinter("START:", ":END"));
  }

  @org.junit.jupiter.api.Test
  void printOdd() {
    lse.printOdd(lse.createIntStream(nums), lse.getLambdaPrinter("START:", ":END"));
  }

  @org.junit.jupiter.api.Test
  void flatNestedInt() {
    List<Integer> first = Arrays.asList(1,2);
    List<Integer> second = Arrays.asList(3,4,5);
    List<List<Integer>> numbers = Arrays.asList(first, second);
    Stream<List<Integer>> actualInput = numbers.stream();

    lse.flatNestedInt(actualInput).forEach(System.out::println);
  }


}