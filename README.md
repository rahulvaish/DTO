# DTO

```
public static void main(String[] args) {
        try {
            // Using File
            File file = new File("src/main/resources/sample-message.xml");
            XmlMapper xmlMapper = new XmlMapper();
            Person personFromFile = xmlMapper.readValue(file, Person.class);
            System.out.println("Read from file: " + personFromFile);

            // Using InputStream
            try (InputStream inputStream = Files.newInputStream(Paths.get("src/main/resources/sample-message.xml"))) {
                Person personFromInputStream = xmlMapper.readValue(inputStream, Person.class);
                System.out.println("Read from InputStream: " + personFromInputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


```

```
package com.example;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class MyClass {

    public static void main(String[] args) {
        try {
            File file = getFileFromResources("sample-message.xml");
            System.out.println("File path: " + file.getAbsolutePath());
            // Now you can use this file object as needed
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static File getFileFromResources(String fileName) throws IOException, URISyntaxException {
        ClassLoader classLoader = MyClass.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IOException("File not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }
    }
}

```


```
mvn install:install-file -Dfile=path/to/your.jar -DgroupId=com.example -DartifactId=your-artifact-id -Dversion=1.0 -Dpackaging=jar

```


```
package com.example.ObjToXml;

import com.example.generated.Employee;
import com.example.generated.Person;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class AppController {

    @GetMapping("/transformPersonToEmployee")
    public String transformPersonToEmployee() {
        try {
            // Create an XmlMapper instance
            XmlMapper xmlMapper = new XmlMapper();

            // Read and deserialize the person.xml file into a Person object
            File personFile = new File("src/main/resources/source/person.xml");
            Person person = xmlMapper.readValue(personFile, Person.class);

            // Transform data from Person to Employee
            Employee employee = transformPersonToEmployee(person);

            // Serialize the Employee object to XML and save to a file
            File employeeFile = new File("src/main/resources/destination/employee.xml");
            xmlMapper.writeValue(employeeFile, employee);

            // Serialize the Employee object to XML and print to console
            String xmlString = xmlMapper.writeValueAsString(employee);
            System.out.print(xmlString);

            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return "FAILURE";
        }
    }

    private Employee transformPersonToEmployee(Person person) {
        Employee employee = new Employee();
        employee.setFullName(person.getName());
        employee.setYears(person.getAge());
        employee.setContact(person.getEmail());
        return employee;
    }
}


```
