import java.io.File;

public class RequestChecker {
    // RequestChecker class will receive a path to a file through its
    // validate function. It will check for the file's existience and
    // it will check for any redirects for that specific path.

    // Path format should be:
    //  "/"
    //  "/img/funny.png"
    //  where '/' is the root directory of your webserver.

    public static void validate(Request request) throws ResponseException {

        String path = request.getURI();
        String newpath = redirect(path);
        if (!newpath.equals(path)) {
            throw new ResponseException(StatusCode.MOVED_PERMANENTLY, newpath, request);
        }
        if (!fileExists(path)) {
            throw new ResponseException(StatusCode.NOT_FOUND, request);
        }
    
    }
    
    private static String redirect(String path) {
        switch(path) {
            case "/":                  return "/html/index.html";
            default:                    return path;
        }
    }

    private static boolean fileExists(String path) {
        File file = new File('.' + path);
        if (file.exists() && !file.isDirectory()) {
            return true;
        }
        return false;
    }
}
