package ma.youcode.lineperm.ui;

import java.util.Scanner;

import javax.swing.plaf.synth.SynthToolTipUI;

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

        userService.loadUsers();

        while (!input.equals("exit")) {
            String[] parts = input.split(" ", 2);

            if (parts.length == 1) {

                switch (input) {
                    case "signup":

                        System.out.println("=================");
                        System.out.print("signup \n");
                        System.out.println("=================");

                        signup(scanner);
                        break;
                    case "login":

                        System.out.print("login \n");
                        login(scanner);

                        break;
                    case "help":
                        System.out.print("help your self \n");

                        break;

                    case "logout":
                        logout();
                        break;

                    default:
                        System.out.print("invalide input  \n");
                        break;
                }

            } else {
                String commande = parts[0];
                String fileName = parts[1];

                switch (commande) {
                    case "touch":
                        createfile();

                        break;

                    default:
                        System.out.println("invalide commannde");
                        break;
                }

            }

            if (userService.isAuth) {
                System.out.print(userService.getCurrentUser().getName() + "@lineperm> ");
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
        if (userService.isAuth) {
            System.out.println("allredy loged in !!");
            return;
        }
        System.out.print("name:");
        String name = scanner.nextLine();

        System.out.print("password:");
        String code = scanner.nextLine();

        userService.login(name, code);

        if (userService.isAuth) {
            System.out.println("Login succesuly::::");
            System.out.println("welcome " + userService.getCurrentUser().getName());

        } else {
            System.out.println("wrong name or password!");
        }
    }

    public static void logout() {
        if (!userService.isAuth) {
            System.out.println("your are not connected to do that");
            return;
        }
        System.out.print("loging out ... \n");
        userService.isAuth = false;

    }

    public static void createfile() {
        if(userService.isAuth){
        fileService.createFile();
        }
        else{
            System.out.println("you are not connected ::!!!");
        }

    }

}
