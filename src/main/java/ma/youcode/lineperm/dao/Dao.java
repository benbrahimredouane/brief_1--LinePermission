package ma.youcode.lineperm.dao;

import java.util.Optional;

public interface Dao<T> {
    public void save(T o);
    public Optional<T> findById(int id);
    public void delete(T o);
    
}
