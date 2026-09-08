package ma.youcode.lineperm.ui;

import java.util.Scanner;
import ma.youcode.lineperm.service.UserService;


public class Console {
    public static UserService userService = new UserService();


    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("lineperm> ");
        String input = scanner.nextLine();

        input = input.trim().toLowerCase();
        if (input.equals("exit")) {
            System.out.print("bye");
        }

        while (!input.equals("exit")) {

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

    public static void signup(Scanner scanner) {

        System.out.print("name:");
        String name = scanner.nextLine();

        System.out.print("password:");
        String code = scanner.nextLine();

        userService.createUser(name, code);

    }
    public static void login(Scanner scanner){
        System.out.println("name:  ");
        String name = scanner.nextLine();

        System.out.println("password:  ");
        String code = scanner.nextLine();

        boolean isAuth = userService.login(name,code);

        if(isAuth){
            System.out.println("Login succesuly::::");
            System.out.println("welcome " + userService.getCurrentUser().getName());


        }else{
            System.out.println("wrong name or password!");
        }
    }

}
