# SWE262P Programming Style Course
## Running Environment
1. Install Java Development Kit (JDK).

2. Install Apache Maven: Download and install Maven from the official website.

3. Clone the project to your local machine
4. run "mvn clean""mvn compile""mvn test"

# M5
## Purpose:
The test checks if converting XML to JSON asynchronously works correctly. It ensures the output matches expected results when given specific XML input.
## Process:
The process involves preparing XML strings, setting up success and failure callbacks, and calling a custom method to convert XML to JSON asynchronously. The test waits for the operation to complete and then checks if the conversion outcome is as expected.
## Performance: 
Using asynchronous programming makes the conversion process more efficient, allowing other tasks to run simultaneously. This method improves the application's overall responsiveness and capacity to handle operations without blocking.
## Test：
``` java
 @Test
    public void shouldReturnAsyncObject() {

        // Input XML string
        String inputXML = "<?xml version=\"1.0\"?>\n" +
                "<library>\n" +
                "    <book id=\"bk001\">\n" +
                "        <author>Smith, John</author>\n" +
                "        <title>Java Fundamentals</title>\n" +
                "    </book>\n" +
                "    <book id=\"bk002\">\n" +
                "        <author>Doe, Jane</author>\n" +
                "        <title>Understanding Algorithms</title>\n" +
                "    </book>\n" +
                "</library>";

        // Expected XML string to match against the result
        String expectedXML = "<?xml version=\"1.0\"?>\n" +
                "<library>\n" +
                "    <book id=\"bk001\">\n" +
                "        <author>Smith, John</author>\n" +
                "        <title>Java Fundamentals</title>\n" +
                "    </book>\n" +
                "    <book id=\"bk002\">\n" +
                "        <author>Doe, Jane</author>\n" +
                "        <title>Understanding Algorithms</title>\n" +
                "    </book>\n" +
                "</library>";

        // Prepare success and failure callback functions
        Consumer<JSONObject> onSuccess = (json) -> System.out.println("Success: " + json.toString());
        Consumer<Exception> onFailure = Throwable::printStackTrace;

        // Call the asynchronous toJSONObject method
        CompletableFuture<JSONObject> asyncResult = XML.toJSONObject(new StringReader(inputXML), onSuccess, onFailure);
        JSONObject expectedJSONObject = XML.toJSONObject(expectedXML);

        JSONObject actualJSONObject = null;
        try {
            // Wait for the asynchronous processing result
            actualJSONObject = asyncResult.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Assert that the actual JSON object is not null and equals the expected JSON object
        assertNotNull(actualJSONObject);
        assertEquals(expectedJSONObject.toString(), actualJSONObject.toString());
    }

```
### Test Result
![M5 unit test result](https://github.com/Emmeline1101/swe262p-programming-style/blob/173982d64068d1990e9261560edce130c502a2da/images/M5%20Unit%20Test.png)
![M5 mvn test result](https://github.com/Emmeline1101/swe262p-programming-style/blob/173982d64068d1990e9261560edce130c502a2da/images/M5%20mvn%20Test.png)
# M4

## Purpose:
Create a method to convert JSON objects to streams. And the streams can be utilized to process the data.
## Process:
1. Create the `JSONSpliterator` class as an implementation of the `Spliterator<JSONObject>` interface.
2. Implement `tryAdvance` method to iterate over keys and values, creating new JSON object and handling nested objects and arrays
3. Implement `toJSONObjectStream` method to convert the JSON object into a Stream of JSON objects. The `spliterator()` method plays a key role in this process by providing a customized `JSONSpliterator` for the current JSON object, making the conversion into a Stream of JSONObjects possible.
## Test
### Test Result
![M4 mvn test result](https://github.com/Emmeline1101/swe262p-programming-style/blob/0692aed5827b19d84ed284fcc988c56c2b9342cf/images/M4Test.png)
### Test1
```java
    @Test
    public void testStreamExtractTitles() { // extract all the String value of the "title"
        JSONObject obj = XML.toJSONObject("<Books><book><title>AAA</title><author>ASmith</author></book><book><title>BBB</title><author>BSmith</author></book></Books>");
        List<String> titles = obj.toJSONObjectStream()
                .filter(node -> node.has("title")) //filter the node
                .map(node -> node.getString("title"))// get the key/value of the title
                .collect(Collectors.toList()); //collect them in the list
        assertTrue(titles.containsAll(Arrays.asList("AAA", "BBB")));
    }
```

### Test2
```java
 @Test
    @Test
    public void testStreamFilterAndTransform() {//filter & proccess with the price
        JSONObject obj = XML.toJSONObject("<Books><book><title>AAA</title><price>10</price></book><book><title>BBB</title><price>15</price></book></Books>");
        List<String> discountedPrices = obj.toJSONObjectStream()
                .filter(node -> node.has("price"))
                .map(node -> {
                    double price = node.getDouble("price");
                    double discountPrice = price * 0.9; // discount
                    return String.format("%.2f", discountPrice);
                })
                .collect(Collectors.toList());
        assertEquals(Arrays.asList("9.00", "13.50"), discountedPrices); // expectation
    }
```

### Test3
```java
    @Test
    public void testStreamTransformBasedOnPath() { //filter title
        JSONObject obj = XML.toJSONObject("<Books><book><title>AAA</title><author>ASmith</author></book><book><title>BBB</title><author>BSmith</author></book></Books>");
        List<String> transformedTitles = obj.toJSONObjectStream()
                .filter(node -> node.has("title"))
                .map(node -> {
                    String title = node.getString("title");
                    return title.toUpperCase();
                })
                .collect(Collectors.toList());
        assertTrue(transformedTitles.containsAll(Arrays.asList("AAA", "BBB")));
    }
```

# M3
## Purpose: 
Convert data from XML data to JSON objects, and add the prefix to the key in the process of converting by using the 'Function<String, String> keyTransformer' interface.
## Process:
1. Initialize parsing with XMLTokener.
2. Use parse to recursively traverse XML, and when finding the key of the XML object, add the prefix to it.
3. Convert XML to JSON.

## Performance
Compared to M1 task4, M3 directly modifies the JSON library,which reduces the calling time. Besides this, M3 adds the prefix during the process of converting XML to JSON rather than adding the prefix after the converting process
## Test
### Test Result
![M3 mvn test result](https://github.com/Emmeline1101/swe262p-programming-style/blob/b96bb8b67df26cc1c2d7ec85e3a2c545fa88445d/images/M3%20mvn%20test.png)
![M3 XML test result](https://github.com/Emmeline1101/swe262p-programming-style/blob/b96bb8b67df26cc1c2d7ec85e3a2c545fa88445d/images/M3%20JUnit.png)
### Test1 (Add Prefix)
```java
@Test
    public void M3Test1() {
        // xml string
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
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
                "</product>\n";
        // create a reader
        StringReader reader = new StringReader(xmlString);
        //define a keyTransformer
        Function<String, String> keyTransformer = key -> "swe262_" + key;
        //call the method
        try {
            JSONObject json = XML.toJSONObject(reader, keyTransformer);
            System.out.println("Test1 for M3" + json.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
```
### Test2 (Add Prefix)
```java
public void M3Test2() {
        // xml string
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<order id=\"12345\" date=\"2024-02-13\">\n" +
                "  <customer>\n" +
                "    <firstName>John</firstName>\n" +
                "    <lastName>Doe</lastName>\n" +
                "  </customer>\n" +
                "  <items>\n" +
                "    <item id=\"1\">\n" +
                "      <name>Laptop</name>\n" +
                "      <quantity>1</quantity>\n" +
                "      <price>1200.00</price>\n" +
                "    </item>\n" +
                "    <item id=\"2\">\n" +
                "      <name>Mouse</name>\n" +
                "      <quantity>2</quantity>\n" +
                "      <price>25.00</price>\n" +
                "    </item>\n" +
                "  </items>\n" +
                "</order>\n";
        // create a reader
        StringReader reader = new StringReader(xmlString);
        //define a keyTransformer
        Function<String, String> keyTransformer = key -> "swe262_" + key;
        //call the method
        try {
            JSONObject json = XML.toJSONObject(reader, keyTransformer);
            System.out.println("Test2 for M3" + json.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
```
### Test3 (Reverse)
```java
public void M3Test3() {
        // xml string
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<bookstore xmlns:bk=\"http://www.example.com/books\">\n" +
                "  <book id=\"book1\">\n" +
                "    <bk:title>The Great Gatsby</bk:title>\n" +
                "    <bk:author>F. Scott Fitzgerald</bk:author>\n" +
                "    <bk:year>1925</bk:year>\n" +
                "  </book>\n" +
                "  <book id=\"book2\">\n" +
                "    <bk:title>To Kill a Mockingbird</bk:title>\n" +
                "    <bk:author>Harper Lee</bk:author>\n" +
                "    <bk:year>1960</bk:year>\n" +
                "  </book>\n" +
                "</bookstore>\n";
        // create a reader
        StringReader reader = new StringReader(xmlString);
        //define a keyTransformer
        Function<String, String> keyTransformer = key -> new StringBuilder(key).reverse().toString();;
        //call the method
        try {
            JSONObject json = XML.toJSONObject(reader, keyTransformer);
            System.out.println("Test3 for M3" + json.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

```
### Test4 (Reverse)
```java
@Test
    public void M3Test4() {
        // xml string
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<survey>\n" +
                "  <question type=\"multiple-choice\">\n" +
                "    <text>What is your favorite color?</text>\n" +
                "    <options>\n" +
                "      <option>Red</option>\n" +
                "      <option>Green</option>\n" +
                "      <option>Blue</option>\n" +
                "      <option>Yellow</option>\n" +
                "    </options>\n" +
                "  </question>\n" +
                "  <question type=\"text\">\n" +
                "    <text>Please describe your ideal vacation destination.</text>\n" +
                "  </question>\n" +
                "</survey>\n";
        // create a reader
        StringReader reader = new StringReader(xmlString);
        //define a keyTransformer
        Function<String, String> keyTransformer = key -> new StringBuilder(key).reverse().toString();;
        //call the method
        try {
            JSONObject json = XML.toJSONObject(reader, keyTransformer);
            System.out.println("Test4 for M3" + json.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
```

# M2

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

![M2 mvn test result](https://github.com/Emmeline1101/swe262p-programming-style/blob/master/images/m2_testresult.png)
![M2 XML test result](https://github.com/Emmeline1101/swe262p-programming-style/blob/master/images/m2result2.png)

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
