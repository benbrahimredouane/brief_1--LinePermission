package ma.youcode.lineperm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractDoa {

    protected Connection getConnection() throws SQLException {

        Connection conn = DriverManager.getConnection("jdbc:sqlite:youcode.db");
        return conn;

    };

}
