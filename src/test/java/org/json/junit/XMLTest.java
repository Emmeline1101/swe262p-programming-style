package org.json.junit;

/*
Public Domain.
*/

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.json.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;


/**
 * Tests for JSON-Java XML.java
 * Note: noSpace() will be tested by JSONMLTest
 */
public class XMLTest {
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
                // 如果返回的不是 JSONObject，则直接打印结果
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
//                // 如果返回的不是 JSONObject，则直接打印结果
                System.out.println("Test2 for Task1:" + result);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

//Task2

        try {
            JSONObject replacement =XML.toJSONObject("<street>Ave of the Arts</street>\n");
            System.out.println("Given replacement: " + replacement);
            JSONObject jobj = XML.toJSONObject(new StringReader(xmlString2), new JSONPointer("/contact/address/street/"), replacement);
            System.out.println("Test2 for Task2:" + jobj);
        } catch (org.json.JSONException e) {
            System.out.println("JSON Exception: " + e.getMessage());
        }
        System.out.println("-----------------------");
    }

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
            JSONObject replacement =XML.toJSONObject("<course>Replace Course</course>\n");
            System.out.println("Given replacement: " + replacement);
            JSONObject jobj = XML.toJSONObject(new StringReader(xmlString2), new JSONPointer("/student/courses/course/"), replacement);
            System.out.println("Test3 for Task2:" + jobj);
        } catch (org.json.JSONException e) {
            System.out.println("JSON Exception: " + e.getMessage());
        }
        System.out.println("-----------------------");
    }


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
            JSONObject replacement =XML.toJSONObject("<storage>128 GB</storage>\n");
            System.out.println("Given replacement: " + replacement);
            JSONObject jobj = XML.toJSONObject(new StringReader(xmlString2), new JSONPointer("/product/specifications/storage/"), replacement);
            System.out.println("Test4 for Task2:" + jobj);
        } catch (org.json.JSONException e) {
            System.out.println("JSON Exception: " + e.getMessage());
        }
        System.out.println("-----------------------");
    }
}



