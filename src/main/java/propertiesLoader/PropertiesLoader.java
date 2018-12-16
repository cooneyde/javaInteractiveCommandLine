package propertiesLoader;


import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesLoader {


    private final Properties prop = new Properties();

    public PropertiesLoader(String path) {
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            prop.load(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Properties getProperties() {

        return prop;
    }
}
