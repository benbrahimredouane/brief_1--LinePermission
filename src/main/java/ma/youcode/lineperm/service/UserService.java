package ma.youcode.lineperm.service;

import ma.youcode.lineperm.dao.UserDao;
import ma.youcode.lineperm.model.User;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;

public class UserService {

    private static boolean isAuth = false;
    private static User currentUser;

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean createUser(String name, String code) {

        if (userDao.findByUserName(name).isPresent()) {
            System.out.println("this user allready exists");
            return false;
        }

        String hashed = BCrypt.hashpw(code, BCrypt.gensalt());

        User user = new User(name, hashed);

        userDao.save(user);

        currentUser = userDao.findByUserName(name).orElse(user);
        System.out.println("Welcome " + currentUser.getName());
        isAuth = true;
        return true;

    }

    public boolean login(String name, String code) {

        Optional<User> maybeUser = userDao.findByUserName(name);

        if (maybeUser.isEmpty()) {
            System.out.println("this user not exists");
            return false;
        }
        User user = maybeUser.get();
        if (!BCrypt.checkpw(code, user.getCode())) {
            System.out.println("password is incorrect:");
            return false;
        }

        currentUser = user;
        isAuth = true;
        return true;

    }

    public void delete(User user) {

        userDao.delete(user);

    }

    public void logout() {
        // System.out.println(isAuth);
        if (!isAuth) {
            System.out.println("that possible only if you are connected");
            return;
        }
        System.out.print("loging out ... \n");
        isAuth = false;
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isAuth() {
        return isAuth;
    }

}
