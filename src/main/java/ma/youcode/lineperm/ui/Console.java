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

        if (input.equals("exit")) {
            System.out.print("bye");
        }
        input = input.trim().toLowerCase();

        userService.loadUsers();

        while (!input.equals("exit")) {
            String[] parts = input.split(" ");

            if (parts.length == 1) {

                switch (input) {
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

                        break;

                    case "logout":
                        System.out.println("=================");
                        logout();
                        System.out.println("=================");
                        break;
                    case "ls":
                        ls();
                        break;

                    default:
                        System.out.print("invalide input  \n");
                        break;
                }

            } else if (parts.length == 2) {
                String commande = parts[0];
                String fileName = parts[1];

                switch (commande) {
                    case "touch":
                        createfile(fileName);

                        break;
                    case "nano":
                        nano(fileName);
                        break;

                    case "ls":

                        String option = parts[1];
                        listwithpermition(option);
                        break;

                    case "cat":
                        cat(fileName);

                        break;

                    default:
                        System.out.println("invalide commannde");
                        break;
                }

            } else if (parts.length == 3) {
                String commande = parts[0];
                String droit = parts[1];
                String fileName = parts[2];

                switch (commande) {
                    case "chmod":
                        chmod(droit, fileName);

                        break;

                    default:
                        break;
                }
            } else {
                System.out.println("invalid command");
            }

            if (UserService.isAuth) {
                System.out.print(UserService.getCurrentUser().getName() + "@lineperm> ");
            } else {
                System.out.print("lineperm>");
            }

            input = scanner.nextLine();

            if (input.equals("exit")) {
                System.out.print("bye");
            }

        }
        scanner.close();
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

    public static void logout() {
        userService.logout();

    }

    public static void createfile(String fileName) {
        if (UserService.isAuth) {

            fileService.createFile(fileName);
        } else {
            System.out.println("you are not connected ::!!!");
        }

    }

    public static void nano(String fileName) {
        if (UserService.isAuth) {
            fileService.nano(fileName);
        } else {
            System.out.println("your are not connected");
        }
    }

    public static void ls() {
        if (!UserService.isAuth) {
            System.out.println("your are not connected !!");
            return;
        }
        fileService.ls();
    }

    public static void cat(String fileName) {
        if (!UserService.isAuth) {
            System.out.println("your are not connected !!");
            return;
        }
        fileService.cat(fileName);

    }

    public static void listwithpermition(String option) {
        if (option.equals("-l")) {
            fileService.listWithPermision();
        }

    }

    public static void chmod(String droit, String fileName) {
        if (!UserService.isAuth) {
            System.out.println("your are not connected !!");
            return;
        }

        fileService.chmod(droit, fileName);

    }

    public static void stats() {
        Analyzer analyzer = new Analyzer();
        analyzer.start();

    }

}
