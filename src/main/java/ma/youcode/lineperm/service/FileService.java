package ma.youcode.lineperm.service;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import ma.youcode.lineperm.model.BriefFile;
import ma.youcode.lineperm.model.Log.Action;
import ma.youcode.lineperm.model.Log.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import java.io.File;

public class FileService {

    UserService userService = new UserService();
    LogService logService = new LogService();

    private HashMap<String, String> filesOwners = new HashMap<>();

    public void createFile(String fileName) {
        // System.out.println(UserService.isAuth);
        // System.out.println(UserService.getCurrentUser().getName());

        String owner = UserService.getCurrentUser().getName();

        System.out.println("creating file ....");

        try {
            FileWriter writer = new FileWriter("src\\main\\resources\\filesStorage\\" + fileName, true);
            writer.close();

        } catch (FileNotFoundException e) {
            System.out.println("File path not found");
        } catch (IOException e) {
            System.out.println("Error saving FILE.");
        }
        BriefFile file = new BriefFile(owner);
        file.setFileName(fileName);

        filesOwners.put(fileName, owner);
        file.setPermition("---");

        try {
            FileWriter writer = new FileWriter("src\\main\\resources\\fileOwners.txt", true);

            String permition = file.getPermition();
            writer.write(owner + ":" + fileName + ":" + permition + "\n");
            writer.close();

        } catch (IOException e) {
            System.out.println("could not write to that file");

        }
        return;

    }

    public void nano(String fileName) {
        String logedUser = UserService.getCurrentUser().getName();
        String[] parts = findFileRecord(fileName);

        if (parts == null) {
            System.out.println("file not found");
            return;
        }
        String owner = parts[0];
        String permition = parts[2];

        if (!owner.equals(logedUser) && permition.charAt(0) != 'w') {
            System.out.println("not allowed");
            logService.addLog(logedUser, fileName, Action.ECRITURE, Status.REFUSE);
            return;
        }

        logService.addLog(logedUser, fileName, Action.ECRITURE, Status.OK);
        Scanner scanner = new Scanner(System.in);

        StringBuilder sc = new StringBuilder();
        System.out.println(" : =============:::mode modifie activer:::============== : ");

        while (true) {
            String text = scanner.nextLine();

            if (text.contains("EOF")) {
                break;
            }
            sc.append(text).append(System.lineSeparator());

        }
        scanner.close();

        try {

            FileWriter writer = new FileWriter("src\\main\\resources\\filesStorage\\" + fileName, true);
            writer.write(sc.toString());
            writer.close();

        } catch (IOException e) {
            System.out.println("could not write the file");
        }
    }

    public void ls() {
        System.out.println("=================");
        System.out.println("lister files....");
        System.out.println("=================");

        String dirctpath = "src\\main\\resources\\filesStorage";

        File dir = new File(dirctpath);

        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                System.out.println(file.getName());
            }
        }

    }

    public void listWithPermision() {
        System.out.println("=================");
        System.out.println("lister files with permision....");
        System.out.println("=================");

        try {
            Path path = Path.of("src\\main\\resources\\fileOwners.txt");

            if (!Files.exists(path)) {
                return;
            }

            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {

                String[] parts = line.split(":");

                String owner = parts[0];
                String fileName = parts[1];
                String permition = parts[2];

                System.out.println("rwd | " + permition + " " + owner + " " + fileName);

            }

        } catch (Exception e) {
            System.out.println("Failed to load path");
        }

    }

    public void cat(String fileName) {
        String logedUser = UserService.getCurrentUser().getName();
        String[] parts = findFileRecord(fileName);
        if (parts == null) {
            System.out.println("file not found");
        }
        String owner = parts[0];
        String permition = parts[2];

        if (!owner.equals(logedUser) && permition.charAt(0) != 'r') {
            System.out.println("not allowed");

            logService.addLog(logedUser, fileName, Action.LECTURE, Status.REFUSE);
            return;
        }

        logService.addLog(logedUser, fileName, Action.LECTURE, Status.OK);

        File file1 = new File("src\\main\\resources\\filesStorage\\" + fileName);
        if (file1.length() == 0) {
            System.out.println("(this file is empty)");
        }
        try {

            Scanner sc = new Scanner(file1);
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
        } catch (Exception e) {
            System.out.println("cant read file");
        }

    }

    public void chmod(String droit, String fileName) {
        String logeduser = UserService.getCurrentUser().getName();
        String[] parts = findFileRecord(fileName);
        String owner = parts[0];
        String permition = parts[2];

        if (!owner.equals(logeduser)) {
            System.out.println("that is not your file to change permition");
            return;
        }

        char[] chars = permition.toCharArray();
        switch (droit) {
            case "r":
                chars[0] = 'r';
                break;
            case "w":
                chars[1] = 'w';
                chars[0] = 'r';
                break;
            case "-r":
                chars[0] = '-';
                chars[1] = '-';
                break;
            case "-w":
                chars[1] = '-';

                break;
            default:
                System.out.println("invalide permition");
                return;

        }
        String newPer = new String(chars);
        updatePermition(fileName, newPer);
        System.out.println(fileName + " : rwd|" + permition + " -> rwd|" + newPer);

    }

    private String[] findFileRecord(String fileName) {
        try {
            Path path = Path.of("src\\main\\resources\\fileOwners.txt");
            if (!Files.exists(path))
                return null;

            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts[1].equals(fileName)) {
                    return parts;
                }
            }
        } catch (IOException e) {
            System.out.println("failed to open and read that file records ");

        }
        return null;
    }

    private void updatePermition(String fileName, String newPermition) {
        try {
            Path path = Path.of("src\\main\\resources\\fileOwners.txt");

            List<String> lines = Files.readAllLines(path);
            List<String> updatedlines = new ArrayList<>();

            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts[1].equals(fileName)) {
                    updatedlines.add(parts[0] + ":" + parts[1] + ":" + newPermition);
                } else {
                    updatedlines.add(line);
                }
            }
            Files.write(path, updatedlines);
        } catch (IOException e) {
            System.out.println("could not update the permition" + e.getMessage());
        }
    }

}
