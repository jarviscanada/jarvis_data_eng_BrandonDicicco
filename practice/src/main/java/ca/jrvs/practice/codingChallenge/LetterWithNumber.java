package ca.jrvs.practice.codingChallenge;

public class LetterWithNumber {

  public static void main(String[] args) {
    LetterWithNumber letterWithNumber = new LetterWithNumber();
    System.out.println(letterWithNumber.printLetterAndNumber("abcABC"));
  }
  public String printLetterAndNumber(String input) {
    StringBuilder builder = new StringBuilder();
    char[] characters = input.toCharArray();

    for (char character : characters) {
      builder.append(character);
      if (Character.isLowerCase(character)) {
        builder.append((int) character - 96);
      } else {
        builder.append((int) character - 38);
      }
    }

    return builder.toString();
  }
}
