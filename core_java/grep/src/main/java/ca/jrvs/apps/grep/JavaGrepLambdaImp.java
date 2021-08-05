package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JavaGrepLambdaImp extends JavaGrepImp {

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }

    JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
    javaGrepLambdaImp.setRegex(args[0]);
    javaGrepLambdaImp.setRootPath(args[1]);
    javaGrepLambdaImp.setOutFile(args[2]);

    try {
      javaGrepLambdaImp.process();
    } catch (Exception ex) {
      javaGrepLambdaImp.logger.error("Error when conducting process flow", ex);
    }
  }

  @Override
  public List<String> readLines(File inputFile) {
    List<String> lines = new ArrayList<>();

    try {
      lines = Files.lines(inputFile.toPath())
          .collect(Collectors.toList());
    } catch (IOException ex) {
      logger.error("Error when reading lines from file", ex);
    }

    return lines;
  }

  @Override
  public List<File> listFiles(String rootDir) {
    List<File> files = new ArrayList<>();

    try {
      // Walk the directory tree starting at rootDir, get only regular files (no directories)
      // and add to file list
      files = Files.walk(Paths.get(rootDir))
          .filter(Files::isRegularFile)
          .map(Path::toFile)
          .collect(Collectors.toList());
    } catch (IOException ex) {
      logger.error("Error when traversing the files list", ex);
    }

    return files;
  }

}