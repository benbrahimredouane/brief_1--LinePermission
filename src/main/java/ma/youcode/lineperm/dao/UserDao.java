package ma.youcode.lineperm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import ma.youcode.lineperm.model.User;

public class UserDao extends AbstractDao<User> {

    @Override
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

    public Optional<User> findByUserName(String username) {

        String sql = "select * FROM users where login = ?";

        try (
                Connection conn = getConnection();
                PreparedStatement stas = conn.prepareStatement(sql)) {

            stas.setString(1, username);
            ResultSet res = stas.executeQuery();
            if (res.next()) {
                int id = res.getInt("UserID");
                String login = res.getString("login");
                String password = res.getString("password");

                return Optional.of(new User(id, login, password));
            }

        } catch (SQLException e) {
            System.err.println("somethnig wrong with : " + e.getMessage());
        }
        return Optional.empty();

    }

    @Override
    public Optional<User> findById(int id) {
        String sql = "SELECT * FROM users WHERE UserId = ?";
        try (
                Connection conn = getConnection();
                PreparedStatement sts = conn.prepareStatement(sql);) {
            sts.setInt(1, id);
            ResultSet res = sts.executeQuery();
            if (res.next()) {
                int UserId = res.getInt("UserId");
                String login = res.getString("login");
                String password = res.getString("password");

                User newUser = new User(UserId, login, password);
                Optional<User> opUser = Optional.of(newUser);
                return opUser;

            }
        } catch (SQLException e) {
            System.err.println("error: " + e.getMessage());

        }
        return Optional.empty();

    }

    @Override
    public void delete(User user) {

        String sql = "DELETE FROM users where UserId = ?";

        try (
                Connection conn = getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, user.getUserId());

            statement.executeUpdate();

            System.out.println("user deleted with success:!");

        } catch (SQLException e) {
            System.err.println("error : " + e.getMessage());
        }

    }

}
