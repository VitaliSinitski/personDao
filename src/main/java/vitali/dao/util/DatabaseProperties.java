package vitali.dao.util;

import java.util.ResourceBundle;

public class DatabaseProperties {

    private static final ResourceBundle resource = ResourceBundle.getBundle("database");
    public static final String URL = resource.getString("url");
    public static final String DRIVER = resource.getString("driver");
    public static final String USER = resource.getString("user");
    public static final String PASS = resource.getString("pass");
}
