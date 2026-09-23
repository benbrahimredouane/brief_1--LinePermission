package ma.youcode.lineperm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ma.youcode.lineperm.model.User;

public class UserDao extends AbstractDoa {

    public void save(User user) {

        String sql = "INSERT INTO users(login,password) VALUES(?,?);";

        try (
                Connection conn = getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)

        ) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getCode());

            statement.executeUpdate();
            System.out.println("user saved with success!");

        } catch (SQLException e) {
            System.err.println("error saving user : " + e.getMessage());
        }

    }

    public User findByUserName(String username) {

        String sql = "select * FROM users where login = ?";

        try (
                Connection conn = getConnection();
                PreparedStatement stas = conn.prepareStatement(sql)) {

            stas.setString(1,username);
            ResultSet res = stas.executeQuery();
            if(res.next()){
                int id = res.getInt("UserID");
                String login = res.getString("login");
                String password = res.getString("password");
                return new User(id,login,password);
            }
            System.out.println("user find with succes !");

        } catch (SQLException e) {
            System.err.println("somethnig wrong with : " + e.getMessage());
        }
        return null;

    }
    public void findById(int id){

    }
    public void delete(User user){

    }

}
