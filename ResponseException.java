class ResponseException extends Exception {
    private StatusCode code;
    
    ResponseException(StatusCode code) {
        super(code.toString());
        this.code = code;
    }

    ResponseException(StatusCode code, Throwable e) {
        super(code.toString(), e);
        this.code = code;
    }

    public StatusCode getStatusCode() {
        return code;
    }
}