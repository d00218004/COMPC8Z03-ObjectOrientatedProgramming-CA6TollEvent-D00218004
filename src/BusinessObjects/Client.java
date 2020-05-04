/** *****************************************************************************
 * Object-Orientated Programming CA5 [Stage 1] | Toll System | CA Value: 10%
 * Author: Matthew Waller | D00218004
 ***************************************************************************** */
package BusinessObjects;

import DTOs.TollEvent;
import DAOs.MySqlTollEventDao;
import DAOs.TollEventDaoInterface;
import Exceptions.DaoException;
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
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;

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
    public static void main(String[] args)
    {

        Client client = new Client();
        Scanner in = new Scanner(System.in);

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

            boolean quit = false;

            while (quit == false)
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
                        quit = true;
                        break;
                    case 1:
                        command = "Heartbeat";
                        heartbeat();
                        pressAnyKeyToContinue();
                        //loadRegistrationDatabase(fileName);
                        break;
                    case 2:
                        //loadTollEventsDatabase(fileName1);
                        break;
                    case 3:
                        //processTollEvents(fileName);
                        break;
                    case 4:
                        //writeTollEvents(fileName);
                        break;
                    case 5:
                        //getAllTollEventsDetails();
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
                        //getAllTollEventsReturnedMap();
                        break;
                }

            }
        } catch (IOException e)
        {
            System.out.println("Client message: IOException: " + e);
        }
    }

    private static void openMenu()
    {
        System.out.println("Opening Menu...");
    }

    private static void printActions()
    {
        System.out.println("\n+-------------------------------------------------------------------------------+");
        System.out.println("|\tVehicle Registration Toll Application\t\t\t\t\t|");
        System.out.println("|\t0  - Shutdown \t\t\t\t\t\t\t\t|\n"
                + "| \t1  - Load Registration from the Database\t\t\t\t|\n"
                + "| \t2  - Load Toll Events from the File\t\t\t\t\t|\n"
                + "| \t3  - Process Toll Events\t\t\t\t\t\t|\n"
                + "| \t4  - Write Toll Events to the Database\t\t\t\t\t|\n"
                + "| \t5  - Get All Toll Event Details\t\t\t\t\t\t|\n"
                + "| \t6  - Get All Toll Event Details by Registration\t\t\t\t|\n"
                + "| \t7  - Get All Toll Event Details Since a Specified Date or Time\t\t|\n"
                + "| \t8  - Get All Toll Event Details Between a Specified Date or Time\t|\n"
                + "| \t9  - Get All Registrations that passed through the Toll\t\t\t|\n"
                + "| \t10 - Get All Toll Event Details (returned as a map)\t\t\t|"
        );
        System.out.println("+-------------------------------------------------------------------------------+");
    }

    public static void pressAnyKeyToContinue()
    {
        System.out.print("\nPress Enter key to continue...");
        try
        {
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
        } catch (Exception e)
        {

        }
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

        String response = socketReader.nextLine();// wait for, and retrieve the echo ( or other message)
        System.out.println("Client: Response from server: \"" + response + "\"");
    }
}
