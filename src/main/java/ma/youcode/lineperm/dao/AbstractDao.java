package ma.youcode.lineperm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public abstract class AbstractDao<T> implements Dao<T> {

    protected Connection getConnection() throws SQLException {

        Connection conn = DriverManager.getConnection("jdbc:sqlite:db/youcode.db");
        return conn;

    }
   

}
