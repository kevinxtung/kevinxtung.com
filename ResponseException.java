class ResponseException extends Exception {
    private StatusCode code;
    private Request request;

    ResponseException(StatusCode code, Request request) {
        super(code.toString());
        this.code = code;
        this.request = request;
    }

    // Here, an exception was thrown before a request object was able to be made.
    ResponseException(StatusCode code, Throwable e, Request request) {
        super(code.toString(), e);
        this.code = code;
        this.request = request;
    }

    // Message param used for path in 3XX redirects.
    ResponseException(StatusCode code, String message, Request request) {
        super(message);
        this.code = code;
        this.request = request;
    }

    public StatusCode getStatusCode() {
        return code;
    }
    public Request getRequest() {
        return request;
    }
}