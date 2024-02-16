import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

// To save employee.xml to database

public class XMLToDatabase {

    public static void main(String[] args) {
        try {
            // Load the XML file
            File xmlFile = new File("D:\\Projects\\SGIC\\XML Data Filter Using Java\\src\\Exployee.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // Normalize the XML structure
            doc.getDocumentElement().normalize();

            // Set up the database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "");

            // Process each employee node in the XML
            NodeList employeelist = doc.getElementsByTagName("employee");
            for (int i = 0; i < employeelist.getLength(); i++) {
                Node EmployeeNode = employeelist.item(i);
                if (EmployeeNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element employeeElement = (Element) EmployeeNode;

                    // Extract data from XML
                    int id = Integer.parseInt(employeeElement.getElementsByTagName("id").item(0).getTextContent());
                    String name = employeeElement.getElementsByTagName("name").item(0).getTextContent();
                    String position = employeeElement.getElementsByTagName("position").item(0).getTextContent();
                    String department = employeeElement.getElementsByTagName("department").item(0).getTextContent();

                    // Save data to the database
                    saveToDatabase(connection, id, name, position, department);
                }
            }

            // Close the database connection
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveToDatabase(Connection connection, int id, String name, String position, String department)
            throws SQLException {

        String sql = "INSERT INTO employees (id, name, position, department) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, position);
            statement.setString(4, department);
            statement.executeUpdate();
        }
    }

}
