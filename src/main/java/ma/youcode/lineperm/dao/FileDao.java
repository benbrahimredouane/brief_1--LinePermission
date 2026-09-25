package ma.youcode.lineperm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import ma.youcode.lineperm.model.BriefFile;
import ma.youcode.lineperm.model.User;
import ma.youcode.lineperm.service.UserService;

public class FileDao extends AbstractDao<BriefFile> {
    UserDao userDao = new UserDao();

    @Override
    public void save(BriefFile bFile) {

        String sql = "INSERT INTO files(filename,droits,UserId) Values(?,?,?) ";

        if(findById(bFile.getFileId()).isPresent()){
            System.out.println("this file allready exists !");
            return ;
        }

        try (
                Connection conn = getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)

        ) {
            statement.setString(1, bFile.getFileName());
            statement.setString(2, bFile.getPermition());
            statement.setInt(3, UserService.getCurrentUser().getUserId());

            statement.executeUpdate();
            System.out.println("file saved with success ! ");

        } catch (SQLException e) {
            System.err.println("error saving file: " + e.getMessage());
        }

    }

    @Override
    public Optional<BriefFile> findById(int id) {
        // Optional<BriefFile> maybefile =

        String sql = "SELECT * FROM files WHERE Fileid = ?";

        try (
                Connection conn = getConnection();
                PreparedStatement statment = conn.prepareStatement(sql)) {
            statment.setInt(1, id);

            ResultSet resu = statment.executeQuery();

            if (resu.next()) {
                int idd = resu.getInt("FileId");
                String fileName = resu.getString("filename");
                String droits = resu.getString("droits");
                int usId = resu.getInt("UserId");

                Optional<User> owner = userDao.findById(usId);

                BriefFile bfile = new BriefFile(owner.get().getName(), fileName, droits);
                return Optional.of(bfile);

            }

        } catch (SQLException e) {
            System.err.println("error finding the file : " + e.getMessage());
        }
        return Optional.empty();

    }

    @Override
    public void delete(BriefFile bfile) {
        String sql = "DELETE FROM files where FileId = ?";
        try (
                Connection conn = getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, bfile.getFileId());
            statement.executeUpdate();

            System.out.println("file deleteed with succes !!!");

        } catch (SQLException e) {
            System.err.println("error deleting that file: " + e.getMessage());
        }

    }

    public Optional<BriefFile> findByProprietaire(int UserId) {
        String sql = "SELECT * FROM files WHERE UserId = ?";

        try (
                Connection conn = getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, UserId);
            ResultSet bfile = statement.executeQuery();

            if (bfile.next()) {
                int id = bfile.getInt("FileI");
                String filename = bfile.getString("filename");
                String droits = bfile.getString("droits");
                int uId = bfile.getInt("UserId");
                Optional<User> owner = userDao.findById(uId);

                return Optional.of(new BriefFile(owner.get().getName(), filename, droits));

            }

        } catch (SQLException e) {
            System.err.println("error: " + e.getMessage());

        }
        return Optional.empty();

    }

    public void updateDroits(int id, String droit) {

        Optional<BriefFile> bfile = findById(id);

        if (bfile.isPresent()) {
            BriefFile file = bfile.get();

            if (!UserService.getCurrentUser().getName().equals(file.getOwner())) {
                System.out.println("not allowed : not your file");
                return;
            }

            String permition = file.getPermition();
            char[] chars = permition.toCharArray();

            switch (droit) {
                case "r":
                    chars[0] = 'r';
                    break;
                case "w":
                    chars[1] = 'w';
                    chars[0] = 'r';
                    break;
                case "d":
                    chars[2] = 'd';
                    break;
                case "-d":
                    chars[2] = '-';
                    break;
                case "-r":
                    chars[0] = '-';
                    chars[1] = '-';
                    break;
                case "-w":
                    chars[1] = '-';

                    break;
                default:
                    System.out.println("invalide permition");
                    return;
            }
            String newPer = new String(chars);

            file.setPermition(newPer);

            // change the permition on db
            String sql = "UPDATE TABLE files set droits = ? where FileId = ?";

            try (
                    Connection conn = getConnection();
                    PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, newPer);
                statement.setInt(2, id);
                statement.executeUpdate();
                System.out.println("update with suucees");
                System.out.println(file.getFileName() + " : rwd|" + permition + " -> rwd|" + newPer);

            } catch (SQLException e) {
                System.err.println("error: " + e.getMessage());

            }

        } else {
            System.out.println("this file not exists");
        }
    }

}
