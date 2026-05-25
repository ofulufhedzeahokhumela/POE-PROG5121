package quickchatt;

import java.util.Scanner;

public class Quickchatt {

    public static void main(String[] args) {

        // Scanner reads input from the user
        Scanner input = new Scanner(System.in);

        // Login object handles registration and login
        Login login = new Login();

        System.out.println("==================================");
        System.out.println(" Registration and Login System");
        System.out.println("==================================");

        // Registration section
        System.out.print("Enter first name: ");
        String firstName = input.nextLine();
        login.setFirstName(firstName);

        System.out.print("Enter last name: ");
        String lastName = input.nextLine();
        login.setLastName(lastName);

        System.out.print("Enter username: ");
        String userName = input.nextLine();
        login.setUserName(userName);
        System.out.println(login.getUserNameMessage());

        System.out.print("Enter password: ");
        String password = input.nextLine();
        login.setPassword(password);
        System.out.println(login.getPasswordMessage());

        System.out.print("Enter cell phone number (example: +27838968976): ");
        String cellPhone = input.nextLine();
        login.setCellPhoneNumber(cellPhone);
        System.out.println(login.getCellPhoneMessage());

        String registrationMessage = login.registerUser();
        System.out.println(registrationMessage);

        // Only allow login if registration was successful
        if (registrationMessage.equals("User has been registered successfully.")) {

            System.out.println("\nLogin");

            System.out.print("Enter username: ");
            String enteredUserName = input.nextLine();

            System.out.print("Enter password: ");
            String enteredPassword = input.nextLine();

            boolean loggedIn = login.loginUser(enteredUserName, enteredPassword);
            System.out.println(login.returnLoginStatus());

            // Part 2 starts only if login is successful
            if (loggedIn) {
                runQuickChat(input);
            }
        }

        input.close();
    }

    // This method runs the Part 2 QuickChat menu
    public static void runQuickChat(Scanner input) {

        System.out.println("\nWelcome to QuickChat.");

        // Ask the user how many messages they want to enter
        int maxMessages = readInt(input, "How many messages would you like to enter? ");

        int messagesEntered = 0;
        boolean running = true;

        // The menu keeps running until the user chooses Quit
        while (running) {

            System.out.println("\nPlease choose an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");

            int option = readInt(input, "Enter option: ");

            if (option == 1) {

                // Stop the user from entering more than the chosen number of messages
                if (messagesEntered >= maxMessages) {
                    System.out.println("You have already entered the set number of messages.");
                } else {
                    messagesEntered++;
                    sendMessageProcess(input, messagesEntered);
                }

            } else if (option == 2) {

                // This feature is required to display Coming Soon
                System.out.println("Coming Soon.");

            } else if (option == 3) {

                // Quit the program
                running = false;

                Message tempMessage = new Message(0, "+27838968976", "Temporary message");
                System.out.println("Total messages sent: " + tempMessage.returnTotalMessages());
                System.out.println("Goodbye.");

            } else {
                System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }
    }

    // This method handles the process of creating and sending one message
    public static void sendMessageProcess(Scanner input, int messageNumber) {

        String recipient;
        String messageText;

        // Ask for recipient number
        System.out.print("Enter recipient number (example: +27718693002): ");
        recipient = input.nextLine();

        // Ask for the message
        System.out.print("Enter your message: ");
        messageText = input.nextLine();

        // Create the message object
        Message message = new Message(messageNumber, recipient, messageText);

        // Validate recipient number
        while (!message.isRecipientCellValid()) {
            System.out.println(message.checkRecipientCell());

            System.out.print("Enter recipient number again: ");
            recipient = input.nextLine();

            message = new Message(messageNumber, recipient, messageText);
        }

        System.out.println(message.checkRecipientCell());

        // Validate message length
        while (!message.checkMessageLength().equals("Message ready to send.")) {
            System.out.println(message.checkMessageLength());

            System.out.print("Enter a shorter message: ");
            messageText = input.nextLine();

            message = new Message(messageNumber, recipient, messageText);
        }

        System.out.println(message.checkMessageLength());

        // Display message details
        System.out.println("\nMessage Details:");
        System.out.println(message.getFullMessageDetails());

        // Ask what to do with the message
        System.out.println("\nWhat would you like to do?");
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");

        int sendChoice = readInt(input, "Enter option: ");

        // Display result of selected option
        System.out.println(message.sentMessage(sendChoice));

        // If the user selected disregard, ask them to press 0
        if (sendChoice == 2) {
            int deleteChoice = readInt(input, "Enter 0 to delete the message: ");

            if (deleteChoice == 0) {
                System.out.println("Message deleted.");
            }
        }
    }

    // This helper method safely reads an integer from the user
    public static int readInt(Scanner input, String message) {

        while (true) {
            System.out.print(message);

            try {
                int number = Integer.parseInt(input.nextLine());
                return number;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
