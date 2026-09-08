package ma.youcode.lineperm.service;

import ma.youcode.lineperm.model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.HashMap;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;

public class UserService {
    public static boolean isAuth = false;
    private HashMap<String, String> users = new HashMap<>();

    private User currentUser;

    public void createUser(String name, String code) {

        if (users.containsKey(name)) {
            System.out.println("allready existe");
            return;
        }
        User user = new User(name, code);

        users.put(name, code);

        System.out.println(users);

        try {
            FileWriter writer = new FileWriter("src\\main\\resources\\users.txt", true);

            writer.write(name + ":" + code + "\n");

            writer.close();

        } catch (FileNotFoundException e) {
            System.out.println("File path not found");
        } catch (IOException e) {
            System.out.println("Error saving user.");
        }
        currentUser = user;
        System.out.println("Welcome " + currentUser.getName());
        isAuth = true;

    }

    public void login(String name, String code) {
        if (users.containsKey(name)) {
            if (users.get(name).equals(code)) {
                currentUser = new User(name, code);
                isAuth = true;

            }
        }
    }

    public User getCurrentUser() {
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

                String[] parts = line.split(":");

                String name = parts[0];
                String password = parts[1];
               
                users.put(name, password);

            }

        } catch (Exception e) {
            System.out.println("Failed to load path");
        }

    }

}
