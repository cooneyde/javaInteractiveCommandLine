package propertiesLoader;


import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesLoader {


    Properties prop = new Properties();
    public PropertiesLoader(String path) {
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            prop.load(fileInputStream);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Properties getProperties() {
        return prop;
    }
}
