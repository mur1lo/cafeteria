package cafeteria.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CarregarConfig {

    private Properties properties;

    public CarregarConfig(String filePath) {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}


