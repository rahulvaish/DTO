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

```
SELECT * from TradeEvent t1 where t1.bookId=? and t1.securityId=? and t1.lastUpdate >= (SELECT MAX(t2.lastUpdate) from TradeEvent t2 where t2.bookId=? and t2.securityId=? and t1.securityId=t2.securityId and eventType='EODPosition' AND LastUpdate <?) ORDER BY securityId, tradeId, version 

@Autowired
    private MongoTemplate mongoTemplate;

    public List<TradeEvent> findTradeEvents(String bookId, String securityId, String maxLastUpdate) {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("bookId").is(bookId)
                .and("securityId").is(securityId)
                .and("lastUpdate").gte(maxLastUpdate)),
            Aggregation.group("securityId").last("securityId").as("securityId")
                .last("tradeId").as("tradeId")
                .last("version").as("version"),
            Aggregation.sort(Sort.by("securityId", "tradeId", "version"))
        );

        AggregationResults<TradeEvent> results = mongoTemplate.aggregate(aggregation, "TradeEvent", TradeEvent.class);
        return results.getMappedResults();
    }
}





SELECT * from TradeEvent where bookId=? and security=? and eventType='EODPosition' ORDER BY LastUpdate DESC LIMIT 1

public List<TradeEvent> findLatestTradeEvent(String bookId, String security, String eventType) {
        MatchOperation match = Aggregation.match(Criteria.where("bookId").is(bookId)
                .and("security").is(security)
                .and("eventType").is("EODPosition"));

        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.DESC, "LastUpdate"));

        Aggregation aggregation = Aggregation.newAggregation(match, sort, Aggregation.limit(1));

        return mongoTemplate.aggregate(aggregation, "TradeEvent", TradeEvent.class).getMappedResults();
    }



SELECT * FROM TradeEvent where bookId=:bookId AND securityId=:securityId AND tradeId IN(:id) ORDER BY securityId, tradeId, version
public List<TradeEvent> findTradeEvents(String bookId, String securityId, List<String> tradeIds) {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("bookId").is(bookId)
                .and("securityId").is(securityId)
                .and("tradeId").in(tradeIds)),
            Aggregation.sort(Sort.by("securityId", "tradeId", "version"))
        );

        return mongoTemplate.aggregate(aggregation, "TradeEvent", TradeEvent.class).getMappedResults();
    }
```
```
**ListTradeIdsSinceLastEOD**

SELECT DISTINCT t1.tradeId FROM TradeEvent t1 WHERE t1.bookId=? AND t1.securityId=? AND t1.eventType != 'EODPosition' AND t1.LastUpdate >= (SELECT MAX(t2.LastUpdate) FROM TradeEvent t2 WHERE t1.BookId = t2.BookId AND t1.securityId=t2.securityId AND t2.eventType='EODPosition') OR 0=(SELECT COUNT(t3.securityId) FROM TradeEvent t3 where t1.bookId = t3.bookId AND t1.securityId=t3.securityId AND t3.eventType='EODPosition')


public List<String> findDistinctTradeIds(String bookId, String securityId) {
        Aggregation aggregation = Aggregation.newAggregation(
            // Subquery for MAX(t2.LastUpdate)
            Aggregation.match(Criteria.where("BookId").is(bookId)
                .and("securityId").is(securityId)
                .and("eventType").is("EODPosition")),
            Aggregation.group("securityId")
                .max("LastUpdate").as("maxLastUpdate"),
            
            // Main aggregation
            Aggregation.match(new Criteria().andOperator(
                Criteria.where("bookId").is(bookId),
                Criteria.where("securityId").is(securityId),
                Criteria.where("eventType").ne("EODPosition")
                    .and("LastUpdate").gte(Aggregation.getField("maxLastUpdate"))
            )),
            Aggregation.group("tradeId").addToSet("tradeId").as("tradeIds"),
            Aggregation.lookup("TradeEvent", "bookId", "bookId", "tradeEvents"),
            Aggregation.addFields().addFieldWithValue("countSecurityId",
                Aggregation.size("tradeEvents")),
            Aggregation.match(new Criteria().orOperator(
                Criteria.where("countSecurityId").is(0),
                Criteria.where("countSecurityId").is(0)
            )),
            Aggregation.project("tradeIds")
        );

        AggregationResults<String> results = mongoTemplate.aggregate(aggregation, "TradeEvent", String.class);
        return results.getMappedResults();
    }

```
