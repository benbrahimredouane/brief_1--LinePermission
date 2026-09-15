package ma.youcode.lineperm.ui;

import java.util.Scanner;

public class Analyzer {
    public void start() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("welcome to the stats part chose an option by its number");

        System.out.println("=============LogAnalyzer============");

        System.out.println("1) Number of total actions");
        System.out.println("2) Number d'acces refuses");
        System.out.println("3) Users are distinct");
        System.out.println("4) Actions by user");
        System.out.println("5) Top 3 files consulted");
        System.out.println("6) Acces refuses from the user");
        System.out.println("7) User with most activities");
        System.out.println("8) Partition of actions by type");
        System.out.println("0) Quitter");

        System.out.print("choix :");
        int number = scanner.nextInt();
        if (number == 0) {
            System.out.println("Au revoir ! analyzeur team");

            return;
        }
        while (number != 0) {

            switch (number) {
                case 1:
                    System.out.println("you chose 1");
                    break;
                case 2:
                    System.out.println("you chose 2");

                    break;
                case 3:
                    System.out.println("you chose 3");

                    break;
                case 4:
                    System.out.println("you chose 4");

                    break;
                case 5:
                    System.out.println("you chose 5");

                    break;
                case 6:
                    System.out.println("you chose 6");

                case 7:
                    System.out.println("you chose 7");

                    break;
                case 8:
                    System.out.println("you chose 8");

                    break;
                case 0:
                    System.out.println("Au revoir Analyzer");
                    break;

                default:
                    System.out.println("invalid choix");
                    break;
            }
            System.out.println("1) Number of total actions");
            System.out.println("2) Number d'acces refuses");
            System.out.println("3) Users are distinct");
            System.out.println("4) Actions by user");
            System.out.println("5) Top 3 files consulted");
            System.out.println("6) Acces refuses from the user");
            System.out.println("7) User with most activities");
            System.out.println("8) Partition of actions by type");
            System.out.println("0) Quitter");

            System.out.print("choix :");
            number = scanner.nextInt();

        }

    }

}
