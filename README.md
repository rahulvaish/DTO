# DTO

```
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XMLFileExporter {

    public static void main(String[] args) {
        // Database connection details for Microsoft SQL Server
        String jdbcUrl = "jdbc:sqlserver://localhost:1433;databaseName=your_database"; // Replace with your DB details
        String username = "your_username";  // Replace with your DB username
        String password = "your_password";  // Replace with your DB password
        String outputDir = "/path/to/output/directory"; // Replace with desired output directory

        try {
            // 1. Establish a database connection
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            // 2. Execute the query to get the XML data from the table
            String query = "SELECT ABC FROM your_table"; // Replace with your table name and column
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // 3. Process the XML data
            while (resultSet.next()) {
                String xmlContent = resultSet.getString("ABC");

                // 4. Parse the XML content to get the <name> tag
                String fileName = extractNameFromXML(xmlContent);

                if (fileName != null && !fileName.isEmpty()) {
                    // 5. Write the XML content to a file with the <name> tag as filename
                    File outputFile = new File(outputDir, fileName + ".xml");
                    FileWriter writer = new FileWriter(outputFile);
                    writer.write(xmlContent);
                    writer.close();

                    System.out.println("File created: " + outputFile.getAbsolutePath());
                } else {
                    System.out.println("Skipping XML as <name> tag is missing");
                }
            }

            // 6. Clean up
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to extract <name> tag from the XML string
    private static String extractNameFromXML(String xmlContent) {
        try {
            // Parse the XML content
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new java.io.ByteArrayInputStream(xmlContent.getBytes()));

            // Extract the content of the <name> tag
            NodeList nodeList = document.getElementsByTagName("name");
            if (nodeList.getLength() > 0) {
                return nodeList.item(0).getTextContent().trim();  // Get the name tag content
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;  // Return null if <name> tag is not found
    }
}
```
