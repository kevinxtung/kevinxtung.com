public enum StatusCode {
    // 2xx Successful
    OK(200, "OK"),
    
    // 3xx Redirection
    REDIRECT(301, "Moved Permanently"),
    
    // 4xx Client Error
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    
    // 5xx Server Error
    INTERNAL_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented");

    private final int code;
    private final String description;

    StatusCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String toString() {
        return "" + this.code + " " + this.description;
    }

    public int getCode() {
        return this.code;
    }
}