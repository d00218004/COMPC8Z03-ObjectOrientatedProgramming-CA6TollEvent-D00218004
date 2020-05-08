/** *****************************************************************************
 * Object-Orientated Programming CA6 | Client-Server Toll System | CA Value: 35%
 * Author: Matthew Waller | D00218004
 ***************************************************************************** */
package BusinessObjects;

import DAOs.MySqlTollEventDao;
import DAOs.TollEventDaoInterface;
import DTOs.TollEvent;
import Exceptions.DaoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
            String fileName = "vehicles.csv";
            try
            {
                while ((message = socketReader.readLine()) != null)
                {
                    System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + message);

                    JsonReader reader = Json.createReader(socketReader);
                    JsonObject object = reader.readObject();
                    String value = object.getString("PacketType");

                    if (value.equalsIgnoreCase("Heartbeat"))
                    {
                        System.out.println("Starting Request ...");

                        // Now construct a response, and send it back to the Client
                        JsonObject jsonRootObject = Json.createObjectBuilder()
                                .add("PacketType", "Heartbeat Response")
                                .build();

                        String response = jsonRootObject.toString(); // JSON Request [String Format]
                        socketWriter.println(response); // Write this to the socket, Send back over to the Client
                    } else if (value.equalsIgnoreCase("GetRegisteredVehicles"))
                    {
                        loadRegistrationDatabase(fileName);
                        Set<String> set;
                        set = loadRegistrationDatabase("vehicles.csv");
                        Iterator iter = set.iterator(); // Set set iterator for looping through reg

                        String jsonString = "{" // root object
                                + "\"PacketType\":"
                                + "\"ReturnRegisteredVehicles\","
                                + "\"vehicles\":[";   // set up the array (list)
                        for (int i = 0; i < set.size(); i++)
                        {
                            Object element = iter.next();
                            jsonString
                                    += //                        + "\"id\":" + set + "\",";
                                    "\"" + element + "\",";
                        }
                        jsonString += "]}";  // close the array and close the object

                        socketWriter.println(jsonString);

                    } else if (value.equalsIgnoreCase("RegisterValidTollEvent"))
                    {
                        // Accept JSON File and Parse to Object
                        System.out.println(object.toString());
                        value = object.getString("PacketType");
                        String tollId = object.getString("TollBoothID");
                        String vehicleRegistration = object.getString("Vehicle Registration");
                        long vehicleImageId = object.getInt("Vehicle Image ID");
                        long timestamp = object.getInt("LocalDateTime");
                        TollEvent event = new TollEvent(tollId, vehicleRegistration, vehicleImageId, timestamp);
                        System.out.println(event.toString());

                        /*  #########################################################################
                            #   I was able to read the JSON String and allocate the data to the     #
                            #   TollEvent object. However when I used the DAO to write the          #
                            #   TollEvent Object, a DAO Exception Occured  at the server.start()    #
                            #   even though I declared the DAOs and the TollEvent Object correctly  #
                            #########################################################################
                         */
                        // Prepare TollEvent DAO, Write to Database
                        /*
                        TollEventDaoInterface ITollEventDao = new MySqlTollEventDao();

                        try
                        {
                            ITollEventDao.writeTollEvent(tollId, vehicleRegistration, vehicleImageId, timestamp);
                            System.out.println("Writing to Database Successful");
                        } catch (DaoException e)
                        {
                            e.printStackTrace();
                        }
                         */
                        // Prepare and send Response to the Server
                        JsonObject jsonRootObject = Json.createObjectBuilder()
                                .add("PacketType", "Registered Valid Toll Event")
                                .build();

                        String response = jsonRootObject.toString(); // JSON Request [String Format]
                        socketWriter.println(response); // Write this to the socket, Send back over to the Client

                    } else if (value.equalsIgnoreCase("RegisterInvalidTollEvent"))
                    {
                        // Accept JSON File and Parse to Object
                        System.out.println(object.toString());
                        value = object.getString("PacketType");
                        String tollId = object.getString("TollBoothID");
                        String vehicleRegistration = object.getString("Vehicle Registration");
                        long vehicleImageId = object.getInt("Vehicle Image ID");
                        long timestamp = object.getInt("LocalDateTime");

                        // Assign the data to the TollEvent Object
                        TollEvent event = new TollEvent(tollId, vehicleRegistration, vehicleImageId, timestamp);
                        System.out.println(event.toString());
                        ArrayList<String> invalidRegistrationsList = new ArrayList<>();
                        invalidRegistrationsList.add(event.getRegistration());

                        // Prepare and send Response to the Server
                        JsonObject jsonRootObject = Json.createObjectBuilder()
                                .add("PacketType", "Registered Invalid TollEvent")
                                .build();

                        String response = jsonRootObject.toString(); // JSON Request [String Format]
                        socketWriter.println(response); // Write this to the socket, Send back over to the Client
                    } else if (value.equalsIgnoreCase("Process Toll Events Billing"))
                    {
//                        TollEventDaoInterface ITollEventDao = new MySqlTollEventDao();
//                        try
//                        {
//                            List<TollEvent> events = ITollEventDao.ProcessTollEventBillingByMonth();
//
//                            if (events.isEmpty())
//                            {
//                                socketWriter.println("There are no Toll Events");
//                            }
//
//                            for (TollEvent event : events)
//                            {
//                                System.out.println(event.toString());
//                                socketWriter.println(events.toString());
//                            }
//                        } catch (DaoException e)
//                        {
//                            e.printStackTrace();
//                        }
                        JsonObject jsonRootObject = Json.createObjectBuilder()
                                .add("PacketType", "Billing Processed Successfully")
                                .build();

                        String response = jsonRootObject.toString(); // JSON Request [String Format]
                        socketWriter.println(response); // Write this to the socket, Send back over to the Client
                    } else if (value.equalsIgnoreCase("Close"))
                    {
                        socket.close();
                        System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");

                    } else
                    {
                        socketWriter.println("I'm sorry I don't understand :(");
                    }

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
        System.out.println("\n+-----------------------------------------------------+");
        System.out.println("|     Load Toll Event Registrations from Database     |");
        System.out.println("+-----------------------------------------------------+");

        System.out.println("Reading from " + fileName);

        Set set = new HashSet<String>();

        try
        {
            Scanner sc = new Scanner(new File(fileName));
            String reg = "";
            // default delimeter is whitespace and newlines
            while (sc.hasNext())
            {
                reg = sc.next();
                set.add(reg);
            }
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
