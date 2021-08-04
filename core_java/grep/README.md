
# Introduction
The purpose of this project is to simulate similar behaviour to the Linux feature `grep` using Java 8. 
The program is implemented in two ways: using loops, and using streams with lambdas. The program takes a
regular expression pattern, and iterates through the files in a root directory and all subdirectories, and produces
an output file that contains the matched lines. The program was created in IntelliJ, built with Maven, and
packaged into a Docker image.

# Quick Start
```
# Package with Maven
mvn clean compile package

# Execute JAR locally
java -cp path/to/grep-1.0-SNAPSHOT.jar [ca.jrvs.apps.grep.JavaGrepImp | ca.jrvs.apps.grep.JavaGrepLambdaImp] <regex> <rootDirectory> <outputFile>

# Pull image from DockerHub
docker pull bdicicco/grep

# Execute Docker container
docker run --rm -v `pwd`/data:/data -v `pwd`/log:/log bdicicco/grep .*Romeo.*Juliet.* /data /log/grep.out
```

#Implementation
The application was implemented in two ways: using loops and using streams. The core algorithm for both
implementations is the same, and is detailed below.
## Pseudocode
```
process() {
stringsMatchedLinesList = []

for each file in listFilesRecursively(rootDir)
  for each line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)

}
```

## Performance Issues
In worse case scenarios, this program will fail with an OutOfMemory error. This is due to the heap memory allocated
by the JVM being insufficient. These scenarios occur due to the reading of large amounts of data. This can be fixed
by implementing buffers to limit memory usage, or through the use of streams which more efficiently
process large amounts of data.

# Testing
Testing was done manually using IntelliJ debugger and expected results. Files with test data and known matched output were used 
to ensure the application was successfully returning the expected output. Any errors were logged using the log4j logger.

# Deployment
For easier distribution, the application was packaged into a Docker image named bdicicco/grep. This image
is stored on Docker Hub remotely in a public repository. To run the image, the following commands can be used:
```
# Pull image from DockerHub
docker pull bdicicco/grep

# Execute Docker container
docker run --rm -v `pwd`/data:/data -v `pwd`/log:/log bdicicco/grep .*Romeo.*Juliet.* /data /log/grep.out
```

# Improvements
1. Expand on the output provided rather than just matched lines. For example, add file and line number to be able to trace
   back where the matched line came from.
2. Add various options similar to `grep` such as only count the output lines, ignoring character case, etc.
3. Automatically open the output file to be able to see the results without having to go to it.