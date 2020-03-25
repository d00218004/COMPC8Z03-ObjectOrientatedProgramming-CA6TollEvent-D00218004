/** *****************************************************************************
 * Object-Orientated Programming CA5 [Stage 1] | Toll System | CA Value: 10%
 * Author: Matthew Waller | D00218004
 ***************************************************************************** */
package BusinessObjects;

import DTOs.TollEvent;
import Daos.MySqlTollEventDao;
import Daos.TollEventDaoInterface;
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

class MainApp
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
        //TollEventDaoInterface ITollEventDao = new MySqlTollEventDao();
        boolean quit = false;
        openMenu();
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
                    loadRegistrationDatabase(fileName);
                    break;
                case 2:
                    loadTollEventsDatabase(fileName1);
                    break;
                case 3:
                    processTollEvents(fileName);
                    break;
                case 4:
                    writeTollEvents(fileName);
                    break;
                case 5:
                    getAllTollEventsDetails();
                    break;
                case 6:
                    getAllTollEventsByRegistration(reg);
                    break;
                case 7:
                    getAllTollEventsSinceSpecifiedDateTime(timestamp);
                    break;
                case 8:
                    getAllTollEventsSinceStartDateTimeToDateTime(timestampStart, timestampFinish);
                    break;
                case 9:
                    getAllTollEventsRegistrations();
                    break;
                case 10:
                    getAllTollEventsReturnedMap();
                    break;
            }
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
                System.out.println(reg);
            }
            sc.close();
            System.out.println("\n+-----------------------------------------------------+");
            System.out.println("|               Database Load Successful              |");
            System.out.println("+-----------------------------------------------------+");

        } catch (IOException e)
        {
            System.out.println("\n+-----------------------------------------------------+");
            System.out.println("|              Database Load Unsuccessful             |");
            System.out.println("|            Check File Name / Path / Type            |");
            System.out.println("+-----------------------------------------------------+");
        }

        return set; // of valid registrations
    }

    public static void loadTollEventsDatabase(String fileName)
    {
        System.out.println("Load Parser Method: ");
        try
        {
            Set<String> set;
            set = loadParser("Toll-Events.csv");
        } catch (IOException e)
        {
            System.out.println("File Not Found!");
        }
    }

    public static Set loadParser(String fileName) throws FileNotFoundException
    {
        System.out.println("Reading from " + fileName);
        Set set = new HashSet<>();
        try
        {
            // The Default Delimeter is Whitespace and New Line
            try (Scanner sc = new Scanner(new File(fileName)))
            {
                sc.useDelimiter(";");
                while (sc.hasNext())
                {
                    String registration = sc.next();
                    set.add(registration);
                    System.out.println(registration);
                }
            }
        } catch (FileNotFoundException e)
        {
            System.out.println("File Not Found!");
        }
        return set;
    }

    public static void processTollEvents(String fileName)
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

        System.out.println("TollEvents: " + set.toString());
        // Creating a TollEvent Object Instance
        TollEvent event = new TollEvent("2051LH309", 222222, 1562537493);

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
            } else // The Registration is already in the map
            {
                ArrayList<TollEvent> list = map.get(event.getRegistration());
                list.add(event);  // Adds to the ArrayList in the map

            }

            System.out.println("Registration From map: " + map.get(event.getRegistration()));
        } else
        {
            System.out.println("Registration: " + event.getRegistration() + " is not a valid Registration, Adding to the Invalid Registration List...");

            // Add the Registration to a list of Invalid Registrations
            invalidRegistrationsList.add(event.getRegistration());
        }
    }

    public static void writeTollEvents(String fileName)
    {
        
        System.out.println("\n+-----------------------------------------------------+");
        System.out.println("|          Write Toll Events to the Database          |");
        System.out.println("+-----------------------------------------------------+");
        TollEventDaoInterface ITollEventDao = new MySqlTollEventDao();
    /*  #########################################################################
        #   I tried to process code where the write could send in bulk          #
        #   however i couldn't do it. I have the hardcoded code working         #
        #   I tested it and it added to the database in PHP                     #
        #########################################################################
     */
        try
        {
            ITollEventDao.writeTollEvent("201LH444", 33333, 1562537493);
            System.out.println("Writing to Database Successful");
        } catch (DaoException e)
        {
            e.printStackTrace();
        }

    }

    public static void getAllTollEventsDetails()
    {
        System.out.println("\n+-----------------------------------------------------+");
        System.out.println("|             Get all Toll Events Details             |");
        System.out.println("+-----------------------------------------------------+");
        TollEventDaoInterface ITollEventDao = new MySqlTollEventDao();
        try
        {
            List<TollEvent> events = ITollEventDao.findAllTollEvents();

            if (events.isEmpty())
            {
                System.out.println("There are no Toll Events");
            }

            for (TollEvent event : events)
            {
                System.out.println(event.toString());
            }
        } catch (DaoException e)
        {
            e.printStackTrace();
        }
    }

    public static void getAllTollEventsByRegistration(String reg)
    {
        System.out.println("\n+-----------------------------------------------------+");
        System.out.println("|      Get all Toll Events Details by Registration    |");
        System.out.println("+-----------------------------------------------------+");
        TollEventDaoInterface ITollEventDao = new MySqlTollEventDao();

        try
        {
            Scanner keyboard = new Scanner(System.in);

            System.out.print("Input Registration > "); // Try 191LH1111 for a sample List of Registrations
            reg = keyboard.nextLine();

            List<TollEvent> events = ITollEventDao.findAllTollEventsByRegistration(reg);
            if (events.isEmpty())
            {
                System.out.println("There are no Toll Events for this Registration");
            }

            for (TollEvent event : events)
            {
                System.out.println(event.toString());
            }
        } catch (DaoException e)
        {
            e.printStackTrace();
        }

        /*  #########################################################################
            #                Test DAO with Registration in the Database             #
            ######################################################################### */
//        try
//        {
//            List<TollEvent> events = ITollEventDao.findAllTollEventsByRegistration("191LH1111");
//            if (events.isEmpty())
//            {
//                System.out.println("There are no Toll Events for this Registration");
//            }
//
//            for (TollEvent event : events)
//            {
//                System.out.println(event.toString());
//            }
//        } catch (DaoException e)
//        {
//            e.printStackTrace();
//        }
        /*  #########################################################################
            #            Test DAO with Registration not in the Database             #
            ######################################################################### */
//        try
//        {
//            List<TollEvent> events = ITollEventDao.findAllTollEventsByRegistration("192LH1111");
//            if (events.isEmpty())
//            {
//                System.out.println("There are no Toll Events for this Registration");
//            }
//
//            for (TollEvent event : events)
//            {
//                System.out.println(event.toString());
//            }
//        } catch (DaoException e)
//        {
//            e.printStackTrace();
//        }
    }

    /* *****************************************************************************
     * I wasn't able to get the long timestamp to convert into the instance working
     * completely, so I kept the timestamp variables as a long, so at least the
     * code would print out properly. I left the instance conversion commented out
     * in the MySqlTollEventDao.java File. :(
     * *****************************************************************************/
    public static void getAllTollEventsSinceSpecifiedDateTime(long timestamp)
    {
        System.out.println("\n+-----------------------------------------------------+");
        System.out.println("|     Get all Toll Events Details by Date and Time    |");
        System.out.println("+-----------------------------------------------------+");
        TollEventDaoInterface ITollEventDao = new MySqlTollEventDao();

        try
        {
            Scanner keyboard = new Scanner(System.in);

            System.out.print("Input Date > "); // Try 20200215121545 for a sample List of Registrations
            timestamp = keyboard.nextLong();

            List<TollEvent> events = ITollEventDao.findAllTollEventsSinceSpecifiedDateTime(timestamp);
            if (events.isEmpty())
            {
                System.out.println("There are no Toll Events for this Date and Time");
            }

            for (TollEvent event : events)
            {
                System.out.println(event.toString());
            }
        } catch (DaoException e)
        {
            e.printStackTrace();
        }

        /*  #########################################################################
            #                    Test DAO with Date in the Database                 #
            ######################################################################### */
//        try
//        {
//            Scanner keyboard = new Scanner(System.in);
//
//            System.out.print("Input Date > "); // Try 20200215121545 for a sample List of Registrations
//            timestamp = keyboard.nextLong();
//
//            List<TollEvent> events = ITollEventDao.findAllTollEventsSinceSpecifiedDateTime(timestamp);
//
//            if (events.isEmpty())
//            {
//                System.out.println("There are no Toll Events for this Date and Time");
//            }
//
//            for (TollEvent event : events)
//            {
//                System.out.println(event.toString());
//            }
//        } catch (DaoException e)
//        {
//            e.printStackTrace();
//        }
        /*  #########################################################################
            #                Test DAO with Date not in the Database                 #
            ######################################################################### */
//        try
//        {
//            List<TollEvent> events = ITollEventDao.findAllTollEventsByRegistration("3030046992");
//
//            if (events.isEmpty())
//            {
//                System.out.println("There are no Toll Events for this Date and Time");
//            }
//
//            for (TollEvent event : events)
//            {
//                System.out.println(event.toString());
//            }
//        } catch (DaoException e)
//        {
//            e.printStackTrace();
//        }
    }

    /* *****************************************************************************
     * I wasn't able to get the long timestamp to convert into the instance working
     * completely, so I kept the timestamp variables as a long, so at least the
     * code would print out properly. I left the instance conversion commented out
     * in the MySqlTollEventDao.java File. :(
     * *****************************************************************************/
    public static void getAllTollEventsSinceStartDateTimeToDateTime(long timestampStart, long timestampFinish)
    {
        System.out.println("\n+-----------------------------------------------------+");
        System.out.println("|     Get all Toll Events Details by Date and Time    |");
        System.out.println("+-----------------------------------------------------+");
        TollEventDaoInterface ITollEventDao = new MySqlTollEventDao();

        try
        {
            Scanner keyboard = new Scanner(System.in);

            System.out.print("Input Start Date > "); // Try 20200214101530 for a sample List of Registrations
            timestampStart = keyboard.nextLong();

            System.out.print("Input Finish Date > "); // Try 20200214231539 for a sample List of Registrations
            timestampFinish = keyboard.nextLong();

            List<TollEvent> events = ITollEventDao.findAllTollEventsBetweenSpecifiedDateTime(timestampStart, timestampFinish);
            if (events.isEmpty())
            {
                System.out.println("There are no Toll Events for this Date and Time");
            }

            for (TollEvent event : events)
            {
                System.out.println(event.toString());
            }
        } catch (DaoException e)
        {
            e.printStackTrace();
        }

        /*  #########################################################################
            #                Test DAO with Registration in the Database             #
            ######################################################################### */
//        try
//        {
//            Scanner keyboard = new Scanner(System.in);
//
//            System.out.print("Input Start Date > "); // Try 20200214101530 for a sample List of Registrations
//            timestampStart = keyboard.nextLong();
//
//            System.out.print("Input Finish Date > "); // Try 20200214231539 for a sample List of Registrations
//            timestampFinish = keyboard.nextLong();
//
//            List<TollEvent> events = ITollEventDao.findAllTollEventsBetweenSpecifiedDateTime(timestampStart, timestampFinish);
//            if (events.isEmpty())
//            {
//                System.out.println("There are no Toll Events for this Date and Time");
//            }
//
//            for (TollEvent event : events)
//            {
//                System.out.println(event.toString());
//            }
//        } catch (DaoException e)
//        {
//            e.printStackTrace();
//        }

        /*  #########################################################################
            #            Test DAO with Registration not in the Database             #
            ######################################################################### */
        //        try
//        {
//            Scanner keyboard = new Scanner(System.in);
//                                                            
//            System.out.print("Input Start Date > "); // Try 99999999999999 for a sample List of Registrations
//            timestampStart = keyboard.nextLong();
//
//            System.out.print("Input Finish Date > "); // Try 99999999999999 for a sample List of Registrations
//            timestampFinish = keyboard.nextLong();
//
//            List<TollEvent> events = ITollEventDao.findAllTollEventsBetweenSpecifiedDateTime(timestampStart, timestampFinish);
//            if (events.isEmpty())
//            {
//                System.out.println("There are no Toll Events for this Date and Time");
//            }
//
//            for (TollEvent event : events)
//            {
//                System.out.println(event.toString());
//            }
//        } catch (DaoException e)
//        {
//            e.printStackTrace();
//        }
    }

    public static void getAllTollEventsRegistrations()
    {
        System.out.println("\n+-----------------------------------------------------+");
        System.out.println("|          Get all Toll Registration Details          |");
        System.out.println("+-----------------------------------------------------+");
        TollEventDaoInterface ITollEventDao = new MySqlTollEventDao();
        try
        {
            List<TollEvent> events = ITollEventDao.finaAllTollEventsThatPassedThroughToll();

            if (events.isEmpty())
            {
                System.out.println("There are no Toll Events");
            }

            for (TollEvent event : events)
            {
                System.out.println(event.toString());
            }
        } catch (DaoException e)
        {
            e.printStackTrace();
        }
    }

    public static void getAllTollEventsReturnedMap()
    {
        System.out.println("\n+-----------------------------------------------------+");
        System.out.println("|           Get all Toll Events Details Map           |");
        System.out.println("+-----------------------------------------------------+");
        TollEventDaoInterface ITollEventDao = new MySqlTollEventDao();
        try
        {
            List<TollEvent> events = ITollEventDao.findAllTollEvents();

            if (events.isEmpty())
            {
                System.out.println("There are no Toll Events");
            }

            for (TollEvent event : events)
            {
                System.out.println(event.toString());
            }
        } catch (DaoException e)
        {
            e.printStackTrace();
        }
    }

    private static void openMenu()
    {
        System.out.println("Opening Menu...");
    }

    private static void printActions()
    {
        System.out.println("\n+-----------------------------------------------------+");
        System.out.println("|\tVehicle Registration Toll Application         |");
        System.out.println("+-----------------------------------------------------+");
        System.out.println("0  - Shutdown\n"
                + "1  - Load Registration from the Database\n"
                + "2  - Load Toll Events from the File\n"
                + "3  - Process Toll Events\n"
                + "4  - Write Toll Events to the Database\n"
                + "5  - Get All Toll Event Details\n"
                + "6  - Get All Toll Event Details by Registration\n"
                + "7  - Get All Toll Event Details Since a Specified Date or Time\n"
                + "8  - Get All Toll Event Details Between a Specified Date or Time\n"
                + "9  - Get All Registrations that passed through the Toll\n"
                + "10 - Get All Toll Event Details (returned as a map)\n"
        );
    }
}
