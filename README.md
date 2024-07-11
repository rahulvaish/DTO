# DTO

```
import com.opencsv.CSVWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

    public class XmlToCsvConverter {

        public static Employees unmarshal(File file) throws JAXBException {
            JAXBContext context = JAXBContext.newInstance(Employees.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Employees) unmarshaller.unmarshal(file);
        }

        public static void writeCsv(Employees employees, String csvFilePath) throws IOException {
            try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
                String[] header = {"ID", "Name", "Role"};
                writer.writeNext(header);

                List<Employee> employeeList = employees.getEmployees();
                for (Employee employee : employeeList) {
                    String[] data = {String.valueOf(employee.getId()), employee.getName(), employee.getRole()};
                    writer.writeNext(data);
                }
            }
        }

        public static void main(String[] args) {
            try {
                File xmlFile = new File("src/main/resources/employees.xml");
                Employees employees = unmarshal(xmlFile);
                writeCsv(employees, "src/main/resources/employees.csv");
            } catch (JAXBException | IOException e) {
                e.printStackTrace();
            }
        }
    }



```


```


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(propOrder = {"id", "name", "role"})
public class Employee {
    private int id;
    private String name;
    private String role;

    public int getId() {
        return id;
    }

    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    @XmlElement
    public void setRole(String role) {
        this.role = role;
    }
}

```


```
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement(name = "employees")
public class Employees {
    private List<Employee> employees;

    @XmlElement(name = "employee")
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
```


```
<employees>
    <employee>
        <id>1</id>
        <name>John Doe</name>
        <role>Developer</role>
    </employee>
    <employee>
        <id>2</id>
        <name>Jane Smith</name>
        <role>Manager</role>
    </employee>
</employees>

```


```
<dependencies>
        <dependency>
            <groupId>javax.xml.bind</groupId>
            <artifactId>jaxb-api</artifactId>
            <version>2.3.1</version>
        </dependency>
        <dependency>
            <groupId>org.glassfish.jaxb</groupId>
            <artifactId>jaxb-runtime</artifactId>
            <version>2.3.2</version>
        </dependency>
        <dependency>
            <groupId>com.opencsv</groupId>
            <artifactId>opencsv</artifactId>
            <version>5.4</version>
        </dependency>
    </dependencies>

```
