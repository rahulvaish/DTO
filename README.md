# DTO
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
