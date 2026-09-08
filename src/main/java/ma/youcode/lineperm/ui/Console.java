package ma.youcode.lineperm.ui;

import java.util.Scanner;
import ma.youcode.lineperm.service.UserService;

// import java.io.FileWriter;
// import java.io.IOException;
// import java.io.FileNotFoundException;

import java.util.HashMap;

public class Console {

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("lineperm> ");
        String input = scanner.nextLine();

        input.trim().toLowerCase();
        if (input.equals("exit")) {
            System.out.print("bye");
        }

        while (!input.equals("exit")) {

            switch (input) {
                case "signup":

                    System.out.println("=================");
                    System.out.print("signup \n");
                    System.out.println("=================");
                    signup();

                    break;
                case "login":
                    System.out.print("login \n");

                    break;
                case "help":
                    System.out.print("help your self \n");

                    break;

                default:
                    System.out.print("invalide input  \n");
                    break;
            }

            System.out.print("lineperm> ");
            input = scanner.nextLine();

            if (input.equals("exit")) {
                System.out.print("bye");
            }

        }
        scanner.close();
    }

    public static void signup() {
        HashMap<String,String> users = new HashMap<String, String>();
        Scanner scanner = new Scanner(System.in);
        // UserService userservice = new UserService();
        System.out.print("name:");
        String name = scanner.nextLine();

        System.out.print("password:");
        String code = scanner.nextLine();
        // userservice.createUser(name, code);

    

    }

}
