package ca.jrvs.practice.dataStructure.list;

import java.util.Arrays;
import java.util.Comparator;

public class EmployeeSort {
  public static class EmployeeCompare implements Comparator<Employee> {

    @Override
    public int compare(Employee o1, Employee o2) {
        return Comparator.comparingInt(Employee::getAge)
            .thenComparingLong(Employee::getSalary)
            .compare(o1, o2);
    }
  }
}
