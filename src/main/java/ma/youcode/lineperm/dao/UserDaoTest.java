package ma.youcode.lineperm.dao;

import ma.youcode.lineperm.model.User;

public class UserDaoTest extends UserDao {
    public static void main(String[] args){
        UserDao userdao = new UserDao();

        User user = new User("alin","z,vozjr");

        userdao.save(user);
    }

    
}
