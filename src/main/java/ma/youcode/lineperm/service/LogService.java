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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import ma.youcode.lineperm.model.Log;
import ma.youcode.lineperm.model.Log.*;

public class LogService {

    static List<Log> logs = new ArrayList<>();

    public static void loadLogs() {
        logs.clear();
        try {
            Path path = Path.of("src\\main\\resources\\actions.log");
            if (!Files.exists(path)) {
                System.out.println("file not found");
                return;
            }
            List<String> lines = Files.readAllLines(path);
            // lines.forEach(line -> logs.add(line));
            for (String line : lines) {
                String[] parts = line.split(";", 6);

                // date, time, ownerFile, fileName, action, status
                // 2026-09-15;17:33;redouane;test1.txt;LECTURE;REFUSE

                LocalDate date = LocalDate.parse(parts[0]);
                LocalTime time = LocalTime.parse(parts[1]);
                Action action = Action.valueOf(parts[4]);
                Status status = Status.valueOf(parts[5]);

                logs.add(new Log(date, time, parts[2], parts[3], action, status));

            }

        } catch (IOException e) {
            System.out.println("ereur loading files");

        }

    }

    public void addLog(LocalDate date, LocalTime time, String ownerFile, String fileName,
            Action action, Status status) {

        Log log = new Log(date, time, ownerFile, fileName, action, status);

        StringBuilder Log = new StringBuilder();
        Log.append(date.now());
        Log.append(";");
        Log.append(time.now().format(DateTimeFormatter.ofPattern("HH:mm")));
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
        logs.add(log);

    }

    public void countallactions() {
        int c = logs.size();
        System.out.println("total actions :" + c);

    }

    public void countrefusedactions() {
        System.out
                .println("number refuse: " + logs.stream().filter(log -> log.getStatus() == Log.Status.REFUSE).count());

    }

    public void usersdisctint() {
        System.out.println("users disctanct : " + logs.stream().map(log -> log.getOwnerFile()).distinct().toList());
    }

    public void actionsPerUser() {

        Map<String, Long> countByactions = logs.stream()
                .collect(Collectors.groupingBy(log -> log.getOwnerFile(), Collectors.counting()));

        countByactions.forEach((name, counter) -> System.out.println(name + "=" + counter));

    }

    public void top3files() {

    }

    public void accesrefusedfromtheuser() {

    }

    public void userwithmostactivites() {

        Map<String, Long> users = logs.stream()
                .collect(Collectors.groupingBy(log -> log.getOwnerFile(), Collectors.counting()));

        Optional<Map.Entry<String, Long>> topUser = users.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        topUser.ifPresent(e -> System.out.println("top user:" + e.getKey() + "with" + e.getValue() + "actions"));

    }

    public void actionsbytype() {

        Map<Action, Long> acc = logs.stream()
                .collect(Collectors.groupingBy(log -> log.getAction(), Collectors.counting()));

        acc.forEach((ac, counter) -> System.out.println(ac + "=" + counter));

    }

}
