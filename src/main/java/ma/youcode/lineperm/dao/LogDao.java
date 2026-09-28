package ma.youcode.lineperm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import ma.youcode.lineperm.model.Log;
import ma.youcode.lineperm.model.Log.*;
import ma.youcode.lineperm.model.User;

public class LogDao extends AbstractDao<Log> {
    UserDao userDao = new UserDao();

    @Override
    public void save(Log log) {
        String sql = "INSERT INTO logs(filename,action,status,UserId) values(?,?,?,?)";

        try (
                Connection conn = getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)

        ) {
            statement.setString(1, log.getFileName());
            statement.setString(2, log.getAction().name());
            statement.setString(3, log.getStatus().name());
            statement.setInt(4, log.getUserId());

            statement.executeUpdate();
            System.out.println("loge saved !");

        } catch (SQLException e) {
            System.err.println("error: " + e.getMessage());

        }

    }

    @Override
    public Optional<Log> findById(int id) {
        // String sql = "SELECT * FROM logs where LogId = ?";
        // try (
        // Connection conn = getConnection();
        // PreparedStatement statement = conn.prepareStatement(sql)) {
        // statement.setInt(1, id);
        // ResultSet res = statement.executeQuery();
        // if (res.next()) {
        // int logid = res.getInt("LogId");
        // Action action = Action.valueOf(res.getString("action"));
        // Status status = Status.valueOf(res.getString("status"));
        // String fileName = "";
        // String datetime = res.getString("created_at");
        // int userid = res.getInt("UserId");

        // String[] parts = datetime.split(" ");

        // LocalDate date = LocalDate.parse(parts[0]);
        // LocalTime time = LocalTime.parse(parts[1]);
        // // Log(LocalDate date, LocalTime time, String fileName, Action action, Status
        // // status, int UserID)
        // return Optional.of(new Log(date, time, fileName, action, status, userid));
        // }
        // } catch (SQLException e) {
        // System.err.println("error: " + e.getMessage());
        // }
        return Optional.empty();
    }

    @Override
    public void delete(Log log) {
        System.out.println("deliting log soon ...");

    }

    public long compterTotal() {

        String sql = "select count(*) from logs";

        try (
                Connection conn = getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                long count = res.getLong("count(*)");
                return count;

            }
        } catch (SQLException e) {
            System.err.println("error: " + e.getMessage());
        }
        return 0;

    }

    public long compterTotalResused() {

        String sql = "select count(*) from logs where status = 'REFUSE'";

        try (
                Connection conn = getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                long count = res.getLong("count(*)");
                return count;

            }
        } catch (SQLException e) {
            System.err.println("error: " + e.getMessage());
        }
        return 0;

    }

    public List<String> usersdisctint() {
        String sql = "SELECT DISTINCT login FROM logs JOIN users on logs.UserId = users.UserId ";
        List<String> users = new ArrayList<>();
        try (
                Connection conn = getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                String name = res.getString("login");
                users.add(name);

            }
            return users;
        } catch (SQLException e) {
            System.err.println("error: " + e.getMessage());
        }
        return null;

    }

    public Map<String, Long> actionsperusers() {
        String sql = "select login , count(*) as times FROM logs join users on logs.UserId = users.UserId group by login";

        Map<String, Long> Actionsuser = new HashMap<>();

        try (
                Connection conn = getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                String login = res.getString("login");
                long times = res.getLong("times");
                Actionsuser.put(login, times);
            }
            return Actionsuser;
        } catch (SQLException e) {
            System.err.println("error: " + e.getMessage());
        }
        return null;
    }

    public Map<String, Long> top3() {
        String sql = "SELECT DISTINCT filename , count(*) as times from logs DESC group by filename LIMIT 3";
        Map<String, Long> top3 = new HashMap<>();

        try (
                Connection conn = getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                String filename = res.getString("filename");
                long n = res.getLong("times");
                top3.put(filename, n);

            }
            return top3;
        } catch (SQLException e) {
            System.err.println("error: " + e.getMessage());
        }
        return null;
    }

    public Map<String, Long> accesrefusedfromtheuser() {

        Map<String, Long> refusedByUser = new HashMap<>();

        String sql = "SELECT login, count(*) as times FROM logs join users on logs.UserId = users.UserId  where status = 'REFUSE' GROUP BY login";

        try(
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)

        ){
            ResultSet res = statement.executeQuery();
            while(res.next()){
                String login = res.getString("login");
                Long times = res.getLong("times");
                refusedByUser.put(login, times);
            }
            return refusedByUser;

        }catch(
            SQLException e
        ){
            System.err.println("error: "+e.getMessage());

        }
        return null;
          

    }
        public Map<String, Long> themost() {

        Map<String, Long> refusedByUser = new HashMap<>();

        String sql = "SELECT login, count(*) as times FROM logs join users on logs.UserId = users.UserId  GROUP BY login HAVING status = \"REFUSE\" order by times desc  limit 1";

        try(
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)

        ){
            ResultSet res = statement.executeQuery();
            if(res.next()){
                String login = res.getString("login");
                Long times = res.getLong("times");
                refusedByUser.put(login, times);
            }
            return refusedByUser;

        }catch(
            SQLException e
        ){
            System.err.println("error: "+e.getMessage());

        }
        return null;
          

    }

            public Map<String, Long> byActiontype() {

        Map<String, Long> Bytype = new HashMap<>();

 String sql = "select DISTINCT action ,count(*) as times from logs group by action";
        try(
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)

        ){
            ResultSet res = statement.executeQuery();
            while(res.next()){
                String action = res.getString("action");
                Long times = res.getLong("times");
                Bytype.put(action, times);
            }
            return Bytype;

        }catch(
            SQLException e
        ){
            System.err.println("error: "+e.getMessage());

        }
        return null;
          

    }

    

}
