package automaticReportGenerator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfProperties {
    protected static FileInputStream fileInputStream;
    protected static Properties PROPERTIES;

    static {
        try {
            fileInputStream = new FileInputStream("configuration.properties");
            PROPERTIES = new Properties();
            PROPERTIES.load(fileInputStream);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла конфигурации: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (fileInputStream != null)
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException nullEx) {
                    System.out.println("Нет файла конфигурации: " + nullEx.getMessage());
                }
        }
    }


    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}

