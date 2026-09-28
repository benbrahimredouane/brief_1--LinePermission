package ma.youcode.lineperm.model;

public class BriefFile {

    private int fileId;
    private String owner;
    private String permition;
    private String fileName;

    public BriefFile(String owner, String fileName, String permition) {

        this.owner = owner;
        this.permition = permition;
        this.fileName = fileName;

    }

    public BriefFile(int fileId, String owner, String fileName, String permition) {
        this.fileId = fileId;
        this.owner = owner;
        this.permition = permition;
        this.fileName = fileName;

    }
    public int getFileId() {
        return fileId;
    }

    public String getOwner() {
        return this.owner;

    }

    public String getPermition() {
        return permition;
    }

    public void setPermition(String permition) {
        this.permition = permition;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
