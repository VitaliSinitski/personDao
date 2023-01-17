package vitali.dao.dao;

import java.sql.SQLException;

public interface Dao<T> {

    T save(T t) throws SQLException;
    T get(int id) throws SQLException;
    void update(T t) throws SQLException;
    int delete(int id) throws SQLException;





}
