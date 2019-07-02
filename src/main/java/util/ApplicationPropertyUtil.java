package util;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class ApplicationPropertyUtil {
    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties applicationProperty() {
        return properties;
    }

    public static String applicationPropertyGet(String propertyName) {
        return applicationProperty().getProperty(propertyName);
    }
}
