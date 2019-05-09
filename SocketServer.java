import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class SocketServer { 
    private static final int DEFAULTPORT = 8000;    // Constant for default port to listen on.
    
    public static void main(String argv[]) throws Exception {
        Integer portNumber = getPort(argv);
        ServerSocket publicSocket = new ServerSocket(portNumber);
        
        // Forever listen for connections on our socket and handle them.
        while (true) {
            HTTPThread connection = new HTTPThread(publicSocket.accept());      // Accept a new connection and create an instance of HTTPThread with the socket.
            Thread connectionThread = new Thread(connection);                   // Create a new thread of the instanced class.
            connectionThread.start();                                           // Start the thread from the run();
        }
    }

    // Chooses a port to start listening on, using argument vector in case user tried to specify port.
    private static Integer getPort(String argv[]) {
        Integer returnPort = -1;
        if (argv.length > 0) {
            try {
                returnPort = Integer.parseInt(argv[0]);
                if (1024 > returnPort || returnPort > 65535) {  // Only allows valid, not well known ports.
                    throw new Exception("Not a port!");
                }
            }
            catch (Exception e) {
                returnPort = DEFAULTPORT;
                timestamp("Unable to parse given port number, starting on port " + returnPort + ".");
            }
        }
        else {  // If no port is specified, we start on the constant DEFAULTPORT.
            returnPort = DEFAULTPORT;
            timestamp("No port number specified, starting on port " + returnPort + ".");
        }
        return returnPort;
    }

    public static void timestamp(String message) {
        System.out.print("[" + new Date() + "] ");  // Prints the current time at function invocation.
        System.out.println(message);                // Prints the messsage.
    }

    public static void timestamp(Exception e) {
        System.out.print("[" + new Date() + " EXCEPTION] ");
        System.out.println(e.toString());
    }
}
