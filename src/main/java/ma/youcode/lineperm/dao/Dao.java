package ma.youcode.lineperm.dao;

import ma.youcode.lineperm.model.User;

public interface Dao<T> {
    public void save(User user);
    public void findById(int id);
    public void delete(User user);
    
}
