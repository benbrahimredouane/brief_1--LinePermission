package ma.youcode.lineperm.service;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import ma.youcode.lineperm.model.Log;
import ma.youcode.lineperm.model.Log.*;

public class LogService {

    static List<String> logs = new ArrayList<>();

    public static void loadLogs() {
        try {
            Path path = Path.of("src\\main\\resources\\actions.log");
            if (!Files.exists(path)) {
                System.out.println("file not found");
            }
            List<String> lines = Files.readAllLines(path);
            lines.forEach(line -> logs.add(line));

        } catch (IOException e) {
            System.out.println("ereur loading files");

        }

    }

    public void addLog(String ownerFile, String fileName,
            Action action, Status status) {

        // Log log = new Log(
        // LocalTime.now(),
        // ownerFile,
        // fileName,
        // Log.Action.LECTURE,
        // Log.Status.OK);

        StringBuilder Log = new StringBuilder();
        Log.append(LocalDate.now());
        Log.append(";");
        Log.append(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        Log.append(";");

        Log.append(ownerFile);
        Log.append(";");

        Log.append(fileName);
        Log.append(";");

        Log.append(action);
        Log.append(";");

        Log.append(status);
        Log.append(System.lineSeparator());

        try {
            FileWriter writer = new FileWriter("src\\main\\resources\\actions.log", true);
            writer.write(Log.toString());
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found ");
        } catch (IOException e) {
            System.out.println("something was wrong file not modifiyed");

        }
        logs.add(Log.toString());

    }

    public void countallactions() {
        int c = logs.size();
        System.out.println("total actions :" + c);

    }
    public void countrefusedactions(){
        System.out.println("number refuse: " + logs.stream().filter(log -> log.contains("REFUSE")).count());
       
    }

}
