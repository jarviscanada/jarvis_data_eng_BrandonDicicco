package ca.jrvs.practice.dataStructure.list;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Employee implements Comparable<Employee> {

  public static void main(String[] args) {
    Map<Employee, List<String>> empStrMap = new HashMap<>();

    Employee amy = new Employee(1, "Amy", 25, 45000);
    List<String> amyPreviousCompanies = Arrays.asList("TD", "RBC", "CIBC");
    empStrMap.put(amy, amyPreviousCompanies);

    Employee bob = new Employee(2, "Bob", 25, 40000);
    List<String> bobPreviousCompanies = Arrays.asList("A&W", "Superstore");
    empStrMap.put(bob, bobPreviousCompanies);

    System.out.println("Bob hashcode: " + bob.hashCode());
    System.out.println("Bob value: " + empStrMap.get(bob).toString());
    System.out.println("Amy hashcode: " + amy.hashCode());
    System.out.println("Amy value: " + empStrMap.get(amy).toString());
  }

  private int id;
  private String name;
  private int age;
  private long salary;

  public Employee() {
  }

  public Employee(int id, String name, int age, long salary) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.salary = salary;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public long getSalary() {
    return salary;
  }

  public void setSalary(long salary) {
    this.salary = salary;
  }

  @Override
  public int compareTo(Employee o) {
    if (this.age > o.getAge())
      return 1;
    else if (this.age < o.getAge())
      return -1;
    else
      return Long.compare(this.salary, o.getSalary());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Employee employee = (Employee) o;
    return id == employee.id && age == employee.age && salary == employee.salary && Objects
        .equals(name, employee.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, age, salary);
  }
}
