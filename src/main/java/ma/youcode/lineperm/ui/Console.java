package ma.youcode.lineperm.ui;

import java.util.Scanner;

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
                    System.out.print("signup \n");

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
    }

}
