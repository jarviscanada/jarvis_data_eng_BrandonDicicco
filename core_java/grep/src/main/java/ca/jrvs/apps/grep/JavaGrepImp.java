package ca.jrvs.apps.grep;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.IO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.Writer;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.BasicConfigurator;

public class JavaGrepImp implements JavaGrep {


  final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

  private String regex;
  private String rootPath;
  private String outFile;

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }

    // Default logger config
    BasicConfigurator.configure();

    JavaGrepImp javaGrepImp = new JavaGrepImp();

    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
      javaGrepImp.process();
    } catch (Exception ex) {
      javaGrepImp.logger.error(ex.getMessage(), ex);
    }

  }

  @Override
  public void process() throws IOException {
    List<String> matchedLines = new ArrayList<>();

    for (File curFile : listFiles(getRootPath())) {
      for (String curLine : readLines(curFile)) {
        if (containsPattern(curLine)) {
          matchedLines.add(curLine);
        }
      }
    }

    writeToFile(matchedLines);
  }

  @Override
  public List<File> listFiles(String rootDir) {
    List<File> files = new ArrayList<>();

    File rootDirectory = new File(rootDir);
    // All files and directories in the given directory
    File[] allFiles = rootDirectory.listFiles();

    // Go through all files in current directory
    if (allFiles != null) {
      for (File curFile : allFiles) {
        // Add file to list if it is a file
        if (curFile.isFile()) {
          files.add(curFile);
          // If it is a directory, must go through all files/directories in it.
        } else if (curFile.isDirectory()) {
          files.addAll(listFiles(curFile.getAbsolutePath()));
        }
      }
    }

    return files;
  }

  @Override
  public List<String> readLines(File inputFile) {
    List<String> lines = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
      String curLine = "";

      while ((curLine = reader.readLine()) != null) {
        lines.add(curLine);
      }

    } catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
    }

    return lines;
  }

  @Override
  public boolean containsPattern(String line) {
    return line.matches(getRegex());
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    try (Writer writer = new BufferedWriter(new FileWriter(getOutFile()))) {

      for (String curLine: lines) {
        writer.write(curLine + System.lineSeparator()); }

    } catch (IOException ex) {
      logger.error(ex.getMessage(), ex);
    }
  }

  @Override
  public String getRootPath() {
    return this.rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getRegex() {
    return this.regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getOutFile() {
    return this.outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }
}
