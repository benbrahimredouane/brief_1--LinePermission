package ma.youcode.lineperm.model;

public class User {

    private int UserId;
    private String name;
    private String code;

    public User(String name, String code) {

        this.name = name;
        this.code = code;
    }

    public User(int UserId, String name, String code) {
        this.UserId = UserId;
        this.name = name;
        this.code = code;
    }

    public int getUserId() {
        return UserId;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

}
