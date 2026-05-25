package quickchatt;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
  
public class Message {

    // Stores all sent messages while the program is running
    private static ArrayList<String> sentMessages = new ArrayList<>();

    // Stores messages that the user chooses to store
    private static ArrayList<Message> storedMessages = new ArrayList<>();

    // Counts the total number of messages successfully sent
    private static int totalMessagesSent = 0;

    // Used to create random message IDs
    private static Random random = new Random();

    // Message details
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    // Constructor used when creating a normal message
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageID = generateMessageID();
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }

    // Constructor used mainly for unit testing
    public Message(String messageID, int messageNumber, String recipient, String messageText) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }

    // Generates a random 10-digit message ID
    private String generateMessageID() {
        long number = 1000000000L + (long) (random.nextDouble() * 9000000000L);
        return String.valueOf(number);
    }

    // Checks that the message ID is not more than 10 characters
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // Checks if the recipient cell number is valid
    public String checkRecipientCell() {
        if (recipient.matches("^\\+27\\d{9}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Boolean version of the recipient check, useful inside the main program
    public boolean isRecipientCellValid() {
        return recipient.matches("^\\+27\\d{9}$");
    }

    // Checks that the message is not more than 250 characters
    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int extraCharacters = messageText.length() - 250;
            return "Message exceeds 250 characters by " + extraCharacters + "; please reduce the size.";
        }
    }

    // Creates the message hash using ID, message number, first word, and last word
    public String createMessageHash() {
        String[] words = messageText.trim().split("\\s+");

        String firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");

        String firstTwoDigits = messageID.substring(0, 2);

        return (firstTwoDigits + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    // Allows the user to send, disregard, or store the message
    public String sentMessage(int choice) {
        if (choice == 1) {
            totalMessagesSent++;

            sentMessages.add(
                "Message ID: " + messageID +
                "\nMessage Hash: " + messageHash +
                "\nRecipient: " + recipient +
                "\nMessage: " + messageText + "\n"
            );

            return "Message successfully sent.";
        } else if (choice == 2) {
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            storeMessage();
            return "Message successfully stored.";
        } else {
            return "Invalid option selected.";
        }
    }

    // Prints all sent messages while the program is running
    public String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent yet.";
        }

        String allMessages = "";

        for (String message : sentMessages) {
            allMessages += message + "\n";
        }

        return allMessages;
    }

    // Returns the total number of messages successfully sent
    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Stores messages in a JSON file
    public void storeMessage() {
        storedMessages.add(this);

        try {
            FileWriter writer = new FileWriter("messages.json");

            writer.write("[\n");

            for (int i = 0; i < storedMessages.size(); i++) {
                Message msg = storedMessages.get(i);

                writer.write("  {\n");
                writer.write("    \"messageID\": \"" + msg.messageID + "\",\n");
                writer.write("    \"messageHash\": \"" + msg.messageHash + "\",\n");
                writer.write("    \"recipient\": \"" + msg.recipient + "\",\n");
                writer.write("    \"message\": \"" + msg.messageText.replace("\"", "\\\"") + "\"\n");
                writer.write("  }");

                if (i < storedMessages.size() - 1) {
                    writer.write(",");
                }

                writer.write("\n");
            }

            writer.write("]");
            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving message to JSON file.");
        }
    }

    // Returns full details of one message
    public String getFullMessageDetails() {
        return "Message ID: " + messageID +
               "\nMessage Hash: " + messageHash +
               "\nRecipient: " + recipient +
               "\nMessage: " + messageText;
    }

    // Getter used for testing
    public String getMessageHash() {
        return messageHash;
    }

    // Getter used for testing
    public String getMessageID() {
        return messageID;
    }
}
