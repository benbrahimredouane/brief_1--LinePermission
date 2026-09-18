package ma.youcode.lineperm.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Log {

    public enum Action {
        LECTURE,
        ECRITURE,
        DELETE
    }

    public enum Status {
        OK,
        REFUSE
    }

    private LocalDate date;
    private LocalTime time;
    private String ownerFile;
    private String fileName;
    private Action action;
    private Status status;

    public Log(LocalDate date, LocalTime time, String ownerFile, String fileName, Action action, Status status) {
        this.date = date;
        this.time = time;
        this.ownerFile = ownerFile;
        this.fileName = fileName;
        this.action = action;
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getOwnerFile() {
        return ownerFile;
    }

    public String getFileName() {
        return fileName;
    }

    public Status getStatus() {
        return status;
    }

    public Action getAction() {
        return action;
    }

}
