/**
 * Assignment3 Group 8.
 *
 * This program use the provided comma-separated value file, bikes.csv, to both retrieve and store
 * the necessary information about bikes. The format of this file is given below.
 *
 * <bike id>,<battery charge>,<in use?>,<rental start time>,<user name>
 *
 * This application shows menu with 4 options:
 *       1. Rent a bike
 *       2. Return a bike
 *       3. Charge battery
 *       4. Exit program
 *
 * On start up, this application reads bikes information from the data file and put them into an
 * ArrayList of Bike objects. On exit, it writes the current bike information from ArrayList of
 * Bike objects back to the data file.
 * Inputs:
 *       File path of the CSV file
 *       Menu option number
 *       Bike ID
 *       User name
 * Output:
 *       Menu
 *       Bikes information
 *       Rental minutes and fee
 *       Battery charge result
 * Methods:
 *       main()
 *       showMenu(Scanner, ArrayList)
 *       loadBikes(File, ArrayList)
 *       showMenu(Scanner, ArrayList)
 *       saveBikes(File, ArrayList)
 *       rentBike(Scanner, ArrayList);
 *       returnBike(Scanner, ArrayList);
 *       chargeBike(Scanner, ArrayList);
 *
 * @author: Waylon Mao(weilong.mao@edu.sait.ca)
 * @version 2022-04-24
 */
import java.io.*;
import java.util.*;

public class Main
{
    //Define static constant variables for batter drain rate, base rental fee, minute rental rate.
    static final double BATTERY_DRAIN = 0.002;
    static final double BASE_FEE = 1.0;
    static final double RATE = 0.25;
    public static void main(String[] args) throws IOException
    {
        Scanner in = new Scanner(System.in);
        System.out.println("*** Welcome to R&R E-Bike Rentals ***\n");
        System.out.print("Enter bike date filename: ");
        //Create File and ArrayList.
        File file = new File(in.next());//Input the CSV file path.
        ArrayList<Bike> bikes = new ArrayList<>();
        System.out.println();

        //Read the bikes' information to ArrayList bikes.
        loadBikes(file, bikes);

        //Display menu to user. The main processes are in this method.
        showMenu(in,bikes);

        //Save bikes information to CSV file after exit.
        saveBikes(file, bikes);
    }

    public static void showMenu(Scanner in,ArrayList<Bike> bikes)
    {
        //Define a boolean variable as a condition to exit the loop.
        boolean exit = false;
        while(!exit)
        {
            //Print bikes information at the beginning.
            printBikes(bikes);
            System.out.println("What would you like to do (1=Rent, 2=Return, 3=Charge Battery, 4=Exit)?");
            System.out.print("Enter your option number: ");
            int input = in.nextInt();
            System.out.println();

            //Here are 4 options.
            if(input == 1)
            {
                rentBike(in, bikes);    //Rent a bike.
            }
            else if(input == 2)
            {
                returnBike(in, bikes);  //Return a bike.
            }
            else if(input == 3)
            {
                chargeBike(in, bikes);  //Charge battery.
            }
            else
            {
                //Exit the menu and application.
                System.out.println("\nGood bye!\n");
                exit = true;
            }
        }

    }

    public static void loadBikes(File file, ArrayList<Bike> bikes) throws IOException
    {
        Scanner inData = new Scanner(file);
        // Set token delimiter to either a comma or a newline
        inData.useDelimiter(",|\r\n");
        // Define variables.
        String bikeID;
        double battery;
        boolean inUse;
        long startTime;
        String userName;

        // Read each bike information, create an object and store in ArrayList.
        while (inData.hasNext())
        {
            bikeID = inData.next();
            battery = inData.nextDouble();
            inUse = inData.nextBoolean();
            startTime = inData.nextLong();
            userName = inData.next();
            bikes.add(new Bike(bikeID, battery, inUse, startTime, userName));
        }
        //Close the file scanner.
        inData.close();
    }

    public static void printBikes(ArrayList<Bike> bikes)
    {
        //Print the table header.
        System.out.printf("%s%9s%15s\n", "Bike ID", "Battery", "Rental Status");
        System.out.println("-------  -------  ----------------------------------------");
        //Print the bike information line by line.
        for (Bike bike : bikes)
        {
            System.out.println(bike.toString());
        }
    }

    public static void saveBikes(File file, ArrayList<Bike> bikes) throws IOException
    {
        //Create PrintWriter to write data in the CSV file.
        PrintWriter outData = new PrintWriter(file);
        for (Bike bike : bikes) {
            outData.print(bike.getBikeId() + ",");
            outData.print(bike.getBatteryCharge() + ",");
            outData.print(bike.isInUse() + ",");
            outData.print(bike.getRentalStart() + ",");
            outData.println(bike.getUserName());
        }
        //Close the PrintWriter.
        outData.close();
    }

    public static Bike findBike(String input, ArrayList<Bike> bikes)
    {
        Bike bike;
        int matchIndex = bikes.indexOf(new Bike(input));
        if (matchIndex == -1)
        {
            //If there is no matched bike, return null.
            bike = null;
        }
        else
        {
            bike = bikes.get(matchIndex);
        }
        return bike;
    }

    public static void rentBike(Scanner input, ArrayList<Bike> bikes)
    {
        System.out.print("Rent a bike. Enter bike ID: ");
        input.nextLine();
        String inputID = input.nextLine().toUpperCase();
        //Call findBike to locate the specific bike.
        Bike bike = findBike(inputID, bikes);
        if(bike != null)
        {
            if (bike.isInUse())     //You can not rent a bike which already be rented.
            {
                System.out.printf("Bike %s is in use and cannot be rented.\n", bike.getBikeId());
            }
            else if (bike.isBatteryLow())   //Check the battery level.
            {
                System.out.printf("Bike %s has a low battery and cannot be rented.\n", bike.getBikeId());
            }
            else
            {
                System.out.print("Enter customer name or email: ");
                bike.rent(input.nextLine());
                System.out.printf("Bike %s rented to %s.\n",bike.getBikeId(),bike.getUserName());
            }
        }
        else
        {
            System.out.printf("Sorry. Bike %s is not found.\n", inputID);
        }
        System.out.print("Press [Enter] to continue...");
        input.nextLine();
        System.out.println();
    }

    private static void chargeBike(Scanner input, ArrayList<Bike> bikes)
    {
        System.out.print("Charge a battery. Enter bike ID: ");
        input.nextLine();
        String inputID = input.nextLine().toUpperCase();
        //Call findBike to locate the specific bike.
        Bike bike = findBike(inputID,bikes);
        if (bike != null)
        {
            if(bike.isInUse()){     //You can not charge a bike which already be rented.
                System.out.printf("Bike %s is rented and cannot be charged.\n",bike.getBikeId());
            }
            else
            {
                double battery = bike.getBatteryCharge();
                if (battery < 1.0)  //Check if it is already full.
                {
                    battery = battery + 0.25;
                    //Make sure the battery level will not charge to over 1.0
                    bike.setBatteryCharge(Math.min(battery, 1.0));
                    System.out.printf("1 charge unit applied to bike %s.\n", bike.getBikeId());
                }
                else
                {
                    System.out.printf("Bike %s does not need to be charged.\n",bike.getBikeId());
                }
            }
        }
        else
        {
            System.out.printf("Sorry. Bike %s is not found.\n", inputID);
        }
        System.out.print("Press [Enter] to continue...");
        input.nextLine();
        System.out.println();
    }

    public static void returnBike(Scanner input, ArrayList<Bike> bikes)
    {
        System.out.print("Return a bike. Enter bike ID: ");
        input.nextLine();
        String inputID = input.nextLine().toUpperCase();
        //Call findBike to locate the specific bike.
        Bike bike = findBike(inputID,bikes);
        if (bike != null)
        {
            if (bike.isInUse())     //You can not return a bike which have not been rented.
            {
                long rentalMillis = bike.unrent();
                long rentalMinutes = rentalMillis / 60_000;
                //Calculate the battery level.
                double battery = bike.getBatteryCharge() - (rentalMinutes * BATTERY_DRAIN);
                //The battery level can not be under 0.
                battery = (battery < 0 )? 0 : battery;
                bike.setBatteryCharge(battery);
                bike.unrent();
                System.out.printf("Bike %s returned. Minutes used: %d Cost: $%.2f\n", bike.getBikeId(), rentalMinutes, BASE_FEE + rentalMinutes * RATE);
            }
            else
            {
                System.out.printf("Bike %s does not need to be rented.\n", inputID);
            }
        }
        else
        {
            System.out.printf("Sorry. Bike %s is not found.\n", inputID);
        }

        System.out.print("Press [Enter] to continue...");
        input.nextLine();
        System.out.println();
    }
}
