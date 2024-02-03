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

## Test Result

![mvn test result](https://github.com/Emmeline1101/swe262p-programming-style/blob/master/images/m2_testresult.png)
![XML test result](https://github.com/Emmeline1101/swe262p-programming-style/blob/master/images/m2result2.png)

### Code for Test1
```

    @Test
    public void M2Test1() { //for m1Task2 m2Task1
        final String xmlString1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Root>\n" +
                "    <Person>\n" +
                "        <Name>John Doe</Name>\n" +
                "        <Age>30</Age>\n" +
                "        <Address>\n" +
                "            <City>New York</City>\n" +
                "            <State>NY</State>\n" +
                "        </Address>\n" +
                "        <Hobbies>\n" +
                "            <Hobby>\n" +
                "                <Type>\n" +
                "                    <City>New York</City>\n" +
                "                    <State>NY</State>\n" +
                "                </Type>\n" +
                "            </Hobby>\n" +
                "            <Hobby>\n" +
                "                <Type1>Traveling</Type1>\n" +
                "            </Hobby>\n" +
                "        </Hobbies>\n" +
                "    </Person>\n" +
                "</Root>";
        try {
            Object result = XML.toJSONObject(new StringReader(xmlString1), new org.json.JSONPointer("/Root/Person/Hobbies/Hobby/1/Type1/"));
            if (result instanceof org.json.JSONObject) {
                org.json.JSONObject jobj = (org.json.JSONObject) result;
                System.out.println("Test1 for Task1:" + jobj);
            } else {
                System.out.println("Test1 for Task1:" + result);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //Task2
        try {
            org.json.JSONObject replacement = XML.toJSONObject("<City>Boston</City>\n");
            System.out.println("Given replacement: " + replacement);
            org.json.JSONObject jobj = XML.toJSONObject(new StringReader(xmlString1), new org.json.JSONPointer("/Root/Person/Hobbies/Hobby/1"), replacement);
            System.out.println("Test1 for Task2:" + jobj);
        } catch (org.json.JSONException e) {
            System.out.println("JSON Exception: " + e.getMessage());
        }


        System.out.println("-----------------------");
    }
```

### Test2
```

    @Test
    public void M2Test2() {
        String xmlString2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<contact>\n" +
                "  <nick>Crista </nick>\n" +
                "  <name>Crista Lopes</name>\n" +
                "  <address>\n" +
                "    <street>Ave of Nowhere</street>\n" +
                "    <zipcode>92614</zipcode>\n" +
                "  </address>\n" +
                "</contact>";

        try {
            JSONObject result = XML.toJSONObject(new StringReader(xmlString2), new JSONPointer("/contact/address/street/"));
//            System.out.println("Test2 for Task1:" +result);
            if (result instanceof JSONObject) {
                JSONObject jobj = (JSONObject) result;
                System.out.println("Test2 for Task1:" + jobj);
            } else {
                System.out.println("Test2 for Task1:" + result);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

//Task2

        try {
            JSONObject replacement = XML.toJSONObject("<street>Ave of the Arts</street>\n");
            System.out.println("Given replacement: " + replacement);
            JSONObject jobj = XML.toJSONObject(new StringReader(xmlString2), new JSONPointer("/contact/address/street/"), replacement);
            System.out.println("Test2 for Task2:" + jobj);
        } catch (org.json.JSONException e) {
            System.out.println("JSON Exception: " + e.getMessage());
        }
        System.out.println("-----------------------");
    }
```

### Test3
```

    @Test
    public void M2Test3() {
        String xmlString2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<student>\n" +
                "  <id>12345</id>\n" +
                "  <name>John Smith</name>\n" +
                "  <age>20</age>\n" +
                "  <courses>\n" +
                "    <course>Math</course>\n" +
                "    <course>Science</course>\n" +
                "    <course>History</course>\n" +
                "  </courses>\n" +
                "</student>";


        try {
            JSONObject result = XML.toJSONObject(new StringReader(xmlString2), new JSONPointer("/student/courses/course/"));
//            System.out.println("Test2 for Task1:" +result);
            if (result instanceof JSONObject) {
                JSONObject jobj = (JSONObject) result;
                System.out.println("Test3 for Task1:" + jobj);
            } else {
//                // 如果返回的不是 JSONObject，则直接打印结果
                System.out.println("Test3 for Task1:" + result);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

//Task2

        try {
            JSONObject replacement = XML.toJSONObject("<course>Replace Course</course>\n");
            System.out.println("Given replacement: " + replacement);
            JSONObject jobj = XML.toJSONObject(new StringReader(xmlString2), new JSONPointer("/student/courses/course/"), replacement);
            System.out.println("Test3 for Task2:" + jobj);
        } catch (org.json.JSONException e) {
            System.out.println("JSON Exception: " + e.getMessage());
        }
        System.out.println("-----------------------");
    }


```

### Test4
```

    @Test
    public void M2Test4() {
        String xmlString2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<product>\n" +
                "  <id>789</id>\n" +
                "  <name>Smartphone</name>\n" +
                "  <brand>ABC Inc.</brand>\n" +
                "  <price>499.99</price>\n" +
                "  <specifications>\n" +
                "    <screen>6.2 inches</screen>\n" +
                "    <camera>12 MP</camera>\n" +
                "    <storage>64 GB</storage>\n" +
                "  </specifications>\n" +
                "</product>";


        try {
            JSONObject result = XML.toJSONObject(new StringReader(xmlString2), new JSONPointer("/product/specifications/"));
//            System.out.println("Test2 for Task1:" +result);
            if (result instanceof JSONObject) {
                JSONObject jobj = (JSONObject) result;
                System.out.println("Test4 for Task1:" + jobj);
            } else {
//                // 如果返回的不是 JSONObject，则直接打印结果
                System.out.println("Test4 for Task1:" + result);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

//Task2

        try {
            JSONObject replacement = XML.toJSONObject("<storage>128 GB</storage>\n");
            System.out.println("Given replacement: " + replacement);
            JSONObject jobj = XML.toJSONObject(new StringReader(xmlString2), new JSONPointer("/product/specifications/storage/"), replacement);
            System.out.println("Test4 for Task2:" + jobj);
        } catch (org.json.JSONException e) {
            System.out.println("JSON Exception: " + e.getMessage());
        }
        System.out.println("-----------------------");
    }
}
```

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
