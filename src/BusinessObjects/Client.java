/** *****************************************************************************
 * Object-Orientated Programming CA6 | Client-Server Toll System | CA Value: 35%
 * Author: Matthew Waller | D00218004
 ***************************************************************************** */
package BusinessObjects;

import DTOs.TollEvent;
import DAOs.MySqlTollEventDao;
import DAOs.TollEventDaoInterface;
import Exceptions.DaoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import static java.time.Clock.system;
import java.util.Scanner;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Client
{

    private static Scanner sc = new Scanner(System.in);

    /*  #########################################################################
        #   Switch Menu souced and ammended from Object Orientated Programming  #
        #   on Moodle from MobilePhone Contacts App - (Skeleton Code) zip file. #
        #   I ammended the cases, and the Menu Comments. I removed the methods  #
        #   already in the code and wrote my own menus to suit the project      #
        #########################################################################
     */
    public static void main(String[] args) throws IOException
    {

        Client client = new Client();
        Scanner keyboard = new Scanner(System.in);

        try
        {
            
            Socket socket = new Socket("localhost", 8080);
            System.out.println("Client: Port# of this client : " + socket.getLocalPort());
            System.out.println("Client: Port# of Server :" + socket.getPort());
            System.out.println("Client: This Client is running and has connected to the server");

            openMenu();

            String command = null;

            OutputStream os = socket.getOutputStream();
            PrintWriter socketWriter = new PrintWriter(os, true);

            socketWriter.println(command);  // Write command to the socket
            Scanner socketReader = new Scanner(socket.getInputStream());
            System.out.print("Press Enter to continue...");

            boolean quit = false;

            while (quit == false)
            {
                sc.nextLine();
                try
                {
                    printActions();
                    System.out.print("\nEnter Program Option: > ");
                    int action = sc.nextInt();
                    sc.nextLine();

                    String fileName = "vehicles.csv";
                    String fileName1 = "Toll-Event.csv";
                    List<TollEvent> list = null;
                    String reg = null;
                    long timestamp = 0, timestampStart = 0, timestampFinish = 0;

                    switch (action)
                    {
                        case 0:
                            System.out.println("Shutting down");
                            closeClientConnection();
                            sc.close();
                            quit = true;
                            break;
                        case 1:
                            heartbeat();
                            pressAnyKeyToContinue();
                            break;
                        case 2:
                            loadRegistrationDatabase();
                            pressAnyKeyToContinue();
                            break;
                        case 3:
                            loadTollEventsDatabase(fileName1);
                            pressAnyKeyToContinue();                            
                            break;
                        case 4:
                            processTollEvents(fileName1);
                            pressAnyKeyToContinue();                            
                            break;
                        case 5:
                            //processValidTollEvents();
                            break;
                        case 6:
                            //getAllTollEventsByRegistration(reg);
                            break;
                        case 7:
                            //getAllTollEventsSinceSpecifiedDateTime(timestamp);
                            break;
                        case 8:
                            //getAllTollEventsSinceStartDateTimeToDateTime(timestampStart, timestampFinish);
                            break;
                        case 9:
                            //getAllTollEventsRegistrations();
                            break;
                        case 10:
                            closeClientConnection();
                            break;
                    }
                } catch (Exception e)
                {
                    System.out.println("Invalid Option");
                }
            }
        } catch (IOException e)
        {
            System.out.println("Client message: IOException: " + e);
        }
    }

    private static void openMenu()
    {
        System.out.print("\nOpening Menu...");
    }

    private static void printActions()
    {
        System.out.println("\n+-------------------------------------------------------------------------------+");
        System.out.println("|\tVehicle Registration Toll Application\t\t\t\t\t|");
        System.out.println("|\t0  - Shutdown \t\t\t\t\t\t\t\t|\n"
                + "| \t1  - Test Client / Server Connection\t\t\t\t\t|\n"
                + "| \t2  - Load Vehicle Registrations from the File\t\t\t\t|\n"
                + "| \t3  - Load Toll Events from the File\t\t\t\t\t|\n"
                + "| \t4  - Process Toll Events\t\t\t\t\t\t|\n"
                + "| \t5  - Get All Toll Event Details\t\t\t\t\t\t|\n"
                + "| \t6  - Get All Toll Event Details by Registration\t\t\t\t|\n"
                + "| \t7  - Get All Toll Event Details Since a Specified Date or Time\t\t|\n"
                + "| \t8  - Get All Toll Event Details Between a Specified Date or Time\t|\n"
                + "| \t9  - Get All Registrations that passed through the Toll\t\t\t|\n"
                + "| \t10 - Close Client Connection\t\t\t\t\t\t|"
        );
        System.out.println("+-------------------------------------------------------------------------------+");
    }

    public static void pressAnyKeyToContinue()
    {
        System.out.print("\nPress Enter key to continue...");
    }

    public static void heartbeat() throws IOException
    {
        Socket socket = new Socket("localhost", 8080);
        OutputStream os = socket.getOutputStream();
        PrintWriter socketWriter = new PrintWriter(os, true);
        String command = "Heartbeat";
        socketWriter.println(command);  // Write command to the socket
        Scanner socketReader = new Scanner(socket.getInputStream());

        System.out.println("\n+-------------------------------------------------------------------------------+");
        System.out.println("|\tTest Client / Server Connection\t\t\t\t\t\t|");
        System.out.println("+-------------------------------------------------------------------------------+");
        JsonBuilderFactory factory = Json.createBuilderFactory(null);

        JsonObject jsonRootObject
                = Json.createObjectBuilder()
                        .add("PacketType", "Heartbeat")
                        .build();

        String value = jsonRootObject.toString(); // JSON Request [String Format]
        socketWriter.println(value);  // write command to socket

        System.out.println("Client Request: " + value);

//        BufferedReader socketReader;
//        PrintWriter socketWriter;
//        Socket socket;
//        int clientNumber;
//    
//        InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
//        this.socketReader = new BufferedReader(isReader);
//
//        OutputStream os = clientSocket.getOutputStream();
//        this.socketWriter = new PrintWriter(os, true); // true => auto flush socket buffer
//
//        this.clientNumber = clientNumber; // ID number that we are assigning to this client
//
//        this.socket = clientSocket; // store socket ref for closing
       
        String response = socketReader.nextLine();// wait for, and retrieve the echo ( or other message)
        System.out.println("Client: Response from server: \"" + response + "\"");
        socket.close();
    }

    public static void loadRegistrationDatabase() throws IOException
    {
        Socket socket = new Socket("localhost", 8080);
        OutputStream os = socket.getOutputStream();
        PrintWriter socketWriter = new PrintWriter(os, true);
        
        String command = "GetRegisteredVehicles";
        socketWriter.println(command);  // Write command to the socket
        Scanner socketReader = new Scanner(socket.getInputStream());

        System.out.println("\n+-------------------------------------------------------------------------------+");
        System.out.println("|\tLoad Vehicle Registration from File\t\t\t\t\t|");
        System.out.println("+-------------------------------------------------------------------------------+");

        JsonObject jsonRootObject
                = Json.createObjectBuilder()
                        .add("PacketType", "GetRegisteredVehicles")
                        .build();

        String value = jsonRootObject.toString(); // JSON Request [String Format]
        socketWriter.println(value);  // write command to socket
        System.out.println("Client Request: " + value);

        //--------------------------------------------------------------------------------------------------------------------------
        
        String response = socketReader.nextLine();// wait for, and retrieve the echo ( or other message)
//        InputStreamReader in = new InputStreamReader(System.in);
//        BufferedReader read = new BufferedReader(in);
//        String message;
//        while ((message = socketReader.readLine()) != null)
//                {
//                    System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + response);
//
//                    JsonReader reader = Json.createReader(socketReader);
//                    JsonObject object = reader.readObject();
//                    String value = object.getString("PacketType");
//                }
//        
//        
//        
        System.out.println("Client: Response from server: " + response);
        socket.close();
    }
    
    public static void loadTollEventsDatabase(String fileName1)
    {
        System.out.println("Load Parser Method: ");
        try
        {
            Set<String> tollEventsSet;
            tollEventsSet = loadParser("Toll-Events.csv");
            System.out.print("Toll Events loaded stored successfully");
        } catch (IOException e)
        {
            System.out.println("File Not Found!");
        }
    }
    
    public static Set loadParser(String fileName) throws FileNotFoundException
    {
        System.out.println("Reading from " + fileName);
        Set tollEventsSet = new HashSet<>();
        try
        {
            // The Default Delimeter is Whitespace and New Line
            try (Scanner sc = new Scanner(new File(fileName)))
            {
                sc.useDelimiter(";");
                while (sc.hasNext())
                {
                    String registration = sc.next();
                    tollEventsSet.add(registration);
                    System.out.println(registration);
                }
            }
        } catch (FileNotFoundException e)
        {
            System.out.println("File Not Found!");
        }
        return tollEventsSet;
    }
    
    public static void processTollEvents(String fileName1)
    {
        System.out.println("\n+-----------------------------------------------------+");
        System.out.println("|                Process Toll Events                  |");
        System.out.println("+-----------------------------------------------------+");

        // Declare Set of Valid Vehicle Registrations
        Set<String> set;

        // ArrayList of Invalid Registrations
        ArrayList<String> invalidRegistrationsList = new ArrayList<>();

        // Load Vehicle Database from CSV into Set
        set = loadRegistrationDatabase("vehicles.csv");

        // Valid Registrations 'look-up Table' (Check Object Instance against Valid Registrations)
        HashMap<String, ArrayList<TollEvent>> map = new HashMap<>();

        //System.out.println("TollEvents: " + set.toString());
        // Creating a TollEvent Object Instance
        //TollEvent event = new TollEvent("TB_M50", "205LH309", 222222, 1562537493);
        TollEvent event = new TollEvent("TB_M50", "205LH309", 222222, 1562537493);

        // Check Object Instance against the HashMap
        if (set.contains(event.getRegistration()))
        {
            System.out.println("Registration: " + event.getRegistration() + " is valid ");
            // then process the TollEvent object i.e. write TollEvent object to a map<String(registration), List of TollEvents (ArrayList)>

            // map(KEY, VALUE)
            if (map.get(event.getRegistration()) == null) // The Registration is not in the map
            {
                ArrayList<TollEvent> list = new ArrayList<>();
                list.add(event);
                map.put(event.getRegistration(), list);
                System.out.println("Valid Toll Events: "+list);

                JsonObject jsonRootObject = Json.createObjectBuilder()
                                .add("PacketType", "RegisterValidTollEvent")
                                .add("TollBoothID", event.getTollBoothID())
                                .add("Vehicle Registration", event.getRegistration())
                                .add("Vehicle Image ID", event.getImageId())
                                .add("LocalDateTime", event.getTimestamp())
                                .build();
                        String value = jsonRootObject.toString(); // JSON Request [String Format]
                System.out.println(value);  // write command to socket
            } else // The Registration is already in the map
            {
                ArrayList<TollEvent> list = map.get(event.getRegistration());
                list.add(event);  // Adds to the ArrayList in the map
                System.out.println("Adding event to the ArrayList");
            }

            //System.out.println("Registration From map: " + map.get(event.getRegistration()));
        } else
        {
            System.out.println("Registration: " + event.getRegistration() + " is not a valid Registration, Adding to the Invalid Registration List...");

            // Add the Registration to a list of Invalid Registrations
            invalidRegistrationsList.add(event.getRegistration());
            System.out.println("Invalid Toll Events: " + event);
            
            JsonObject jsonRootObject = Json.createObjectBuilder()
                                .add("PacketType", "RegisterInvalidTollEvent")
                                .add("TollBoothID", event.getTollBoothID())
                                .add("Vehicle Registration", event.getRegistration())
                                .add("Vehicle Image ID", event.getImageId())
                                .add("LocalDateTime", event.getTimestamp())
                                .build();
                        String value = jsonRootObject.toString(); // JSON Request [String Format]
                System.out.println(value);  // write command to socket
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

            // default delimeter is whitespace and newlines
            while (sc.hasNext())
            {
                String reg = sc.next();
                set.add(reg);
                //System.out.println(reg);
            }
            sc.close();
            System.out.println("File load successful. Data stored into set");

        } catch (IOException e)
        {
            System.out.println("File load unsuccessful. Check File name and location.");
        }

        return set; // of valid registrations
    }
    
    public static void closeClientConnection() throws IOException
    {
        Socket socket = new Socket("localhost", 8080);
        OutputStream os = socket.getOutputStream();
        PrintWriter socketWriter = new PrintWriter(os, true);
        
        String command = "Close";
        socketWriter.println(command);  // Write command to the socket
    }
}
