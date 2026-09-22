package ma.youcode.lineperm.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseTest extends AbstractDoa {
    public static void main(String[] args) {

        try (Connection connection = new DatabaseTest().getConnection()) {

            if (connection != null) {
                System.out.println("Database connected successfully!");
            }

        } catch (SQLException e) {

            System.out.println("Database connection failed!");
            e.printStackTrace();

        }

    }

}
