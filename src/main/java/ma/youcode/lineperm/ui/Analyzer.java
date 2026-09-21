package ma.youcode.lineperm.ui;

import java.util.Scanner;

import ma.youcode.lineperm.service.LogService;

public class Analyzer {
    private LogService logservice = new LogService();

    public void start() {

        LogService.loadLogs();
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
                    logservice.countallactions();
                    break;
                case 2:
                    logservice.countrefusedactions();

                    break;
                case 3:
                    logservice.usersdisctint();

                    break;
                case 4:
                    logservice.actionsPerUser();

                    break;
                case 5:
                    logservice.top3files();

                    break;
                case 6:
                    logservice.accesrefusedfromtheuser();
                    break;
                case 7:
                    logservice.userwithmostactivites();
                    break;
                case 8:
                    logservice.actionsbytype();

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
