package ma.youcode.lineperm.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.Action;

import ma.youcode.lineperm.dao.FileDao;
import ma.youcode.lineperm.model.BriefFile;
import ma.youcode.lineperm.model.User;

// import java.io.FileNotFoundException;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.time.LocalDate;
// import java.time.LocalTime;
// import java.time.format.DateTimeFormatter;

// import ma.youcode.lineperm.model.BriefFile;
// import ma.youcode.lineperm.model.Log.Action;
// import ma.youcode.lineperm.model.Log.Status;

// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Scanner;
// import java.util.stream.Collectors;
// import java.io.File;

public class FileService {

    // UserService userService = new UserService();
    // LogService logService = new LogService();
    private FileDao filedao;

    public FileService(FileDao fileDao) {
        this.filedao = fileDao;
    }

    public void createFile(String fileName) {

        String owner = UserService.getCurrentUser().getName();
        String permition = "---";

        BriefFile file = new BriefFile(owner, fileName, permition);

        System.out.println("creating file ....");
        try {
            FileWriter writer = new FileWriter("src\\main\\resources\\filesStorage\\" + fileName);
            writer.close();
            filedao.save(file);
        } catch (FileNotFoundException e) {
            System.err.println("error" + e.getMessage());

        } catch (IOException e) {
            System.err.println("error: " + e.getMessage());
        }

    }

    public void rm(String fileName) {

        if (!UserService.isAuth()) {
            System.out.println("you are not connected!");
            return;
        }

        int id = UserService.getCurrentUser().getUserId();
        Optional<BriefFile> Optionalfile = filedao.findByProprietaire(id);

        if (Optionalfile.isEmpty()) {
            System.out.println("file is not exists");
            return;
        }
        BriefFile file = Optionalfile.get();

        String owner = UserService.getCurrentUser().getName();

        String fileOwner = file.getOwner();
        String permition = file.getPermition();
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        if (!owner.equals(fileOwner)) {
            if (!permition.contains("d")) {

                System.out.println("not allowed");
                // logService.addLog(date, time, owner, fileName, Action.DELETE, Status.REFUSE);
                return;
            }

        }

        // remove the file

        Path path = Paths.get("src\\main\\resources\\filesStorage\\" + fileName);

        try {
            boolean deleted = Files.deleteIfExists(path);
            if (deleted) {
                System.out.println("file deleted with succes!");

                // logService.addLog(date, time, owner, fileName, Action.DELETE, Status.OK);

            }
        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public void nano(String fileName) {

        if(!UserService.isAuth()){
            System.out.println("you are not connected!!!");
            return;

        }

    String logedUser = UserService.getCurrentUser().getName();

    Optional<BriefFile> Optionalfile = filedao.findByFileName(fileName);
    if(Optionalfile.isEmpty()){
        System.out.println("this file not even exists");
        return;

    }
    BriefFile file = Optionalfile.get();

    String owner = file.getOwner();
    String permition = file.getPermition();

    LocalDate date = LocalDate.now();
    LocalTime time = LocalTime.now();

    if (!owner.equals(logedUser) && permition.charAt(0) != 'w') {
    System.out.println("not allowed");

    // logService.addLog(date, time, logedUser, fileName, Action.ECRITURE,Status.REFUSE);
    return;
    }

    // logService.addLog(date, time, logedUser, fileName, Action.ECRITURE,
    // Status.OK);
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

    try {

    FileWriter writer = new FileWriter("src\\main\\resources\\filesStorage\\" +
    fileName, true);
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

        if(!UserService.isAuth()){
            System.out.println("you are not connected !");
            return;
        }

    System.out.println("=================");
    System.out.println("lister files with permision....");
    System.out.println("=================");

    List<BriefFile> files= filedao.findall() ;
    for(BriefFile file : files){
        System.out.println("rwd | " + file.getPermition() + " " + file.getOwner() + " " + file.getFileName());
    }

    }

    public void cat(String fileName) {
    String logedUser = UserService.getCurrentUser().getName();

    Optional<BriefFile> Optionalfile = filedao.findByFileName(fileName);

    if(Optionalfile.isEmpty()){
        System.out.println("that file not found!");
        return;
    }
    BriefFile file = Optionalfile.get();

    // LocalDate date = LocalDate.now();
    // LocalTime time = LocalTime.now();

   
    String owner = file.getOwner();
    String permition = file.getPermition();

    if (!owner.equals(logedUser) && permition.charAt(0) != 'r') {
    System.out.println("not allowed");
    return;
    }

    // logService.addLog(date, time, logedUser, fileName, Action.LECTURE,
    // Status.REFUSE);
    // return;
    // }

    // logService.addLog(date, time, logedUser, fileName, Action.LECTURE,
    // Status.OK);

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

    Optional<BriefFile> Optionalfile = filedao.findByFileName(fileName);

    if (Optionalfile.isEmpty()) {
    System.out.println("file not found");
    return;
    }
    BriefFile file = Optionalfile.get();
    String owner = file.getOwner();
   
    int id = file.getFileId();

    if (!owner.equals(logeduser)) {
    System.out.println("not allowed : not your file");
    return;
    }
    filedao.updateDroits(id,droit);

    }

    // // private String[] findFileRecord(String fileName) {
    // // try {
    // // Path path = Path.of("src\\main\\resources\\fileOwners.txt");
    // // if (!Files.exists(path))
    // // return null;

    // // List<String> lines = Files.readAllLines(path);
    // // for (String line : lines) {
    // // String[] parts = line.split(":");
    // // if (parts[1].equals(fileName)) {
    // // return parts;
    // // }
    // // }
    // // } catch (IOException e) {
    // // System.out.println("failed to open and read that file records ");

    // // }
    // // return null;
    // // }

    // private void updatePermition(String fileName, String newPermition) {
    // try {
    // Path path = Path.of("src\\main\\resources\\fileOwners.txt");

    // List<String> lines = Files.readAllLines(path);
    // List<String> updatedlines = new ArrayList<>();

    // for (String line : lines) {
    // String[] parts = line.split(":");
    // if (parts[1].equals(fileName)) {
    // updatedlines.add(parts[0] + ":" + parts[1] + ":" + newPermition);
    // } else {
    // updatedlines.add(line);
    // }
    // }
    // Files.write(path, updatedlines);
    // } catch (IOException e) {
    // System.out.println("could not update the permition" + e.getMessage());
    // }
    // }

}
