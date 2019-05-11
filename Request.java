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
    
    public String toString() {
        String fullRequest = "";
        fullRequest += requestLine;
        fullRequest += headers;
        fullRequest += "\r\n";
        fullRequest += body;
        return fullRequest;
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
