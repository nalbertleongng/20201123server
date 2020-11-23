
import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MultiThreadSocketServer {

    public static final int PORT = 54321;

    public static void main(String[] args) {
        new MultiThreadSocketServer();
    }

    public MultiThreadSocketServer() {
        ServerSocket serverSocket = null;
        try {
            // create a server socket
            serverSocket = new ServerSocket(PORT);
            while (true) {
                // listen for a new connection request
                Socket clientSocket = serverSocket.accept();
                // create a new thread for handling the request
                new Handler(clientSocket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    // Inner Class
    class Handler extends Thread {
        private final Socket socket;

        public Handler(Socket s) {
            socket = s;
        }

        public void run() {
            try (socket;
                 // Create an output stream to send data to client
                 var out = new DataOutputStream(socket.getOutputStream());
                 // Create an input stream to receive data client
                 var in = new DataInputStream(socket.getInputStream())) {
                // ... handle client request
                double radius = in.readDouble();
                double area = Math.PI * radius * radius;
                out.writeDouble(area);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
