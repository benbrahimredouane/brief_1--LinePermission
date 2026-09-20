package ma.youcode.lineperm.ui;

// import java.io.IOException;
// import java.nio.file.Files;
import java.util.Scanner;

import ma.youcode.lineperm.service.FileService;
import ma.youcode.lineperm.service.UserService;

public class Console {
    public static UserService userService = new UserService();
    public static FileService fileService = new FileService();

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("lineperm> ");
        String input = scanner.nextLine();

        input = input.trim().toLowerCase();

        userService.loadUsers();
        fileService.loadFiles();
        do {
            String[] parts = input.split(" ");
            switch (parts[0]) {
                case "signup":

                    System.out.println("=================");
                    System.out.print("signup \n");
                    System.out.println("=================");

                    signup(scanner);
                    break;

                case "login":
                    System.out.println("=================");
                    System.out.print("login \n");
                    System.out.println("=================");
                    login(scanner);

                    break;
                case "stats":
                    System.out.println("=================");
                    System.out.print("stats \n");
                    System.out.println("=================");
                    stats();

                    break;
                case "help":
                    System.out.println("=================");
                    System.out.print("help your self \n");
                    System.out.println("=================");
                    help();

                    break;

                case "logout":
                    System.out.println("=================");
                    logout();
                    System.out.println("=================");
                    break;
                case "ls":
                    if (!parts[1].isEmpty()) {

                        listwithpermition(parts[1]);

                    } else {
                        ls();
                    }
                    break;

                case "touch":
                    createfile(parts[1]);

                    break;
                case "nano":
                    nano(parts[1]);
                    break;

                case "cat":
                    cat(parts[1]);

                    break;
                case "rm":
                    rm(parts[1]);

                    break;
                case "chmod":
                    chmod(parts[1], parts[2]);
                    break;
                case "exit":
                    System.out.println("bye:: lineperm team ");
                    return;

                default:
                    System.out.println("invalide input");

            }

            if (UserService.isAuth) {
                System.out.print(UserService.getCurrentUser().getName() + "@lineperm> ");
            } else {
                System.out.print("lineperm>");
            }

            input = scanner.nextLine();

            // if (input.equals("exit")) {
            // System.out.print("bye");
            // }

        } while (!input.equals("exit"));
    }

    public static void signup(Scanner scanner) {

        System.out.print("name:");
        String name = scanner.nextLine();

        if (name.isEmpty()) {
            System.out.println("that name is empty");
            return;
        }

        System.out.print("password:");
        String code = scanner.nextLine();

        if (code.isEmpty() || code.length() < 4) {
            System.out.println("that code is unvalide");
            return;
        }

        userService.createUser(name, code);

    }

    public static void login(Scanner scanner) {
        if (UserService.isAuth) {
            System.out.println("allready loged in !!");
            return;
        }
        System.out.print("name:");
        String name = scanner.nextLine();

        System.out.print("password:");
        String code = scanner.nextLine();

        userService.login(name, code);

        if (UserService.isAuth) {
            System.out.println("Login succesuly::::");
            System.out.println("welcome " + UserService.getCurrentUser().getName());

        } else {
            System.out.println("wrong name or password!");
        }
    }

    private void logout() {
        userService.logout();

    }

    private void createfile(String fileName) {
        if (UserService.isAuth) {

            fileService.createFile(fileName);
        } else {
            System.out.println("you are not connected ::!!!");
        }

    }

    private void nano(String fileName) {
        if (UserService.isAuth) {
            fileService.nano(fileName);
        } else {
            System.out.println("your are not connected");
        }
    }

    private void ls() {
        if (!UserService.isAuth) {
            System.out.println("your are not connected !!");
            return;
        }
        fileService.ls();
    }

    private void cat(String fileName) {
        if (!UserService.isAuth) {
            System.out.println("your are not connected !!");
            return;
        }
        fileService.cat(fileName);

    }

    private void listwithpermition(String option) {
        if (option.equals("-l")) {
            fileService.listWithPermision();
        }

    }

    private void chmod(String droit, String fileName) {
        if (!UserService.isAuth) {
            System.out.println("your are not connected !!");
            return;
        }

        fileService.chmod(droit, fileName);

    }

    private void stats() {
        Analyzer analyzer = new Analyzer();
        analyzer.start();

    }

    private void help() {
        if (!UserService.isAuth) {
            System.out.println("signup");
            System.out.println("login");

        } else {
            System.out.println("cat + fileName");
            System.out.println("nano + filename");
            System.out.println("touch + fileName");
            System.out.println("chmod + fileName");
            System.out.println("rm + fileName");
        }
    }

    private void rm(String fileName) {
        fileService.rm(fileName);
    }

}
