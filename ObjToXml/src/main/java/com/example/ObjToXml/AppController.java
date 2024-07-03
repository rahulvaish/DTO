package com.example.ObjToXml;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.StringWriter;

@RestController
public class AppController {

    @GetMapping("/generateEmployeeXml")
    public String generateEmployeeXml() {
        try {
            // Create an Employee object and set values
            Employee employee = new Employee();
            employee.setFullName("John Doe");
            employee.setYears(30);
            employee.setContact("john.doe@example.com");

            // Create JAXB context and marshaller
            JAXBContext context = JAXBContext.newInstance(Employee.class);
            Marshaller marshaller = context.createMarshaller();

            // To format the generated XML
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Marshal the Employee object to a file
            File file = new File("src/main/resources/destination/employee.xml");
            marshaller.marshal(employee, file);


            // Marshal the Employee object to a StringWriter
            StringWriter sw = new StringWriter();
            marshaller.marshal(employee, sw);
            String xmlString = sw.toString();
            System.out.print(xmlString);


            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return "FAILURE";
        }
    }
}
