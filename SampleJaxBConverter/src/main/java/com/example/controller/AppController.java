//package com.example.controller;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.Marshaller;
//import javax.xml.bind.Unmarshaller;
//import java.io.File;
//import javax.xml.bind.JAXBException;
//
//@RestController
//public class AppController {
//
//
//
//
//    @GetMapping("/getValue")
//    public String getValue() throws JAXBException{
//        // Unmarshal the original XML
//        JAXBContext originalContext = JAXBContext.newInstance(Person.class);
//        Unmarshaller unmarshaller = originalContext.createUnmarshaller();
//        Person originalData = (Person) unmarshaller.unmarshal(new File("src/main/resources/source/person.xml"));
//
//        // Transform data to the new format
//        Employee newData = transformData(originalData);
//
//        // Marshal the new XML
//        JAXBContext newContext = JAXBContext.newInstance(Employee.class);
//        Marshaller marshaller = newContext.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        marshaller.marshal(newData, new File("src/main/resources/destination/employee.xml"));
//        return "SUCCESS";
//    }
//
//    private static generated.Employee transformData(Person person) {
//        Employee newData = new Employee();
//        newData.setFullName(person.getName());
//        newData.setYears(person.getAge());
//        newData.setContact(person.getEmail());
//        return newData;
//    }
//
//}
