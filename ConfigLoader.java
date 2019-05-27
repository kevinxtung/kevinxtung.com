import java.io.FileReader;
import java.util.Properties;
import java.io.IOException;

public final class ConfigLoader {

    private static Properties properties;
    
    static {
        try {
            properties = new Properties();
            FileReader configFileReader = new FileReader(Constants.PATH_TO_CONFIG);  
            properties.load(configFileReader); 
        }
        catch (Exception e) {
            SocketServer.timestamp(e);
        }
    }
    
    public static String getConfiguration(String configName) {
        return properties.getProperty(configName);
    }
}
