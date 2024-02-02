# SWE262P Programming Style Course
## Running Environment
1. Install Java Development Kit (JDK).

2. Install Apache Maven: Download and install Maven from the official website.

3. Clone the project to your local machine
4. run "mvn clean""mvn compile""mvn test"

## Task1

### Purpose: 
Extract and convert data from an XML document corresponding to a specified JSONPointer path into a JSON object.
### Process:
1. Initialize parsing with XMLTokener.
2. Use parseSub to recursively traverse XML within the JSON pointer path adn skip others, building a JSON representation.
3. When parseSub finds a path match with JSONPointer, it extracts that portion as a JSON object and returns it.



## Task2

### Purpose: 
Similar to Task 1, but it additionally replaces the data at the specified JSONPointer path with a provided JSON object during the XML-to-JSON conversion.
### Process: 
1. Start with XML parsing using XMLTokener.
2. parseSub is tasked with both building the JSON object and replacing the matched path segment with the replacement JSON object.
3. Continue parsing the document after replacement to ensure complete conversion.

## Performance

### Task1:
Compare to milestone 1 task 2, task1 only needs to convert the xml within the JSON pointer path to a JSON object, without transforming the entire content.

### Task2:
Compare to milestone 1 task 5, task2 replaces the given JSON object while converting the XML.
![mvn test result](https://github.com/Emmeline1101/swe262p-programming-style/blob/master/images/m2_testresult.png)
![XML test result](https://github.com/Emmeline1101/swe262p-programming-style/blob/master/images/m2result2.png)

=============================================================================================

![Json-Java logo](https://github.com/stleary/JSON-java/blob/master/images/JsonJava.png?raw=true)

<sub><sup>image credit: Ismael Pérez Ortiz</sup></sub>


JSON in Java [package org.json]
===============================

[![Maven Central](https://img.shields.io/maven-central/v/org.json/json.svg)](https://mvnrepository.com/artifact/org.json/json)
[![Java CI with Maven](https://github.com/stleary/JSON-java/actions/workflows/pipeline.yml/badge.svg)](https://github.com/stleary/JSON-java/actions/workflows/pipeline.yml)
[![CodeQL](https://github.com/stleary/JSON-java/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/stleary/JSON-java/actions/workflows/codeql-analysis.yml)

**[Click here if you just want the latest release jar file.](https://search.maven.org/remotecontent?filepath=org/json/json/20231013/json-20231013.jar)**


# Overview

[JSON](http://www.JSON.org/) is a light-weight language-independent data interchange format.

The JSON-Java package is a reference implementation that demonstrates how to parse JSON documents into Java objects and how to generate new JSON documents from the Java classes.

Project goals include:
* Reliable and consistent results
* Adherence to the JSON specification 
* Easy to build, use, and include in other projects
* No external dependencies
* Fast execution and low memory footprint
* Maintain backward compatibility
* Designed and tested to use on Java versions 1.8 - 21


The files in this package implement JSON encoders and decoders. The package can also convert between JSON and XML, HTTP headers, Cookies, and CDL.

# If you would like to contribute to this project

For more information on contributions, please see [CONTRIBUTING.md](https://github.com/stleary/JSON-java/blob/master/docs/CONTRIBUTING.md)

Bug fixes, code improvements, and unit test coverage changes are welcome! Because this project is currently in the maintenance phase, the kinds of changes that can be accepted are limited. For more information, please read the [FAQ](https://github.com/stleary/JSON-java/wiki/FAQ).

# Build Instructions

The org.json package can be built from the command line, Maven, and Gradle. The unit tests can be executed from Maven, Gradle, or individually in an IDE e.g. Eclipse.
 
**Building from the command line**

*Build the class files from the package root directory src/main/java*
```shell
javac org/json/*.java
```

*Create the jar file in the current directory*
```shell
jar cf json-java.jar org/json/*.class
```

*Compile a program that uses the jar (see example code below)*
```shell
javac -cp .;json-java.jar Test.java (Windows)
javac -cp .:json-java.jar Test.java (Unix Systems)
```

*Test file contents*

```java
import org.json.JSONObject;
public class Test {
    public static void main(String args[]){
       JSONObject jo = new JSONObject("{ \"abc\" : \"def\" }");
       System.out.println(jo);
    }
}
```

*Execute the Test file*
```shell 
java -cp .;json-java.jar Test (Windows)
java -cp .:json-java.jar Test (Unix Systems)
```

*Expected output*

```json
{"abc":"def"}
```

 
**Tools to build the package and execute the unit tests**

Execute the test suite with Maven:
```shell
mvn clean test
```

Execute the test suite with Gradlew:

```shell
gradlew clean build test
```

# Notes

For more information, please see [NOTES.md](https://github.com/stleary/JSON-java/blob/master/docs/NOTES.md)

# Files

For more information on files, please see [FILES.md](https://github.com/stleary/JSON-java/blob/master/docs/FILES.md)

# Release history:

For the release history, please see [RELEASES.md](https://github.com/stleary/JSON-java/blob/master/docs/RELEASES.md)
