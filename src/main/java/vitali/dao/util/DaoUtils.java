package vitali.dao.util;

import vitali.dao.annotation.MyColumn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.*;

public class DaoUtils {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DatabaseProperties.URL,
                    DatabaseProperties.USER, DatabaseProperties.PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void close(AutoCloseable auto){
        if (auto != null) {
            try {
                auto.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static List<String> tableColumns (Class c) {
        Field[] fields = c.getDeclaredFields();
        List<String> list = new ArrayList<>();
        for (Field f : fields) {
            MyColumn myColumn = f.getAnnotation(MyColumn.class);
            list.add(myColumn.name());
        }
        return list;
    }




}
