public class Request {
    String requestLine  = null;

    String method       = null;
    String URI          = null;
    String HTTPVersion  = null;

    String headers      = null;
    String body         = null;

    public Request(String requestLine, String method, String URI, String HTTPVersion, String headers, String body) {
        this.requestLine    = requestLine;
        this.method         = method;
        this.URI            = URI;
        this.HTTPVersion    = HTTPVersion;
        this.headers        = headers;
        this.body           = body;
    }
    
    public Request() {
        this.requestLine    = "";
        this.method         = "";
        this.URI            = "";
        this.HTTPVersion    = "";
        this.headers        = "";
        this.body           = "";
    }

    public String toString() {
        String fullRequest = "";
        fullRequest += requestLine;
        fullRequest += headers;
        fullRequest += "\r\n";
        fullRequest += body;
        return fullRequest;
    }

    // Useful for HTTP error codes.
    // Method of the request is modified to be a GET to return the error page.
    public void updateURI(String newURI) {
        this.URI = newURI;
        this.method = "GET";
    }

    public String getMethod() {
        return method;
    }
    public String getURI() {
        return URI;
    }
    public String getHTTPVersion() {
        return HTTPVersion;
    }
    public String getHeaders() {
        return headers;
    }
    public String getBody() {
        return body;
    }
}
