import java.util.*;
import java.io.*;

public class EndProject {
    static Scanner input = new Scanner(System.in);
    static int choice;

    static String customerName = "";
    static String cnic = "";
    static int roomNumber = 0;
    static int days = 0;

    static String[] orderedItems = new String[20];
    static int[] orderedPrices = new int[20];
    static int orderIndex = 0;
    static int foodTotal = 0;

    static String[] selectedServices = new String[20];
    static int[] selectedPrices = new int[20];
    static int serviceIndex = 0;
    static int serviceTotal = 0;

    public static void main(String args[]) { // main method starts from here

        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║                                                      ║");
        System.out.println("║                    WELCOME TO THE                    ║");
        System.out.println("║                                                      ║");
        System.out.println("║               HOTEL MANAGEMENT SYSTEM!               ║");
        System.out.println("║                                                      ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.println("║               Press enter to continue.               ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        input.nextLine(); // continues as the user presses enter. // reads the line from the user, but
                          // discards any input.

        MainMenu();

    } // main method ends here

    public static void MainMenu() { // main menu method starts from here

        System.out.println("\n╔═════════════════════════════════════════════╗");
        System.out.println("║               * MAIN MENU *                 ║");
        System.out.println("╠═════════════════════════════════════════════╣");
        System.out.println("║ Would you like to:                          ║");
        System.out.println("║                                             ║");
        System.out.println("║   1. Register                               ║");
        System.out.println("║   2. Login as user.                         ║");
        System.out.println("║   3. Login as admin.                        ║");
        System.out.println("║   4. Exit                                   ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        System.out.print(">> ");

        choice = validInput();

        while (choice < 1 || choice > 4) {
            System.out.println("Invalid input.Try again");
            choice = validInput();
        }

        switch (choice) {
            case 1:
                registerUser();
                break;

            case 2:
                loginUser();
                break;

            case 3:
                loginAdmin();
                break;
            case 4:
                Exit();
        }
    }

    public static int validInput() { // 2. valid input method starts from here

        while (true) {
            if (input.hasNextInt()) {
                choice = input.nextInt();

                return choice; // ---> returns the choice user made to the calling code.
            } else {
                System.out.println("Invalid input. Try again.");
                input.next();
            }
        }
    }

    public static void registerUser() { // 3. register the user method starts here

        input.nextLine(); // Clears the input buffer first

        String username;
        String password;

        while (true) {

            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║               * REGISTER ACCOUNT *                 ║");
            System.out.println("╚════════════════════════════════════════════════════╝");

            System.out.print("Choose a Username: ");
            username = input.nextLine();

            System.out.print("Choose a Password: ");
            password = input.nextLine();

            if (isUserOrPasswordTaken(username, password)) {
                System.out.println("\nUsername or password already exists. Please try different ones.\n");
            } else {
                break;
            }
        }

        try {

            FileWriter file = new FileWriter("users.txt", true); // creates a file in append mode
            PrintWriter writer = new PrintWriter(file);

            writer.println(username + "," + password); // writes the username and password into the file

            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                                                              ║");
            System.out.println("║                    REGISTRATION SUCCESSFUL                   ║");
            System.out.println("║                                                              ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");

            writer.close();

            try { // giving a break of 1.5 millisecond before the next box shows us
                Thread.sleep(1500); // 1500 ms = 1.5 second

            } catch (InterruptedException ex) {

            }

            System.out.println("\n╔══════════════════════════════════════════════════╗");
            System.out.println("║     Do you wish to login into your system?       ║");
            System.out.println("║                                                  ║");
            System.out.println("║       Press 'yes' to continue or 'no' to exit.   ║");
            System.out.println("╚══════════════════════════════════════════════════╝");
            System.out.print(">> ");

            String answer = input.next();

            while (!(answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("no"))) {

                System.out.println("Invalid input! Please type 'yes' to continue or 'no' to quit.");
                System.out.print("Your choice: ");

                answer = input.next();
            }

            if (answer.equalsIgnoreCase("yes")) {
                loginUser();
            } else {
                System.out.println("\n══════════════════════════════════════════════════════════════");
                System.out.println("                        Exiting the System ");
                System.out.println("══════════════════════════════════════════════════════════════");
                System.exit(0);
            }

        } catch (IOException ex) {
            System.out.println("Error writing to file.");
        }
    }

    public static boolean isUserOrPasswordTaken(String username, String password) {
        try {

            File file = new File("users.txt");

            if (!file.exists()) {
                return false;
            }
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(",");

                if (parts.length == 2) {
                    if (parts[0].equals(username) || parts[1].equals(password)) {
                        reader.close();
                        return true;
                    }
                }
            }
            reader.close();
        } catch (IOException ex) {
            System.out.println("Error reading user file in isUserOrPasswordTaken Method.");
        }
        return false;
    }

    public static boolean checkAdminCreds(String username, String pass) {
        try {

            File file = new File("admin.txt");

            if (!file.exists())
                return false;

            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(",");

                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(pass)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();

        } catch (IOException e) {
            System.out.println("Error reading admin file.");
        }
        return false;
    }

    public static void loginAdmin() {
        input.nextLine();

        boolean found = false;
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;
        String loggedInAdmin = "";

        while (!found && attempts < MAX_ATTEMPTS) {
            System.out.print("Enter Username: ");
            String adminName = input.nextLine().trim();
            System.out.print("Enter Password: ");
            String pass = input.nextLine().trim();
            if (checkAdminCreds(adminName, pass)) {
                found = true;
                loggedInAdmin = adminName;
                
                 
                System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                System.out.println("║                                                              ║");
                System.out.println("║                      LOGIN SUCCESSFUL                        ║");
                System.out.println("║                                                              ║");
                System.out.println("╚══════════════════════════════════════════════════════════════╝");
                adminMenu(loggedInAdmin);

            } else if (!checkAdminCreds(adminName, pass)) {
                attempts++;
                if (attempts < MAX_ATTEMPTS) {
                    System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                    System.out.println("║                                                              ║");
                    System.out.println("║                   INVALID CREDENTIALS!                       ║");
                    System.out.println("║                   Attempts remaining: " + (MAX_ATTEMPTS - attempts)
                            + "                      ║");
                    System.out.println("║                                                              ║");
                    System.out.println("╚══════════════════════════════════════════════════════════════╝");
                } else {
                    System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                    System.out.println("║                                                                ║");
                    System.out.println("║                  TOO MANY FAILED ATTEMPTS!                     ║");
                    System.out.println("║                    Returning to Main Menu.                     ║");
                    System.out.println("║                                                                ║");
                    System.out.println("╚════════════════════════════════════════════════════════════════╝");

                    MainMenu();
                }
            }
            }
        }

    

    public static void loginUser() {
        input.nextLine();

        boolean found = false;
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;

        
        

        while (!found && attempts < MAX_ATTEMPTS) {

            System.out.print("Enter Username: ");
            String username = input.nextLine().trim();

            System.out.print("Enter Password: ");
            String password = input.nextLine().trim();

             
                try {

                    File file = new File("users.txt");

                    if (!file.exists()) {

                        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                        System.out.println("║                                                              ║");
                        System.out.println("║              No users registered yet!                        ║");
                        System.out.println("║             Please register first.                           ║");
                        System.out.println("║                                                              ║");
                        System.out.println("╚══════════════════════════════════════════════════════════════╝");
                        System.out.println("Press ENTER to continue to registration...");
                        input.nextLine();

                        registerUser();
                        continue;
                    }

                    Scanner reader = new Scanner(file);

                    while (reader.hasNextLine()) {

                        String line = reader.nextLine().trim();

                        if (!line.isEmpty()) {
                            String[] parts = line.split(",");
                            if (parts.length >= 2 && parts[0].equals(username) && parts[1].equals(password)) {
                                found = true;
                                
                                
                                break;
                            }
                        }
                    }
                    reader.close();

                } catch (IOException ex) {
                    System.out.println("Error reading user file.");
                    return;
                }
            

            if (found) {
                System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                System.out.println("║                                                              ║");
                System.out.println("║                      LOGIN SUCCESSFUL                        ║");
                System.out.println("║                                                              ║");
                System.out.println("╚══════════════════════════════════════════════════════════════╝");

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

                userMenu(username);
                break;
            } else {
                attempts++;
                if (attempts < MAX_ATTEMPTS) {
                    System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                    System.out.println("║                                                              ║");
                    System.out.println("║                   INVALID CREDENTIALS!                       ║");
                    System.out.println("║                   Attempts remaining: " + (MAX_ATTEMPTS - attempts)
                            + "                      ║");
                    System.out.println("║                                                              ║");
                    System.out.println("╚══════════════════════════════════════════════════════════════╝");
                } else {
                    System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                    System.out.println("║                                                                ║");
                    System.out.println("║                  TOO MANY FAILED ATTEMPTS!                     ║");
                    System.out.println("║                    Returning to Main Menu.                     ║");
                    System.out.println("║                                                                ║");
                    System.out.println("╚════════════════════════════════════════════════════════════════╝");

                    MainMenu();
                }
            }
        }
    }

    public static void manageRooms(String adminUsername) {

        String[] manageRoomOpt = { "1. Add Room Booings", "2. Search Booking", "3. Update Booking",
                "4. Back to admin Menu" };
        PrintMenu("Manage Rooms", " ", manageRoomOpt);
        System.out.print(">> ");

        choice = validInput();
        while (choice < 1 || choice > 4) {
            System.out.println("Invalid input. Try again");
            choice = validInput();
        }

        switch (choice) {
            case 1:
                System.out.println("\n--- Booking a Room for a Customer (Admin Action) ---");
                System.out.print("Enter customer's username for this booking: ");
                input.nextLine();

                String customerForBooking = input.nextLine();
                bookRoom(customerForBooking);
                break;
            case 2:
                searchBooking();
                break;
            case 3:
                updateBooking();
                break;
            case 4:
                adminMenu(adminUsername);
                break;
        }
    }

    public static void searchBooking() {
        input.nextLine();
        System.out.println("\n╔═════════════════════════════════════════════╗");
        System.out.println("║              * SEARCH BOOKING *               ║");
        System.out.println("╚═══════════════════════════════════════════════╝");

        System.out.print("Enter customer's username or CNIC to search: ");
        String searchTerm = input.nextLine().trim();

        File currentDir = new File(".");  //current working directory

        File[] bookingFiles = currentDir.listFiles((dir, name) -> name.startsWith("booking_") && name.endsWith(".txt"));//->lambda func act as a filter

// A lambda expression is a shortcut to pass a method (function) as a value.
        boolean found = false;

        if (bookingFiles != null) {
            for (File file : bookingFiles) {

                try (Scanner reader = new Scanner(file)) {
                    StringBuilder bookingDetails = new StringBuilder();

                    boolean fileContainsSearchTerm = false;

                    while (reader.hasNextLine()) {
                        String line = reader.nextLine();
                        bookingDetails.append(line).append("\n");

                        if (line.toLowerCase().contains(searchTerm.toLowerCase())) {
                            fileContainsSearchTerm = true;
                        }
                    }

                    if (file.getName().toLowerCase().contains(searchTerm.toLowerCase()) || fileContainsSearchTerm) {
                        System.out.println("\n--- Booking Found in " + file.getName() + " ---");
                        System.out.println(bookingDetails.toString());
                        found = true;
                    }
                } catch (IOException e) {
                    System.out.println("Error reading file: " + file.getName());
                }
            }
        }

        if (!found) {
            System.out.println("\nNo booking found matching the"+searchTerm+".");
        }
        menuRedirectQues("", "admin");
    }

    public static void updateBooking() {
        input.nextLine();

        System.out.println("\n╔═════════════════════════════════════════════╗");
        System.out.println("║             * UPDATE BOOKING * ║");
        System.out.println("╚═════════════════════════════════════════════╝");

        System.out.print("Enter username of the customer whose booking you want to update: ");
        String customerToUpdate = input.nextLine().trim();

        String filename = "booking_" + customerToUpdate + ".txt";
        File file = new File(filename);

        if (!file.exists()) {

            System.out.println("No booking found for user: " + customerToUpdate);
            menuRedirectQues("", "admin");
            return;
        }
        try {
            StringBuilder fileContent = new StringBuilder();
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                fileContent.append(reader.nextLine()).append("\n");
            }
            reader.close();

            System.out.println("\n--- Current Booking Details for " + customerToUpdate + " ---");

            System.out.println(fileContent.toString());

            String[] udateBookingOpt = { "1. Room Number", "2. Days of Stay.", "3. Go back to admin menu" };
            PrintMenu("Update Booking", "what you want to update", udateBookingOpt);
            System.out.print(">> ");

            int updateChoice = validInput();
            input.nextLine();

            String updatedContent = fileContent.toString();
            int oldRoomNumber = 0;

            switch (updateChoice) {
                case 1:
                    java.util.regex.Pattern roomNumPattern = java.util.regex.Pattern.compile("Room Number: (\\d+)");
                    java.util.regex.Matcher roomNumMatcher = roomNumPattern.matcher(fileContent.toString());
                    if (roomNumMatcher.find()) {
                        oldRoomNumber = Integer.parseInt(roomNumMatcher.group(1));
                    }
                    System.out.print("Enter new room number: ");
                    int newRoomNumber = validInput();
                    input.nextLine();

                    if (isRoomAlreadyBooked(newRoomNumber) && newRoomNumber != oldRoomNumber) {
                        System.out.println("Room " + newRoomNumber + " is already booked. Cannot update.");
                        break;
                    }

                    updatedContent = updatedContent.replaceAll("Room Number: \\d+", "Room Number: " + newRoomNumber);
                    System.out.println("Room number updated to: " + newRoomNumber);

                    if (oldRoomNumber != 0 && oldRoomNumber != newRoomNumber) {

                        File bookedRoomsFile = new File("booked_rooms.txt");
                        File tempBookedRoomsFile = new File("booked_rooms_temp.txt");
                        Scanner bookedReader = new Scanner(bookedRoomsFile);
                        PrintWriter bookedWriter = new PrintWriter(tempBookedRoomsFile);

                        while (bookedReader.hasNextLine()) {
                            String line = bookedReader.nextLine();
                            if (!line.trim().equals(String.valueOf(oldRoomNumber))) {
                                bookedWriter.println(line);
                            }else{
                                bookedWriter.println(newRoomNumber);
                            }
                        }
                        bookedReader.close();
                        bookedWriter.close();
                        bookedRoomsFile.delete();
                        tempBookedRoomsFile.renameTo(bookedRoomsFile);

                       
                    }
                    break;

                case 2:
                    System.out.print("Enter new number of days of stay: ");
                    int newDays = validInput();
                    input.nextLine();

                    updatedContent = updatedContent.replaceAll("Days of Stay: \\d+", "Days of Stay: " + newDays);
                    System.out.println("Days of stay updated to: " + newDays);
                    break;

                case 3:
                    adminMenu("admin");
                    return;
                default:

                    System.out.println("Invalid update choice.");
            }

            FileWriter fw = new FileWriter(filename);
            PrintWriter writer = new PrintWriter(fw);
            writer.print(updatedContent);
            writer.close();
            System.out.println("\nBooking updated successfully!");

        } catch (IOException e) {
            System.out.println("Error updating booking for user: " + customerToUpdate);
            e.printStackTrace();
        }
        menuRedirectQues("", "admin");
    }

    public static void viewAllBookings() {

        System.out.println("\n══════════════════════════════════════════════════════════");
        System.out.println("                   ALL HOTEL BOOKINGS");
        System.out.println("══════════════════════════════════════════════════════════");

        File currentDir = new File(".");
        File[] bookingFiles = currentDir.listFiles((dir, name) -> name.startsWith("booking_") && name.endsWith(".txt"));

        if (bookingFiles == null || bookingFiles.length == 0) {
            System.out.println("No booking files found.");
            menuRedirectQues("", "admin");
            return;
        }

        for (File file : bookingFiles) {
            try (Scanner reader = new Scanner(file)) {
                System.out.println(
                        "\n--- Booking for " + file.getName().replace("booking_", "").replace(".txt", "") + " ---");
                while (reader.hasNextLine()) {
                    System.out.println(reader.nextLine());
                }
                System.out.println("---------------------------------------------------------");
            } catch (IOException e) {
                System.out.println("Error reading file: " + file.getName());
            }
        }
        System.out.println("\n══════════════════════════════════════════════════════════");
        menuRedirectQues("", "admin"); // Prompt to go back to admin menu
    }

    public static void viewAllOrders() {
        System.out.println("\n══════════════════════════════════════════════════════════");
        System.out.println("                   ALL FOOD AND SERVICE ORDERS");
        System.out.println("══════════════════════════════════════════════════════════");

        File currentDir = new File(".");
        File[] bookingFiles = currentDir.listFiles((dir, name) -> name.startsWith("booking_") && name.endsWith(".txt"));

        if (bookingFiles == null || bookingFiles.length == 0) {
            System.out.println("No booking files found (and thus no orders).");
            menuRedirectQues("", "admin");
            return;
        }

        boolean foundOrders = false;
        for (File file : bookingFiles) {
            try (Scanner reader = new Scanner(file)) {

                boolean inFoodSection = false;
                boolean inServiceSection = false;
                boolean userHasOrders = false;

                StringBuilder currentOrderDetails = new StringBuilder();

                while (reader.hasNextLine()) {
                    String line = reader.nextLine();

                    if (line.contains("----- Food Order -----")) {

                        inFoodSection = true;
                        inServiceSection = false;
                        currentOrderDetails.append("\n--- Orders for "
                                + file.getName().replace("booking_", "").replace(".txt", "") + " ---\n");
                        currentOrderDetails.append(line).append("\n");   // add heading to file
                        userHasOrders = true;
                        foundOrders = true;
                        continue;
                    }

                    if (line.contains("----- Room Services -----")) {

                        inServiceSection = true;
                        inFoodSection = false;
                        currentOrderDetails.append(line).append("\n");
                        userHasOrders = true;
                        foundOrders = true;
                        continue;
                    }

                    if (inFoodSection || inServiceSection) {
                        if (line.isEmpty() || line.contains("Total Food Bill:")
                                || line.contains("Total Room Service Bill:")) {

                            currentOrderDetails.append(line).append("\n");
                            inFoodSection = false;
                            inServiceSection = false;

                        } else if (!line.startsWith("-----") && !line.startsWith("Customer Name:")
                                && !line.startsWith("CNIC:") && !line.startsWith("Room Type:")
                                && !line.startsWith("Room Number:") && !line.startsWith("Days of Stay:")
                                && !line.startsWith("Cost per Day:") && !line.startsWith("Total Cost:")) {

                            currentOrderDetails.append(line).append("\n");
                        }
                    }
                }
                if (userHasOrders) {
                    System.out.println(currentOrderDetails.toString());
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + file.getName());
            }
        }

        if (!foundOrders) {
            System.out.println("No food or service orders found across all bookings.");
        }

        System.out.println("\n══════════════════════════════════════════════════════════");
        menuRedirectQues("", "admin");
    }

    public static void adminMenu(String username) {
        String[] adminMenuOpt = { "1. Manage Rooms", "2. View all Bookings", "3. View all Orders", "4. logout" };
        PrintMenu("Admin Menu", "What would you like to do?", adminMenuOpt);

        choice = validInput();
        while (choice < 1 || choice > 4) {
            System.out.println("Invalid input. Try again");
            choice = validInput();
        }

        switch (choice) {
            case 1:
                manageRooms(username);
                break;
            case 2:
                viewAllBookings();
                break;
            case 3:
                viewAllOrders();
                break;
            case 4:
                logout(username);
                break;
        }
    }

    public static void userMenu(String username) {
        System.out.println();

        String[] usermenuOpt = { "1. Book room", "2. Order food", "3. Order service", "4. view bill",
                "5. generate receipt/ checkout", "6. logout" };
        PrintMenu("USER MENU", "What would you like to do?", usermenuOpt);
        System.out.println("Enter your choice: ");

        choice = validInput();
        while (choice < 1 || choice > 6) {
            System.out.println("Invalid input.Try again");
            choice = validInput();
        }

        switch (choice) {
            case 1:
                bookRoom(username);
                break;

            case 2:
                orderFood(username);
                break;

            case 3:
                roomService(username);
                break;

            case 4:
                viewBill(username);
                break;

            case 5:
                generateReceipt(username);
                break;

            case 6:
                logout(username);
                break;
        }

    }

    public static void bookRoom(String username) {

        input.nextLine();

        System.out.println("\n╔═════════════════════════════════════════════╗");
        System.out.println("║               * BOOK A ROOM *               ║");
        System.out.println("╚═════════════════════════════════════════════╝");

        System.out.println("\n─────────────── ENTER YOUR NAME ───────────────");
        System.out.print(">> ");

        customerName = input.nextLine();

        while (true) {

            System.out.println("\n──────── ENTER CNIC (xxxxx-xxxxxxx-x) ─────────");
            System.out.print(">> ");

            cnic = input.nextLine();

            if (cnic.matches("\\d{5}-\\d{7}-\\d")) {
                break;
            } else {
                System.out.println("Invalid CNIC format.");
            }
        }

        System.out.println("\n╔═════════════════════════════════════════════════════╗");
        System.out.println("║                  SELECT ROOM TYPE                   ║");
        System.out.println("╠═════════════════════════════════════════════════════╣");
        System.out.println("║   1. Standard Room   (100-199)     rs. 5000  pkr    ║");
        System.out.println("║   2. Deluxe Room     (200-299)     rs. 8000  pkr    ║");
        System.out.println("║   3. Executive Room  (300-399)     rs. 12000 pkr    ║");
        System.out.println("╚═════════════════════════════════════════════════════╝");

        int typeChoice = 0;

        while (true) {
            System.out.print("Enter your choice (1-3): ");

            try {

                typeChoice = Integer.parseInt(input.nextLine());

                if (typeChoice >= 1 && typeChoice <= 3) {
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }

            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }

        int startRoom = 0, endRoom = 0, costPerDay = 0;
        String roomType = "";

        switch (typeChoice) {
            case 1:
                startRoom = 100;
                endRoom = 199;
                costPerDay = 5000;
                roomType = "Standard Room";
                break;

            case 2:
                startRoom = 200;
                endRoom = 299;
                costPerDay = 8000;
                roomType = "Deluxe Room";
                break;

            case 3:
                startRoom = 300;
                endRoom = 399;
                costPerDay = 12000;
                roomType = "Executive Room";
                break;
        }

        // SHOW AVAILABLE ROOMS
        System.out.println("\n────────── AVAILABLE ROOMS IN THIS CATEGORY ──────────");
        System.out.println();

        int availableCount = 0;

        for (int i = startRoom; i <= endRoom; i++) {

            if (!isRoomAlreadyBooked(i)) {
                System.out.print(" " + i + " ");
                availableCount++;

                if (availableCount % 10 == 0)
                    System.out.println();
            }
        }

        if (availableCount == 0) {

            System.out.println("\n╔══════════════════════════════════════════════════════╗");
            System.out.println("║   No rooms available in this category.               ║");
            System.out.println("║   Please try again later or choose another type.     ║");
            System.out.println("╚══════════════════════════════════════════════════════╝");

            return ;
        }

        System.out.println();

        while (true) {
            System.out.print("\n──────── SELECT A ROOM NUMBER FROM THE LIST ABOVE ────────\n>> ");

            try {

                roomNumber = Integer.parseInt(input.nextLine());

                if (roomNumber < startRoom || roomNumber > endRoom) {
                    System.out.println("Room is not in the selected type.");
                } else if (isRoomAlreadyBooked(roomNumber)) {
                    System.out.println("That room is already booked. Choose another.");
                } else {
                    break;
                }

            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Enter a number.");
            }
        }

        // DAYS OF STAY
        while (true) {
            System.out.println("\n───────── ENTER NUMBER OF DAYS TO STAY ─────────");
            System.out.print(">> ");

            try {
                days = Integer.parseInt(input.nextLine());

                if (days <= 0) {
                    System.out.println("Days must be positive.");
                } else {
                    break;
                }

            } catch (NumberFormatException ex) {
                System.out.println("Invalid input.");
            }
        }

        int totalCost = costPerDay * days;

        // CONFIRMATION
        System.out.println("\n╔═════════════════════════════════════════════╗");
        System.out.println("║              BOOKING CONFIRMED              ║");
        System.out.println("╠═════════════════════════════════════════════╣");
        System.out.printf("║ %-43s ║\n", "Name         : " + customerName);
        System.out.printf("║ %-43s ║\n", "Room Type    : " + roomType);
        System.out.printf("║ %-43s ║\n", "Room Number  : " + roomNumber);
        System.out.printf("║ %-43s ║\n", "Cost per Day : Rs. " + costPerDay);
        System.out.printf("║ %-43s ║\n", "Days         : " + days);
        System.out.printf("║ %-43s ║\n", "Total Cost   : Rs. " + totalCost);
        System.out.println("╚═════════════════════════════════════════════╝");

        
        String filename = "booking_" + username + ".txt";

        try {
            FileWriter file = new FileWriter(filename);
            PrintWriter writer = new PrintWriter(file);

            writer.println("Customer Name: " + customerName);
            writer.println("CNIC: " + cnic);
            writer.println("Room Type: " + roomType);
            writer.println("Room Number: " + roomNumber);
            writer.println("Days of Stay: " + days);
            writer.println("Cost per Day: Rs. " + costPerDay);
            writer.println("Total Cost: Rs. " + totalCost);
            writer.println("Status: Booked");

            writer.close();

            System.out.println("\nBooking data saved successfully for user: " + username);

            try {
                FileWriter fw = new FileWriter("booked_rooms.txt", true); // append mode
                fw.write(roomNumber + "\n");
                fw.close();

            } catch (IOException ex) {
                System.out.println("Error saving booking.");
            }

            menuRedirectQues(username, "user");

        } catch (IOException e) {
            System.out.println("Error saving room number.");
        }
    }

    public static boolean isRoomAlreadyBooked(int roomNum) {
        try {
            File file = new File("booked_rooms.txt");

            if (!file.exists()) {
                return false; // No bookings yet
            }

            Scanner input = new Scanner(file);

            while (input.hasNextLine()) {

                int booked = Integer.parseInt(input.nextLine());

                if (booked == roomNum) {
                    input.close();
                    return true;
                }
            }
            input.close();

        } catch (IOException | NumberFormatException ex) {
            System.out.println("Error checking room bookings.");
        }
        return false;
    }

    public static void orderFood(String username) {
        String[] foodItems = { "Biryani", "Burger", "Pizza", "Kababs", "Cold Drink" };
        int[] foodPrices = { 400, 300, 500, 200, 100 };
        String[] orderMenuOpt = { "1. Biryani     - Rs. 400", "2. Burger       -Rs. 300", "3. Pizza     -Rs. 500",
                "4. Kababs     -Rs. 200", "5. Cold Drinks       -Rs. 100" };
        PrintMenu("Welcome To Menu", "", orderMenuOpt);
        input.nextLine();

        while (true) {
            System.out.println("\n──────── ENTER THE ITEM NUMBER TO ORDER (0 TO FINISH) ────────");
            System.out.print(">> ");

            try {
                int choice = Integer.parseInt(input.nextLine());

                if (choice == 0) {
                    break;

                } else if (choice >= 1 && choice <= 5) {

                    orderedItems[orderIndex] = foodItems[choice - 1];

                    orderedPrices[orderIndex] = foodPrices[choice - 1];

                    foodTotal += foodPrices[choice - 1];

                    System.out.println("\n" + foodItems[choice - 1] + " added to your order.");
                    orderIndex++;

                } else {
                    System.out.println("Invalid item number. Please select between 1 to 5.");
                }

            } catch (NumberFormatException ex) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }

        // Showing order summary
        if (orderIndex > 0) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                      YOUR ORDER SUMMARY                      ║");
            System.out.println("╠══════════════════════════════════════════════════════════════╣");

            for (int i = 0; i < orderIndex; i++) {

                String itemLine = (i + 1) + ". " + orderedItems[i] + " - Rs. " + orderedPrices[i];

                System.out.printf("║ %-60s ║\n", itemLine);
            }

            System.out.println("╠══════════════════════════════════════════════════════════════╣");
            System.out.printf("║ %-60s ║\n", "Total Food Bill: Rs. " + foodTotal);
            System.out.println("╚══════════════════════════════════════════════════════════════╝");

        } else {
            System.out.println("You did'nt order anything.");
        }

        try {
            String filename = "booking_" + username.trim() + ".txt";
            FileWriter file = new FileWriter(filename, true); // true = append mode
            PrintWriter writer = new PrintWriter(file);

            writer.println();
            writer.println("----- Food Order -----");

            for (int i = 0; i < orderIndex; i++) {
                writer.println(orderedItems[i] + " - Rs. " + orderedPrices[i]);
            }

            writer.println("Total Food Bill: Rs. " + foodTotal);
            writer.close();

        } catch (IOException ex) {
            System.out.println("Error saving food order.");
        }

        menuRedirectQues(username, "user");
    }

    public static void roomService(String username) {
        String[] serviceItems = { "Laundry", "Cleaning", "Extra Towels", "Bathroom supplies", "Ironing" };
        int[] servicePrices = { 1000, 1000, 500, 900, 850 };
        String[] roomServiceOpt = { "1. Laundry       -Rs. 1000", "2. Cleaning     -Rs. 1000",
                "3. Extra Towels     -Rs. 500", "4. Bathroom supplies     -Rs. 900", "5. Ironing       -Rs. 850",
                "0. Done Selecting Services" };
        PrintMenu("Room Service Menu", "", roomServiceOpt);
        input.nextLine(); // Clears any leftover newlines

        while (true) {
            System.out.println("\n──────── ENTER THE SERVICE NUMBER TO ADD (0 TO FINISH) ────────");
            System.out.print(">> ");

            try {
                int choice = Integer.parseInt(input.nextLine());

                if (choice == 0) {
                    break;

                } else if (choice >= 1 && choice <= 5) {

                    selectedServices[serviceIndex] = serviceItems[choice - 1];
                    selectedPrices[serviceIndex] = servicePrices[choice - 1];

                    serviceTotal += servicePrices[choice - 1];

                    System.out.println("\n" + serviceItems[choice - 1] + " added to your room service.");
                    serviceIndex++;

                } else {
                    System.out.println("Invalid choice. Select between 1 - 5.");
                }

            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        if (serviceIndex > 0) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                    ROOM SERVICE SUMMARY                      ║");
            System.out.println("╠══════════════════════════════════════════════════════════════╣");

            for (int i = 0; i < serviceIndex; i++) {

                String line = (i + 1) + ". " + selectedServices[i] + " - Rs. " + selectedPrices[i];

                System.out.printf("║ %-60s ║\n", line);
            }

            System.out.println("╠══════════════════════════════════════════════════════════════╣");
            System.out.printf("║ %-60s ║\n", "Total Room Service Bill: Rs. " + serviceTotal);
            System.out.println("╚══════════════════════════════════════════════════════════════╝");

        } else {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║            You didn't request any room services.             ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
        }

        // Appending to user's booking file
        try {
            String filename = "booking_" + username.trim() + ".txt";
            FileWriter file = new FileWriter(filename, true); // append mode
            PrintWriter writer = new PrintWriter(file);

            writer.println();
            writer.println("----- Room Services -----");

            for (int i = 0; i < serviceIndex; i++) {
                writer.println(selectedServices[i] + " - Rs. " + selectedPrices[i]);
            }

            writer.println("Total Room Service Bill: Rs. " + serviceTotal);
            writer.close();

        } catch (IOException ex) {
            System.out.println("Error saving room service.");
        }
        menuRedirectQues(username, "user");
    }

    public static void viewBill(String username) { // 10. viewBill method starts here
        String filename = "booking_" + username + ".txt";

        int roomCost = 0, foodCost = 0, serviceCost = 0;

        System.out.println("\n══════════════════════════════════════════════════════════");
        System.out.println("                     BILL SUMMARY");
        System.out.println("══════════════════════════════════════════════════════════");

        try {
            File file = new File(filename);

            if (!file.exists()) {
                System.out.println("No booking data found for user: " + username);
                menuRedirectQues(username,"user");
                return;
            }

            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                System.out.println(line);

                if (line.contains("Total Cost: Rs.")) {
                    roomCost += extractAmount(line);
                }

                if (line.contains("Total Food Bill: Rs.")) {
                    foodCost += extractAmount(line);
                }

                if (line.contains("Total Room Service Bill: Rs.")) {
                    serviceCost += extractAmount(line);
                }
            }

            int subtotal = roomCost + foodCost + serviceCost;
            double gst = subtotal * 0.17;
            double grandTotal = subtotal + gst;

            System.out.println("\n══════════════════════════════════════════════════════════");
            System.out.println(" SUBTOTAL: Rs. " + subtotal);
            System.out.printf(" GST (17%%): Rs. %.0f\n", gst);  // % is a special character in formatting
            System.out.printf("GRAND TOTAL (with GST): Rs. %.0f\n", grandTotal);
            System.out.println("══════════════════════════════════════════════════════════");

            reader.close();

            menuRedirectQues(username, "user");

        } catch (IOException ex) {
            System.out.println("Error reading file.");
        }
    }

    public static int extractAmount(String line) {
        try {
            if (line.contains("Rs.")) {
                String[] parts = line.split("Rs\\."); // . is a special character in regex, so we use \\. to mean the literal dot.
                if (parts.length == 2) {
                    String amount = parts[1].trim().split(" ")[0]; // Get only the number
                    return Integer.parseInt(amount);
                }
            }
        } catch (NumberFormatException ex) {
            System.out.println("Could not parse amount from: " + line);
        }
        return 0;
    }

    public static void generateReceipt(String username) { // 12. generate receipt method

        String inputFile = "booking_" + username.trim() + ".txt";
        String outputFile = "receipt_" + username.trim() + ".txt";

        int roomCost = 0, foodCost = 0, serviceCost = 0;

        try {
            File file = new File(inputFile);

            if (!file.exists()) {
                System.out.println("No booking found for user: " + username);
                return;
            }

            Scanner reader = new Scanner(file);

            FileWriter fw = new FileWriter(outputFile);
            PrintWriter writer = new PrintWriter(fw);

            writer.println("═════════════════════════════════════════════════════════════");
            writer.println("                     HOTEL MANAGEMENT SYSTEM");
            writer.println("                      FINAL RECEIPT FOR: " + username);
            writer.println("═════════════════════════════════════════════════════════════");

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                writer.println(line);

                // Extract totals
                if (line.contains("Total Cost:")) {

                    roomCost += extractAmount(line);
                }

                if (line.contains("Total Food Bill:")) {
                    foodCost += extractAmount(line);
                }

                if (line.contains("Total Room Service Bill:")) {
                    serviceCost += extractAmount(line);
                }
            }

            int subtotal = roomCost + foodCost + serviceCost;
            double gst = subtotal * 0.17;
            double grandTotal = subtotal + gst;

            writer.println("═════════════════════════════════════════════════════════════");
            writer.println("SUBTOTAL (Room + Food + Services): Rs. " + subtotal);
            writer.printf("GST (17%%): Rs. %.0f\n", gst);
            writer.printf("GRAND TOTAL (Including GST): Rs. %.0f\n", grandTotal);
            writer.println("Date: " + java.time.LocalDate.now());
            writer.println("Time: " + java.time.LocalTime.now());

            writer.println("═════════════════════════════════════════════════════════════");
            writer.println("             Thank you for staying with us!");
            writer.println("═════════════════════════════════════════════════════════════");

            reader.close();
            writer.close();

            try {
                Scanner showReceipt = new Scanner(new File(outputFile));

                while (showReceipt.hasNextLine()) {
                    System.out.println(showReceipt.nextLine());
                }
                showReceipt.close();
            } catch (IOException e) {
                System.out.println("Error displaying receipt.");
            }

            resetUserData(username);
            menuRedirectQues(username, "user");

        } catch (IOException e) {
            System.out.println("Error generating receipt:");
        }
    }

  public static void resetUserData(String username) {
    int roomNumber=0;
    
    try{
        File userFile = new File("booking_"+username.trim()+".txt");
        Scanner read = new Scanner(userFile);
        while (read.hasNextLine()) {
            String line = read.nextLine().trim();
            if (line.startsWith("Room Number:")){
                String [] parts = line.split(": ");
                roomNumber= Integer.parseInt(parts[1].trim());  
            }
           
        }
        read.close();
        }
    catch(IOException e){
        System.out.println("cannot open file of"+username+"to to extract room no that is occupied by this user in order to remove booked room from \"booked_rooms.txt\"");
    }
    if (roomNumber != 0) {
        try {
            File inputFile = new File("booked_rooms.txt");
            File tempFile = new File("booked_rooms_temp.txt");

            Scanner scanner = new Scanner(inputFile);
            PrintWriter writer = new PrintWriter(tempFile);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // Skip the line if it matches the current room number
                if (!line.equals(String.valueOf(roomNumber))) {
                    writer.println(line);
                }
            }

            scanner.close();
            writer.close();

            // Replace the original file with the updated one
            if (inputFile.delete()) {
                if (!tempFile.renameTo(inputFile)) {
                    System.out.println("Failed to rename temp file.");
                }
            } else {
                System.out.println("Failed to delete original booked_rooms.txt file.");
            }

        } catch (IOException e) {
            System.out.println("Error removing room number from booked_rooms.txt: " + e.getMessage());
        }
    }

    // Reset booking info
    customerName = "";
    cnic = "";
    roomNumber = 0;
    days = 0;

    // Reset food order
    for (int i = 0; i < orderedItems.length; i++) {
        orderedItems[i] = null;
        orderedPrices[i] = 0;
    }

    orderIndex = 0;
    foodTotal = 0;

    // Reset room service
    for (int i = 0; i < selectedServices.length; i++) {
        selectedServices[i] = null;
        selectedPrices[i] = 0;
    }

    serviceIndex = 0;
    serviceTotal = 0;

    // Clear the user's booking file
    String filename = "booking_" + username.trim() + ".txt";
    try {
        new PrintWriter(filename).close(); // Clear content
    } catch (IOException e) {
        System.out.println("Error clearing user file.");
    }
}

    public static void logout(String username) { // 14. logout method starts here

        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("                      Logging out...");
        System.out.println("══════════════════════════════════════════════════════════════");
        System.out.println("You have been logged out successfully.");

        try { // giving a break of 1.5 millisecond before the next box shows us
            Thread.sleep(1500); // 1500 ms = 1.5 second

        } catch (InterruptedException ex) {

        }

        MainMenu();
    }

    public static void menuRedirectQues(String username, String userRole) {

        try {
            Thread.sleep(1500);

        } catch (InterruptedException ex) {
        }

        System.out.println("\n╔══════════════════════════════════════════════════╗");

        if (userRole.equals("admin")) {

            System.out.println("║   Do you wish to go back to the Admin menu?      ║");
        } else {
            System.out.println("║   Do you wish to go back to the User menu?       ║");
        }

        System.out.println("║                                                  ║");
        System.out.println("║       Press 'yes' to continue or 'no' to exit.   ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        String answer = input.next();

        while (!(answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("no"))) {
            System.out.println("Invalid input! Please type 'yes' to continue or 'no' to quit.");
            System.out.print("Your choice: ");
            answer = input.next();
        }

        if (answer.equalsIgnoreCase("yes")) {

            if (userRole.equals("admin")) {
                adminMenu(username);
            } else {
                userMenu(username);
            }
        } else {
            Exit();
        }
    }

    public static void Exit() { // EXIT METHOD ( TERMINATES THE PROGRAM.)

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                              ║");
        System.out.println("║               Thank you for using our system.                ║");
        System.out.println("║                                                              ║");
        System.out.println("║                     Exiting the System.                      ║");
        System.out.println("║                                                              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        System.exit(0);
    }

    public static void PrintMenu(String menuTitle, String question, String[] menuOpt) { // 1. padding menu starts here

        int maxWidth = menuTitle.length();

        if (question.length() > maxWidth) {
            maxWidth = question.length();
        }

        for (String option : menuOpt) { // for each loop
            if (option.length() > maxWidth) {
                maxWidth = option.length();
            }
        }

        maxWidth += 6;

        System.out.println("╔" + "═".repeat(maxWidth) + "╗");

        int titlePadding = (maxWidth - menuTitle.length()) / 2;

        System.out.println("║" + " ".repeat(titlePadding) + menuTitle
                + " ".repeat(maxWidth - titlePadding - menuTitle.length()) + "║");
        System.out.println("╠" + "═".repeat(maxWidth) + "╣");
        System.out.println("║ " + question + " ".repeat(maxWidth - question.length() - 1) + "║\n");

        for (String opt : menuOpt) {
            System.out.println("║ " + opt + " ".repeat(maxWidth - opt.length() - 1) + "║");
        }
        System.out.println("╚" + "═".repeat(maxWidth) + "╝");
    }

    public static String readFile(String filename) { // 2. read file menu starts here.
        String wholeData = "";
        String content = "";

        try {

            File file = new File(filename);
            Scanner readfile = new Scanner(file);

            while (readfile.hasNextLine()) {
                content = readfile.nextLine();
                wholeData = wholeData + "\n" + content;
            }
            readfile.close();
        } catch (IOException e) {
            System.out.println("Cannot open " + filename + " file to extract data!");
            System.out.println(e.getMessage());
        }
        return wholeData;
    }

}
