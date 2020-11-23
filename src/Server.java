import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.Random;

public class Server {
    public static final int PORT = 2000;

    //踢著個Server()
            public static void main(String[] args) {
                new Server();
            }

    //Srver() throw exception 開始listening client, 同踢著個clientHandler
            public Server() {
                ServerSocket serverSocket = null;
                try {
                    // create a server socket
                    serverSocket = new ServerSocket(PORT);
                    while (true) {
                        // listen for a new connection request
                        Socket clientSocket = serverSocket.accept();
                        // create a new thread for handling the request
                        new Server.clientHandler(clientSocket).start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                }


    //行multi Thread 嘅 clientHandler
        class clientHandler extends Thread {
            private final Socket socket;

            public clientHandler(Socket s) {
                socket = s;
            }

            public void run() {
                int answer, guessCount, guessNumber = 0;
                while (true) {
                    try (socket;
                         // Create an output stream to send data to client
                         DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                         // Create an input stream to receive data client
                         DataInputStream in = new DataInputStream(socket.getInputStream())) {

                        // handle client request
                        // generate random number
                        Random generator = new Random();
                        answer = generator.nextInt(100) + 1;

                        // print the number (on server side) for debugging (use System.out.println)
                        System.out.println(answer);

                        // initialize guess count
                        int guess_count = 0;

                        // send a string to the client using **out**, tell the client to start playing
                        String StartPlay = "Guess a number between 0 and 99.";
                        out.writeUTF(StartPlay);

                        // out.flush() can be used to send immediately
                        out.flush();

                        // note: you may use \r\n at the end of each String to tell client to print a new line
                        String newLine = "\n";
                        out.writeUTF(newLine);
                        out.flush();

                        // repeatedly get client's input by **in**
                        BufferedReader input = new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));
                        guessNumber = Integer.parseInt(input.readLine());
                        System.out.println(guessNumber);
                        guessCount = 0;
                        //checking the guessnumber
                        if (guessNumber == answer) {
                            out.writeUTF("Bingo! The number is " + answer + ". You made it in " + guessCount + " attempts.”");
                            socket.close();
                        } else if (guessNumber < answer) {
                            out.writeUTF("Your guess is too low.");
                            guessCount++;

                        } else if (guessNumber > answer) {
                            out.writeUTF("Your guess is too low.");
                            guessCount++;
                        }


                        // update guess count and response using out (exit the loop when correct)

                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
         }
}