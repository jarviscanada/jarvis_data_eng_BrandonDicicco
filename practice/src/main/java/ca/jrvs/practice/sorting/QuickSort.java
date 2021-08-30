package ca.jrvs.practice.sorting;

public class QuickSort {

  public void quickSort(int[] arr, int begin, int end) {
    if (begin < end) {
      int partitionIndex = partition(arr, begin, end);
      quickSort(arr, begin, partitionIndex - 1);
      quickSort(arr, partitionIndex + 1, end);
    }
  }

  private int partition(int[] arr, int begin, int end) {
    int pivot = arr[end];
    int i = begin;

    for (int j = begin; j < end; j++) {
      if (arr[j] <= pivot) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        i++;
      }
    }

    int temp = arr[i];
    arr[i] = arr[end];
    arr[end] = temp;

    return i;
  }
}
