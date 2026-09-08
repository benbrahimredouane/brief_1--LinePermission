package ma.youcode.lineperm.service;

import ma.youcode.lineperm.model.User;

public class UserService {

    public User createUser(String name , String code){

        User user = new User(name , code);

        return user;

    }
    
}
