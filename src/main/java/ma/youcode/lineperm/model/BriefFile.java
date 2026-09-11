package ma.youcode.lineperm.model;

public class BriefFile {

    private String owner;
    private String permition;

    public BriefFile(String owner) {

        this.owner = owner;

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

}
