import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class HTTPThread implements Runnable {
    static final double HTTPVERSION = 1.0;  
    static final String PATH = ".";         // The prefix for paths of requested files. Possible security flaw?

    static ArrayList<String> supportedMethods   = new ArrayList<>(Arrays.asList("GET"));
    static ArrayList<String> unsupportedMethods = new ArrayList<>(Arrays.asList("POST", "HEAD", "PUT", "DELETE", "CONNECT", "OPTIONS", "TRACE", "PATCH"));
    
    private Socket connectionSocket;                    // Connection socket to client.
    private BufferedReader requestReader;               // Reader of the request message from client.
    private BufferedOutputStream responseDataStream;    // Buffer stream going out to client.
    
    public HTTPThread(Socket incomingSocket) {
        connectionSocket = incomingSocket;
        try {
            requestReader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));   // Sets up socket request stream.
            responseDataStream = new BufferedOutputStream(connectionSocket.getOutputStream());              // Sets up socket response stream.
        }
        catch (Exception e) {
            SocketServer.timestamp(e);
        }
    }
    
    @Override
    public void run() {
        try {
            Request request = parseRequest();   // Parses request from request reader and returns request data structure.
            logRequest(request);
            RequestChecker.validate(request);
            buildResponse(request, StatusCode.OK);
        }
        catch (ResponseException re) {
            buildResponse(re);
        }
        catch (Exception e) {
            buildResponse(new ResponseException(StatusCode.INTERNAL_ERROR, e, new Request()));
        }
    }
    
    private void logRequest(Request request) {
        String formatted = "Request recieved.\n";
        formatted += "================================================\n";
        formatted += "BEGIN REQUEST\n";
        formatted += request.toString();
        formatted += "END REQUEST\n";
        formatted += "================================================\n";
        SocketServer.timestamp(formatted);
    }

    //===========================
    // parseRequest()
    //
    //  parseRequest processes an HTTP request and delivers the result to buildGETResponse().
    //  It works by splitting the startrequestLine of the request into 3, and examining the method and target.
    //  After evaluating the request, the function passes a statusCode, statusText, the served file path,
    //  and the HTTP version to the response generator.
    //
    private Request parseRequest() throws ResponseException {
        try {
            String requestLine          = requestReader.readLine();
            String splitLine[]          = requestLine.split(" ");
            String requestMethod        = splitLine[0];
            String requestURI           = splitLine[1];
            String requestHTTPVersion   = splitLine[2];
            String requestHeaders       = "";
            String requestBody          = "";
            
            String line = null;
            
            /*// Fill in the request header
            while (line != "\r\n") {
                requestHeaders += line + "\n";
                line = requestReader.readLine();
            }
            // Fill in the request body.
            while (line != null) {
                requestBody += line + "\n";
                line = requestReader.readLine();
            }
            */

            return new Request(requestLine, requestMethod, requestURI, requestHTTPVersion, requestHeaders, requestBody);
        }
        catch (Exception e) {
            throw new ResponseException(StatusCode.BAD_REQUEST, e, new Request()); // Bad Request
        }
    }

    private void buildResponse(Request request, StatusCode statusCode) throws ResponseException {
        String method = request.getMethod();

        switch(method) {
            case "GET":
                buildGETResponse(request, statusCode);
                break;
            default:
                if (unsupportedMethods.contains(method)) {
                    throw new ResponseException(StatusCode.NOT_IMPLEMENTED, request);
                }
                if (!supportedMethods.contains(method)) {
                    throw new ResponseException(StatusCode.BAD_REQUEST, request);
                }
        }
    }
    private void buildResponse(ResponseException re) {
        try {
            StatusCode statusCode = re.getStatusCode();
            SocketServer.timestamp("Error code " + re.getStatusCode().toString());

            switch(statusCode) {
                case MOVED_PERMANENTLY:
                case FOUND:
                    String redirectURI = re.getMessage();
                    Request request = re.getRequest();
                    request.updateURI(redirectURI);
                    buildResponse(request, statusCode);
                    break;
                
                case BAD_REQUEST:
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND: 

                case INTERNAL_ERROR:
                case NOT_IMPLEMENTED:

                default:
                    SocketServer.timestamp("reached default. code was" + statusCode.toString());
            }
        }
        catch (Exception e) {

        }
    }

    private void buildGETResponse(Request request, StatusCode statusCode) throws ResponseException {
        try {
            String target = '.' + request.getURI();
            if (true /*its a non api uri*/) {
                // do a regular get
            }
            else {
                // invoke api things to get a json
            }

            File targetFile = new File(target);
            int targetLength = (int)targetFile.length();
            byte[] targetData = fileToBytes(targetFile, targetLength);
            String end = "\r\n";    // Carriage return

            // RESPONSE STRINGS - Self Explanatory
            // Each requestLine also has the variable end added on, which signifies a carriage return and newrequestLine.
            String statusLine = "HTTP/" + HTTPVERSION + " " + statusCode.toString() + end;    // Variables from function parameters.
            SocketServer.timestamp(statusLine);
            String dateLine = "Date: " + new Date() + end;
            String contentTypeLine = "Content-type: " + getMIMEType(target) + end; // Content type determined by function with appropriate MIME typing.
            String contentLengthLine = "Content-length: " + targetLength + end;   // Length taken from previous stored value above.

            // header is the concatenation of the response strings, along with two newrequestLines to indicate
            // that the header has ended and the content is next.
            String header = statusLine + dateLine + contentTypeLine + contentLengthLine + end;
            
            responseDataStream.write(header.getBytes(), 0, header.length());    // Write the header.
            responseDataStream.write(targetData, 0, targetLength);              // Write the target data.
            responseDataStream.flush();                                         // Send the data to the client.

            responseDataStream.close();                                         // Close the connection.
            connectionSocket.close();                                           // Close the socket completely.
        }
        catch (IOException ioe) {
            throw new ResponseException(StatusCode.INTERNAL_ERROR, request);
        }
    }


    //===========================
    //  getMIMEType FUNCTION
    //
    //  Takes in a String filename.
    //  Uses the extension of the filename to determine
    //  an appropriate MIME type.
    //  Returns the correct type as a string.
    //
    private String getMIMEType(String filename) {
        String targetExtension = null;  // Extension of filename.
        String type = null;             // MIME type.

        int i = filename.lastIndexOf('.');      // Find the last '.' in the filename.
        if (i > 0) {                // Avoid any weird errors.
            targetExtension = filename.substring(i+1);  // Set targetExtension to the extension, with no dot.
        }
        else {
            targetExtension = "";   // If we do have weird errors, avoid a null pointer.
        }

        // FOR EACH EXTENSION
        // IF IT IS THAT EXTENSION
        // SET THE APPROPRIATE MIME TYPE
        if (targetExtension.equals("html")) {
            type = "text/html"; 
        }
        else if (targetExtension.equals("css")) {
            type = "text/css"; 
        }
        else if (targetExtension.equals("png")) {
            type = "image/png";
        }
        else if (targetExtension.equals("ico")) {
            type = "image/vnd.microsoft.icon";
        }
        else if (targetExtension.equals("json")) {
            type = "application/json";
        }
        else if (targetExtension.equals("js")) {
            type = "text/javascript";
        }
        else {
            // THIS IS A DEFAULT MIME TYPE FOR UNKNOWN EXTENSIONS.
            type = "application/octet-stream";
        }

        return type;
    }

    //===========================
    //  fileToBytes FUNCTION
    //
    //  Takes in a File and the file's length.
    //  Returns a bytearray representation of the file.
    //
    private byte[] fileToBytes(File file, int length) throws IOException {
		byte[] fileData = new byte[length]; // Create the array of the file's size.

		try {
		    FileInputStream fileStream = new FileInputStream(file); // Make a stream out of the file.
			fileStream.read(fileData);                              // Read the stream to the byte array.
			fileStream.close();                                     // Close the stream.
		}
        catch (Exception e) {
            SocketServer.timestamp(e);  // Notify of any exceptions occuring.
        }

		return fileData;
	}
}
