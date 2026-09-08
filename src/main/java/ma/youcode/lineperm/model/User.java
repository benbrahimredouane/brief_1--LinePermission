package ma.youcode.lineperm.model;

public class User {

    private String name;
    private String code;

    public User(String name , String code){
        this.name = name;
        this.code = code;
    }

    public String getName(){
        return name;
    }
    
}
