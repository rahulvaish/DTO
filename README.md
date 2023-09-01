# DTO
```
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.text.SimpleDateFormat;

class Person {
    @SerializedName("name")
    private String name;

    @SerializedName("date")
    private Date date;

    @SerializedName("ludt")
    private Date ludt;

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getLudt() {
        return ludt;
    }

    public void setLudt(Date ludt) {
        this.ludt = ludt;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        return "Person{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", ludt='" + sdf.format(ludt) + '\'' +
                '}';
    }
}

public class Main {
    public static void main(String[] args) {
        String json = "[{\"name\":\"Rahul\",\"date\":\"2021-01-01\",\"ludt\":\"2021-01-01T23:11:12.779374\"}]";

        Gson gson = new Gson();
        Person[] persons = gson.fromJson(json, Person[].class);

        for (Person person : persons) {
            System.out.println(person);
        }
    }
}


```


```
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

class Item {
    @SerializedName("name")
    private String name;

    @SerializedName("date")
    private String date;

    @SerializedName("ludt")
    private String ludt;

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getLudt() {
        return ludt;
    }
}

public class Main {
    public static void main(String[] args) {
        String json = "[{\"name\":\"Rahul\",\"date\":\"2021-01-01\",\"ludt\":\"2021-01-01T23:11:12.779374\"}]";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (jsonElement, type, jsonDeserializationContext) ->
                LocalDateTime.parse(jsonElement.getAsString(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        );
        Gson gson = gsonBuilder.create();

        Item[] items = gson.fromJson(json, Item[].class);

        for (Item item : items) {
            System.out.println("Name: " + item.getName());
            System.out.println("Date: " + item.getDate());
            System.out.println("LUDT: " + item.getLudt());
        }
    }
}

```


```
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectivityCheck {
    public static void main(String[] args) {
        // Define connection parameters
        String dbUrl = "jdbc:mysql://localhost:3306/your_database_name";
        String username = "your_username";
        String password = "your_password";

        // Try to establish a database connection
        try {
            // Load the JDBC driver for your database
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the database connection
            Connection connection = DriverManager.getConnection(dbUrl, username, password);

            // If the connection is successful, print a success message
            System.out.println("Database connection successful.");

            // Don't forget to close the connection when done
            connection.close();
        } catch (ClassNotFoundException e) {
            System.err.println("Error: JDBC driver not found.");
        } catch (SQLException e) {
            System.err.println("Error: Database connection failed.");
            e.printStackTrace();
        }
    }
}

```
