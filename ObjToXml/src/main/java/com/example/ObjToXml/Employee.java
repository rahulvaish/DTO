package com.example.ObjToXml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "employee")
public class Employee {

    private String fullName;
    private int years;
    private String contact;

    @XmlElement
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @XmlElement
    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    @XmlElement
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}

