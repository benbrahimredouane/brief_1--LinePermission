package ma.youcode.lineperm.service;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import ma.youcode.lineperm.model.BriefFile;
import ma.youcode.lineperm.model.Log.Action;
import ma.youcode.lineperm.model.Log.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.File;

public class FileService {

    UserService userService = new UserService();
    LogService logService = new LogService();

    private static HashMap<String, BriefFile> filesOwners = new HashMap<>();

    public void loadFiles() {

        try {
            Path path = Path.of("src\\main\\resources\\fileOwners.txt");

            if (!Files.exists(path)) {
                System.out.println("file not found");
                return;
            }
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                String[] parts = line.split(":", 3);
                BriefFile brief = new BriefFile(parts[0], parts[1], parts[2]);
                filesOwners.put(parts[1], brief);

            }

        } catch (IOException e) {
            System.out.println("something wrong files not loaded");
        }

    }

    public void createFile(String fileName) {

        String owner = UserService.getCurrentUser().getName();

        System.out.println("creating file ....");

        BriefFile files = filesOwners.get(fileName);

        if (!(files == null)) {
            System.out.println("file allready exists ");
            return;
        }

        try {
            FileWriter writer = new FileWriter("src\\main\\resources\\filesStorage\\" + fileName, true);
            writer.close();

        } catch (FileNotFoundException e) {
            System.out.println("File path not found");
        } catch (IOException e) {
            System.out.println("Error saving FILE.");
        }

        BriefFile file = new BriefFile(owner, fileName, "---");

        filesOwners.put(fileName, file);

        try {
            FileWriter writer = new FileWriter("src\\main\\resources\\fileOwners.txt", true);

            String permition = file.getPermition();
            writer.write(owner + ":" + fileName + ":" + permition + "\n");
            writer.close();

        } catch (IOException e) {
            System.out.println("could not write to that file");

        }

    }

    public void rm(String fileName) {
        BriefFile file = filesOwners.get(fileName);

        if (file == null) {
            System.out.println("file is not exists");
            return;
        }
        String owner = UserService.getCurrentUser().getName();
        String fileOwner = file.getOwner();
        String permition = file.getPermition();
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        System.out.println(permition);

        if (!owner.equals(fileOwner)) {
            if (!permition.contains("d")) {
                
                System.out.println("not allowed");
                logService.addLog(date, time, owner, fileName, Action.DELETE, Status.REFUSE);
                return;
            }

        }

        // remove the file

        Path path = Paths.get("src\\main\\resources\\filesStorage\\" + fileName);
        try {
            boolean deleted = Files.deleteIfExists(path);
            if (deleted) {
                System.out.println("file deleted with succes!");
                filesOwners.remove(fileName);
                logService.addLog(date, time, owner, fileName, Action.DELETE, Status.OK);

                try{
                    Path path2 = Paths.get("src\\main\\resources\\fileOwners.txt");
                    List<String> updatedLines = Files.readAllLines(path2).stream()
                    .filter(line-> !line.contains(fileName))
                    .collect(Collectors.toList());

                    Files.write(path2 , updatedLines);
                    System.out.println("line removed");

                }
                catch(IOException e){
                    System.err.println("Error: "+ e.getMessage());
                }

            } else {
                System.out.println("file not deleted because it not exists !!!!!!");

            }

        } catch (IOException e) {
            System.err.println("failed to delete that file" + e.getMessage());
        }

    }

    public void nano(String fileName) {
        String logedUser = UserService.getCurrentUser().getName();
        BriefFile file = filesOwners.get(fileName);

        if (file == null) {
            System.out.println("file not found ");
            return;
        }

        String owner = file.getOwner();
        String permition = file.getPermition();

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        if (!owner.equals(logedUser) && permition.charAt(0) != 'w') {
            System.out.println("not allowed");

            logService.addLog(date, time, logedUser, fileName, Action.ECRITURE, Status.REFUSE);
            return;
        }

        logService.addLog(date, time, logedUser, fileName, Action.ECRITURE, Status.OK);
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

        BriefFile parts = filesOwners.get(fileName);

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        if (parts == null) {
            System.out.println("file not found");
            return;
        }
        String owner = parts.getOwner();
        String permition = parts.getPermition();

        if (!owner.equals(logedUser) && permition.charAt(0) != 'r') {
            System.out.println("not allowed");

            logService.addLog(date, time, logedUser, fileName, Action.LECTURE, Status.REFUSE);
            return;
        }

        logService.addLog(date, time, logedUser, fileName, Action.LECTURE, Status.OK);

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
            System.out.println("can't read file");
        }

    }

    public void chmod(String droit, String fileName) {
        String logeduser = UserService.getCurrentUser().getName();

        BriefFile parts = filesOwners.get(fileName);

        if (parts == null) {
            System.out.println("file not found");
            return;
        }
        String owner = parts.getOwner();
        String permition = parts.getPermition();

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
            case "d":
                chars[2] = 'd';
                break;
            case "-d":
                chars[2] = '-';
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

    // private String[] findFileRecord(String fileName) {
    // try {
    // Path path = Path.of("src\\main\\resources\\fileOwners.txt");
    // if (!Files.exists(path))
    // return null;

    // List<String> lines = Files.readAllLines(path);
    // for (String line : lines) {
    // String[] parts = line.split(":");
    // if (parts[1].equals(fileName)) {
    // return parts;
    // }
    // }
    // } catch (IOException e) {
    // System.out.println("failed to open and read that file records ");

    // }
    // return null;
    // }

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
