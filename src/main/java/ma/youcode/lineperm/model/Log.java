package ma.youcode.lineperm.model;

import java.util.Date;

public class Log {

    public enum Action {
        LECTURE,
        ECRITURE
    }

    public enum Status {
        OK,
        REFUSE
    }

    private Date date;
    private String ownerFile;
    private String fileName;
    private Action action;
    private Status status;

    public Log(Date date, String ownerFile, String fileName, Action action, Status status) {
        this.date = date;
        this.ownerFile = ownerFile;
        this.fileName = fileName;
        this.action = action;
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
