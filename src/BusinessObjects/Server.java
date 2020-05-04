/**
 * SERVER                                                   February 2019 DL 08/03/19
 *
 * Server accepts client connections, creates a ClientHandler to handle the
 * Client communication, creates a socket and passes the socket to the handler,
 * runs the handler in a separate Thread.
 *
 *
 * The handler reads requests from clients, and sends replies to clients, all in
 * accordance with the rules of the protocol. as specified in
 * "ClientServerBasic" sample program
 *
 * The following PROTOCOL is implemented:
 *
 * If ( the Server receives the request "Time", from a Client ) then : the
 * server will send back the current time
 *
 * If ( the Server receives the request "Echo message", from a Client ) then :
 * the server will send back the message
 *
 * If ( the Server receives the request it does not recognize ) then : the
 * server will send back the message "Sorry, I don't understand"
 *
 * This is an example of a simple protocol, where the server's response is based
 * on the client's request.
 *
 *
 */
package BusinessObjects;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Server
{

    public static void main(String[] args)
    {
        Server server = new Server();
        server.start();
    }

    public void start()
    {
        try
        {
            ServerSocket ss = new ServerSocket(8080); // set up ServerSocket to listen for connections on port 8080

            System.out.println("Server: Server started. Listening for connections on port 8080...");

            int clientNumber = 0; // a number for clients that the server allocates as clients connect

            while (true) // loop continuously to accept new client connections
            {
                Socket socket = ss.accept(); // listen (and wait) for a connection, accept the connection,
                // and open a new socket to communicate with the client
                clientNumber++;

                System.out.println("Server: Client " + clientNumber + " has connected.");

                System.out.println("Server: Port# of remote client: " + socket.getPort());
                System.out.println("Server: Port# of this server: " + socket.getLocalPort());

                Thread t = new Thread(new ClientHandler(socket, clientNumber)); // create a new ClientHandler for the
                // client,
                t.start(); // and run it in its own thread

                System.out.println("Server: ClientHandler started in thread for client " + clientNumber + ". ");
                System.out.println("Server: Listening for further connections...");
            }
        } catch (IOException e)
        {
            System.out.println("Server: IOException: " + e);
        }
        System.out.println("Server: Server exiting, Goodbye!");
    }

    public class ClientHandler implements Runnable // each ClientHandler communicates with one Client
    {

        BufferedReader socketReader;
        PrintWriter socketWriter;
        Socket socket;
        int clientNumber;

        public ClientHandler(Socket clientSocket, int clientNumber)
        {
            try
            {
                InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
                this.socketReader = new BufferedReader(isReader);

                OutputStream os = clientSocket.getOutputStream();
                this.socketWriter = new PrintWriter(os, true); // true => auto flush socket buffer

                this.clientNumber = clientNumber; // ID number that we are assigning to this client

                this.socket = clientSocket; // store socket ref for closing

            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }

        @Override
        public void run()
        {
            String message;
            try
            {
                while ((message = socketReader.readLine()) != null)
                {
                    System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + message);

                    JsonReader reader = Json.createReader(socketReader);
                    JsonObject object = reader.readObject();
                    String value = object.getString("PacketType");

                    String fileName = "vehicles.csv";

                    if (value.equalsIgnoreCase("Heartbeat"))
                    {
                        System.out.println("Starting Request ...");

                        // Now construct a response, and send it back to the Client
                        JsonObject jsonRootObject = Json.createObjectBuilder()
                                .add("PacketType", "Heartbeat Response")
                                .build();

                        String response = jsonRootObject.toString(); // JSON Request [String Format]
                        socketWriter.println(response); // Write this to the socket, Send back over to the Client
                        System.out.println("Server Response: " + response);
                    } else if (value.equalsIgnoreCase("GetRegisteredVehicles"))
                    {
                        System.out.println("Reading from " + fileName);
                        Set set = new HashSet<String>();

                        try
                        {
                            Scanner sc = new Scanner(new File(fileName));

                            while (sc.hasNext())
                            {
                                String reg = sc.next();
                                set.add(reg);
                            }

                            String jsonString = "{" // root object
                                    + "\"vehicles\":";   // set up the array (list)
                            jsonString
                                    += "\"" + set + "\",";
                            jsonString
                                    += "}";  // close the array and close the object 

                            System.out.println(jsonString);
                            sc.close();
                        } catch (IOException e)
                        {
                            System.out.println("\n+-----------------------------------------------------+");
                            System.out.println("|              Database Load Unsuccessful             |");
                            System.out.println("|            Check File Name / Path / Type            |");
                            System.out.println("+-----------------------------------------------------+");
                        }

                    } else
                        socketWriter.println("I'm sorry I don't understand :(");

                }

                socket.close();

            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
            System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
        }
    }

    public static Set loadRegistrationDatabase(String fileName)
    {
        System.out.println("Reading from " + fileName);
        Set set = new HashSet<String>();

        try
        {
            Scanner sc = new Scanner(new File(fileName));

            while (sc.hasNext())
            {
                String reg = sc.next();
                set.add(reg);
            }

            String jsonString = "{" // root object
                    + "\"vehicles\":";   // set up the array (list)
            jsonString
                    += "\"" + set + "\",";
            jsonString
                    += "}";  // close the array and close the object 

            System.out.println(jsonString);
            sc.close();

        } catch (IOException e)
        {
            System.out.println("\n+-----------------------------------------------------+");
            System.out.println("|              Database Load Unsuccessful             |");
            System.out.println("|            Check File Name / Path / Type            |");
            System.out.println("+-----------------------------------------------------+");
        }

        return set; // of valid registrations
    }
}
