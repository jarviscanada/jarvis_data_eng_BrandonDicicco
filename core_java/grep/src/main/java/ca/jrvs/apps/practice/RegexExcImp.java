package ca.jrvs.apps.practice;

public class RegexExcImp implements RegexExc {

  @Override
  public boolean matchJpeg(String filename) {
    String pattern = "^.+\\.(jpe?g)$";
    return filename.matches(pattern);
  }

  @Override
  public boolean matchIp(String ip) {
    String pattern = "^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$";
    return ip.matches(pattern);
  }

  @Override
  public boolean isEmptyLine(String line) {
    String pattern = "^\\s*$";
    return line.matches(pattern);
  }
}
