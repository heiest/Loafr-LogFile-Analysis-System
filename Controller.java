import java.io.File;
import java.util.Scanner;
import java.util.List;

public class Controller {

    // Constructor for the Controller class
    public Controller() {}

    // Main method to run the application
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Display the main menu and get the user's choice
            int option = displayMainMenu();

            switch (option) {
                case 1:
                    // User chose to analyze logs
                    String selectedLog = displayLogMenu();
                    LogFile file = new LogFile("./logs/" + selectedLog);
                    System.out.println();

                    while(true) {
                        // Display logs and analysis menu
                        file.printLogs();
                        option = displayAnalysisMenu();

                        switch (option) {
                            case 1:
                                // User chose to search logs based on key-value pair
                                String[] keyValueSearch = displayKeyValueMenu(file.getHeaders());
                                file.search(keyValueSearch[0], keyValueSearch[1]);
                                System.out.println();
                                file.printLogs();
                                break;
                            case 2:
                                // Incomplete option
                                System.out.println("Currently incomplete.");
                                break;
                            case 3:
                                // Incomplete option
                                System.out.println("Currently incomplete.");
                                break;
                            case 4:
                                // User chose to save log analysis
                                file.saveLogMap(displaySaveMenu());
                                break;
                            case 5:
                                // User chose to count logs based on key-value pair
                                String[] keyValueCount = displayKeyValueMenu(file.getHeaders());
                                int count = file.count(keyValueCount[0], keyValueCount[1]);
                                System.out.println();
                                System.out.println("Count: " + count);
                                break;
                            case 6:
                                // User chose to return to the main menu
                                System.out.println("Returning to main menu");
                                break;
                            default:
                                System.out.println("Invalid option. Please enter a valid option.\n");
                        }
                        System.out.println();
                        System.out.println("Enter anything to continue: ");
                        scanner.nextLine();
                        System.out.println();
   

                        if (option == 6) {break;}
                    }
                    break;

                case 2:
                    // User chose to exit the application
                    System.out.println("Exiting Loafr. Goodbye!");
                    System.exit(0);
                    break;

                default:
                    // Invalid option
                    System.out.println("Invalid option. Please enter a valid option.\n");
            }
        }
    }

    // Display menu to select a log file
    public String displayLogMenu() {
        Scanner scanner = new Scanner(System.in);
        String directoryPath = "./logs";
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        System.out.println("Available Files:");

        // Print the names of all files in the directory
        for (int i = 0; i < files.length; i++) {
            System.out.println((i+1) + ". " + files[i].getName());
        }

        while(true){
            // Get user input to select a log file
            System.out.print("\nSelect Option: ");
            try {
                int option = Integer.parseInt(scanner.nextLine());
                return files[option - 1].getName();
            } catch (NumberFormatException e) {
                System.out.println("Invalid option. Please enter a valid option.");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid option. Please enter a valid option.");
            }
        }
    }

    // Display the main menu and get the user's choice
    public int displayMainMenu() {
        System.out.println("Welcome to Loafr!!");
        System.out.println("1. Analyze Logs");
        System.out.println("2. Exit\n");

        return selectOption();
    }


    // Display analysis menu and get the user's choice
    public int displayAnalysisMenu() {
        System.out.println("\nLog analyzation options:");
        System.out.println("1. Search");
        System.out.println("2. Filter <TODO>");
        System.out.println("3. Sort <TODO>");
        System.out.println("4. Save");
        System.out.println("5. Count");
        System.out.println("6. Exit\n");

        return selectOption();
    }

    // Get user input for a file name to save log analysis
    public String displaySaveMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Save file as: ");
        String fileName = scanner.nextLine();

        return fileName;
    }

    // Get user input for a key-value pair to search logs
    public String[] displayKeyValueMenu(List<String> headers) {
        Scanner scanner = new Scanner(System.in);
        String key = "";
        String value = "";

        while(!headers.contains(key)){
            if (!key.equals("")) {
                System.out.println("Invalid Key, please try again\n");
            }
            System.out.print("Enter Key: ");
            key = scanner.nextLine();

            System.out.print("Enter Value: ");
            value = scanner.nextLine();
        }

        return new String[]{key, value};
    }

    // Get user input for selecting an option
    public int selectOption() {
        Scanner scanner = new Scanner(System.in);
        while(true){
            // Get user input for selecting an option
            System.out.print("Select Option: ");
            try {
                int option = Integer.parseInt(scanner.nextLine());
                return option;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid option.\n");
            }
        }
    }
}
