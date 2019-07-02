package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

public class ApplicationPropertyUtil {
    private static final Properties properties = new Properties();

    static {
        try {
            InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
            properties.load(new InputStreamReader(Objects.requireNonNull(input), StandardCharsets.UTF_8));
            properties.put("minfin.address", "Центр, вул. Франка, початок Франка і Зеленої");
            properties.put("remote", System.getProperty("remote", "false"));
            properties.put("time.delta.hours", System.getProperty("time.delta.hours", "0"));
            System.out.println("Properties : " + properties.keySet());
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
