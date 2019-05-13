import java.io.File;

public class RequestValidator {
    // RequestValidator class will receive a path to a file through its
    // validate function. It will check for the file's existience and
    // it will check for any redirects for that specific path.

    // Path format should be:
    //  "./"
    //  "./img/funny.png"
    //  where '.' is the root directory of your webserver.

    public static String validate(String path) throws ResponseException {
        path = redirect(path);
        if (!fileExists(path)) {
            throw new ResponseException(StatusCode.NOT_FOUND);
        }
        return path;
    }
    
    private static String redirect(String path) {
        switch(path) {
            case "./":                  return "./html/index.html";
            default:                    return path;
        }
    }

    private static boolean fileExists(String path) {
        File file = new File(path);
        if (file.exists() && !file.isDirectory()) {
            return true;
        }
        return false;
    }
}
