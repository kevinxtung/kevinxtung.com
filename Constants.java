import java.util.ArrayList;
import java.util.Arrays;

public class Constants {
    public static final String PATH_TO_CONFIG       = "./CONFIG.txt";
    public static final String PATH_TO_WEBSERVER    = "./root";
    public static final double HTTP_VERSION         = 1.0;

    public static final ArrayList<String> SUPPORTED_METHODS   = new ArrayList<>(Arrays.asList("GET"));
    public static final ArrayList<String> UNSUPPORTED_METHODS = new ArrayList<>(Arrays.asList("POST", "HEAD", "PUT", "DELETE", "CONNECT", "OPTIONS", "TRACE", "PATCH"));
}