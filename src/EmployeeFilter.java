import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeFilter {
    // To retrive data from database and filter by position= QA engineer
    public static void main(String[] args) {
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://127.0.0.1:3306/company";
        String user = "root";
        String password = "";

        try {
            // Establish a connection
            Connection connection = DriverManager.getConnection(url, user, password);

            // SQL query to filter data by position
            String sql = "SELECT * FROM employees WHERE position = ?";

            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set the parameter for the position
            preparedStatement.setString(1, "Quality Engineer");

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String position = resultSet.getString("position");
                String department = resultSet.getString("department");

                // Print or process the retrieved data as needed
                System.out.println(
                        "ID: " + id + ", Name: " + name + ", Position: " + position + ", Department: " + department);
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
