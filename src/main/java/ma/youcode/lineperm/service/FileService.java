package ma.youcode.lineperm.service;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import ma.youcode.lineperm.model.FileE;

import java.util.Scanner;

import java.io.File;

public class FileService {
    private FileE filePro;
    UserService userService = new UserService();

    public void createFile(String fileName) {
        System.out.println("creating file ....");

        try {
            FileWriter writer = new FileWriter("src\\main\\resources\\filesStorage\\" + fileName, true);

            // writer.write(content + "\n");

            // writer.close();

            // file propriters
            // filePro = new FileE(userService.getCurrentUser().getName());
            // filePro.setPro(userService.getCurrentUser().getName());

        } catch (FileNotFoundException e) {
            System.out.println("File path not found");
        } catch (IOException e) {
            System.out.println("Error saving user.");
        }
        return;

    }

    public void nano(String fileName) {

        Scanner scanner = new Scanner(System.in);

        StringBuilder sc = new StringBuilder();
        System.out.println("mode modifie activer : ============================ : ");

        while (true) {
            String text = scanner.nextLine();

            if (text.contains("EOF")) {
                break;
            }
            sc.append(text).append(System.lineSeparator());

        }

        System.out.println(sc);
        // scanner.close();
        try {
            // FileWriter writer = new FileWriter("",true)
            FileWriter writer = new FileWriter("src\\main\\resources\\filesStorage\\" + fileName, true);
            writer.write(sc.toString());
            writer.close();

        } catch (IOException e) {
            System.out.println("could not write the file");

        }
    }

    public void ls() {
        System.out.println("lister files....");
        String dirctpath = "src\\main\\resources\\filesStorage";

        File dir = new File(dirctpath);

        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                System.out.println(file.getName());
            }
        }

    }

}
