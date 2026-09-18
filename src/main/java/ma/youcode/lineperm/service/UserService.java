package ma.youcode.lineperm.service;

import ma.youcode.lineperm.model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.List;

import java.nio.file.Path;
import java.nio.file.Files;

import org.mindrot.jbcrypt.BCrypt;

public class UserService {

    public static boolean isAuth = false;
    private HashMap<String, User> users = new HashMap<>();

    private static User currentUser;

    public void createUser(String name, String code) {

        if (users.containsKey(name)) {
            System.out.println("allready existe");
            return;
        }

        String pass = BCrypt.hashpw(code, BCrypt.gensalt());

        User user = new User(name, pass);

        try {
            FileWriter writer = new FileWriter("src\\main\\resources\\users.txt", true);

            writer.write(name + ":" + pass + "\n");

            writer.close();

        } catch (FileNotFoundException e) {
            System.out.println("File path not found");
        } catch (IOException e) {
            System.out.println("Error saving user.");
        }
        users.put(name, user);
        currentUser = user;
        System.out.println("Welcome " + currentUser.getName());
        isAuth = true;

    }

    public void login(String name, String code) {
        if (users.containsKey(name)) {
            User user = users.get(name);

            if (BCrypt.checkpw(code, user.getCode())) {
                currentUser = new User(name, user.getCode());
                isAuth = true;

            } else {
                System.out.println("wrong password");
            }
        } else {
            System.out.println("User not found");
        }
    }

    public void logout() {
        // System.out.println(isAuth);
        if (!isAuth) {
            System.out.println("your are not connected to do that");
            return;
        }
        System.out.print("loging out ... \n");
        isAuth = false;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public void loadUsers() {

        try {
            Path path = Path.of("src/main/resources/users.txt");

            if (!Files.exists(path)) {
                return;
            }

            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {

                String[] parts = line.split(":", 2);

                String name = parts[0];
                String password = parts[1];

                User user = new User(name, password);

                users.put(name, user);

            }

        } catch (Exception e) {
            System.out.println("Failed to load path");
        }

    }

}
