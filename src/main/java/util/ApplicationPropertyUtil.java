package util;

import org.apache.log4j.Logger;
import ui_automation.uber.UberWorkerLoadHistory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

public class ApplicationPropertyUtil {
    private static final Properties properties = new Properties();
    private static final Logger LOGGER = Logger.getLogger(ApplicationPropertyUtil.class);

    static {
        try {
            InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
            properties.load(new InputStreamReader(Objects.requireNonNull(input), StandardCharsets.UTF_8));
            properties.put("minfin.address", "Центр, вул. Франка, початок Франка і Зеленої. Кратно 1000$");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties applicationProperty() {
        return properties;
    }

    public static String applicationPropertyGet(String propertyName) {
        String res = applicationProperty().getProperty(propertyName);
        if (res == null) {
            LOGGER.warn(propertyName + " requared in application.properties file");
        }
        return res;
    }


    public static Integer applicationPropertyGetInteger(String propertyName) {
        try {
            return Integer.parseInt(applicationPropertyGet(propertyName));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(applicationPropertyGet(key));
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }

    }

    public static Integer getInteger(String key, int defaultValue) {
        try {
            return Integer.parseInt(applicationPropertyGet(key));
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static long getLong(String key, long defaultValue) {
        try {
            return Integer.parseInt(applicationPropertyGet(key));
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
}
