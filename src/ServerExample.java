import java.net.*;
import java.io.*;
import java.sql.SQLOutput;

public class ServerExample  extends Thread{
    private ServerSocket serverSocket;

    public ServerExample(int port ) throws IOException{
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
    }

    public void run(){
        while(true){
            try{
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();

                System.out.println("Connected to "+ server.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(server.getInputStream());

                System.out.println(in.readUTF());
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("Nice to Connect to "+ server.getLocalSocketAddress() + "! Good Example");
                server.close();
            }catch (SocketTimeoutException s ){
                System.out.println("Time out for This Socket!");
                break;
            }catch (IOException e ){
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) {
        int port = 1234;
        try{
            Thread t = new ServerExample(port);
            t.start();
        }catch(IOException e ){e.printStackTrace();}
    }
}
