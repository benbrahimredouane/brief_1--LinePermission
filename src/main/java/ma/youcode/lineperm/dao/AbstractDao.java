package ma.youcode.lineperm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public abstract class AbstractDao<T> implements Dao<T> {
    

    private static Connection instance;

    protected Connection getConnection() throws SQLException {
        
        if(instance == null || instance.isClosed()){

        instance = DriverManager.getConnection("jdbc:sqlite:db/youcode.db");
        return instance;
        }
        return instance;

    }
   

}
