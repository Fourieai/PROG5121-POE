/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registrationandloginfeature;

/**
 *
 * @author Student
 */

//imports

import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class StoredMessages {
  
//accept user inputs

static Scanner input = new Scanner(System.in);

//parralel arrays to store user personal data

static ArrayList<String> sentMessages = new ArrayList<>();
static ArrayList<String> disregardedMessages = new ArrayList<>();
static ArrayList<String> storedMessages = new ArrayList<>();
static ArrayList<String> messageHashes = new ArrayList<>();
static ArrayList<String> messageIDs = new ArrayList<>();
static ArrayList<String> storedRecipients = new ArrayList<>();

//load JSON into arrays

public static void loadStoredMessages() {
    
storedMessages.clear();
messageHashes.clear();
messageIDs.clear();
storedRecipients.clear();

try {
    
String content = Files.readString(Paths.get("messages.json"));

JSONArray jsonArray = new JSONArray(content);

for (int i = 0; i < jsonArray.length(); i++) {

JSONObject obj = jsonArray.getJSONObject(i);

//reads all the json.file and occupies all the parallel arrays

storedMessages.add(obj.optString("Message", "N/A"));
messageHashes.add(obj.optString("MessageHash", "N/A"));
messageIDs.add(String.valueOf(obj.optLong("MessageID", 0)));
storedRecipients.add(obj.optString("Recipient", "N/A"));

}

System.out.println("\nStored messages loaded successfully from JSON file.\n");

} catch (IOException e) {

System.out.println("\nCould not read 'messages.json'. File may not exist yet.\n");

} catch (JSONException e) {

System.out.println("\nError parsing 'messages.json'. Please check file format.\n");

    }
}

//menu for user to be able to see stored, delete and etc. to messages

public static void openStoredMessagesMenu() {

loadStoredMessages();

boolean running = true;

while (running) {

//displayed to user for selection process

System.out.println("\n   Stored Messages Menu   \n");
System.out.println("1. Display the sender recipient of all stored messages.");
System.out.println("2. Display the longest stored message.");
System.out.println("3. Search using messageID and display the corresponding recipient and message.");
System.out.println("4. Search all the messages stored for a particular recipient.");
System.out.println("5. Delete a message using message hash.");
System.out.println("6. Display a report that lists the full details of all the stored messages.");
System.out.println("7. Return to Main Menu");
System.out.print("\nChoose option: \n");

//accept user choice and implent effect

String userChoice = input.nextLine();
int choice;

try {

choice = Integer.parseInt(userChoice);

} catch (NumberFormatException e) {

System.out.println("\nInvalid choice, please try again.\n");

//loop user until choice is correctly chosen
            continue;

}

if (choice == 1) {

displayPersonalMessages();

} else if (choice == 2) {

displayLongestMessage();

} else if (choice == 3) {

searchByMessageID();

} else if (choice == 4) {

searchByRecipient();

} else if (choice == 5) {

deleteByHash();

} else if (choice == 6) {

displayMessageReport();

} else if (choice == 7) {

running = false;
System.out.println("\nReturning to Main Menu.\n");

} else {

System.out.println("\nInvalid choice, please try again.\n");

     }
   }
}
//choice one: display the sender recipient of all stored messages.

public static void displayPersonalMessages() {

if (storedMessages.isEmpty()) {

System.out.println("\nNo stored messages found.\n");

//loop code to choose different option in main menu
        return;

}

System.out.println("\n  All Stored Messages: Sender & Recipient  \n");

for (int i = 0; i < storedMessages.size(); i++) {

//to display messages

System.out.println("Message " + (i + 1) + ":");
System.out.println("Recipient : " + storedRecipients.get(i));
System.out.println("Message ID: " + messageIDs.get(i));
System.out.println();

    }
}

//choice two: display the longest stored message

public static String displayLongestMessage() {

if (storedMessages.isEmpty()) {

System.out.println("\nNo stored messages found.\n");
return "No messages available.";

}

int longestText = 0;

for (int i = 1; i < storedMessages.size(); i++) {

//checking validity of longest text characters is corrrect

if (storedMessages.get(i).length() > storedMessages.get(longestText).length()) {

longestText = i;

    }
}

//display longest text information to the user

String result = "\n  Longest Stored Message  \n" +
"Message ID : " + messageIDs.get(longestText) + "\n" +
"Recipient : " + storedRecipients.get(longestText) + "\n" +
"Hash : " + messageHashes.get(longestText) + "\n" +
"Message : " + storedMessages.get(longestText) + "\n" +
"Length : " + storedMessages.get(longestText).length() + " characters\n";

System.out.println(result);

//allows thge test to receive and check the output
          return result;

}

//choice three: search for a message ID and display the corresponding recipient and message

public static String searchByMessageID() {

System.out.print("\nEnter the Message ID to search: \n");
String searchID = input.nextLine().trim();

for (int i = 0; i < messageIDs.size(); i++) {

if (messageIDs.get(i).equals(searchID)) {

String result = "\n  Message Found  \n" +
"Recipient: " + storedRecipients.get(i) + "\n" +
"Message: " + storedMessages.get(i) + "\n";

System.out.println(result);

//allows thge test to receive and check the output
          return result;

   }
}

System.out.println("\nNo message found with ID: " + searchID + "\n");

//exit the code and send message to where it is called
    
return "Message ID not found.";

}

//choice four: search for all the messages stored for a particular recipient

public static ArrayList<String> searchByRecipient() {

System.out.print("\nEnter recipient phone number to search: \n");

//accept user input

String searchRecipient = input.nextLine().trim();

ArrayList<String> results = new ArrayList<>();

System.out.println("\n  Messages for Recipient: " + searchRecipient + "  \n");

boolean found = false;

for (int i = 0; i < storedRecipients.size(); i++) {

if (storedRecipients.get(i).equals(searchRecipient)) {

String entry = "Message ID : " + messageIDs.get(i) + "\n" +
"Hash: " + messageHashes.get(i) + "\n" +
"Message: " + storedMessages.get(i);

//print actual message details: the message ID, message hash, and the actual stored message

System.out.println(entry);

// prints a blank space to be presentable

System.out.println();

//enters entry string to the array list so that all matching messages get collected together

results.add(entry);

//makes the progran know that the found variable from false to true that at least one match was found

found = true;

    }
}

if (!found) {

System.out.println("No messages found for recipient: " + searchRecipient + "\n");

}

return results;

}

//choice five: delete a message using the message hash

public static boolean deleteByHash() {

System.out.print("\nEnter the Message Hash to delete: \n");
String textHash = input.nextLine().trim();

//validity of entered messgae hash to be searched and deleted from program

for (int i = 0; i < messageHashes.size(); i++) {

if (messageHashes.get(i).equals(textHash)) {
 //display message to be deleted
 
System.out.println("\nMessage found:");
System.out.println("Message ID: " + messageIDs.get(i));
System.out.println("Recipient: " + storedRecipients.get(i));
System.out.println("Message: " + storedMessages.get(i));
System.out.println("\nRespond Yes or No to the request sent.");
System.out.println("\nAre you sure you want to delete this message?: ");

String confirm = input.nextLine().trim().toLowerCase();

if (confirm.equals("yes")) {

//deleting all traces of message

storedMessages.remove(i);
messageHashes.remove(i);
messageIDs.remove(i);
storedRecipients.remove(i);

saveUpdatedMessages();

System.out.println("\nMessage deleted successfully.\n");

//exit code
          return true;

    
} else {

System.out.println("\nDeletion cancelled.\n");

//break the code execution
                return false;

        }
    }
}

System.out.println("\nNo message found with hash: " + textHash + "\n");

//break the code execution
    return false;
}

// helper method to save updated arrays back to JSON after being deleted

private static void saveUpdatedMessages() {

JSONArray updatedArray = new JSONArray();

for (int i = 0; i < storedMessages.size(); i++) {

JSONObject obj = new JSONObject();

obj.put("MessageID", Long.parseLong(messageIDs.get(i)));
obj.put("Recipient", storedRecipients.get(i));
obj.put("Message", storedMessages.get(i));
obj.put("MessageHash", messageHashes.get(i));

updatedArray.put(obj);

}

try {

java.io.FileWriter fw = new java.io.FileWriter("messages.json");
fw.write(updatedArray.toString(4));

fw.close();

System.out.println("JSON file updated successfully.\n");

} catch (IOException e) {

System.out.println("Error saving updated messages to file.\n");

    }
}

//choice six: display full message report

public static String displayMessageReport() {

if (storedMessages.isEmpty()) {

System.out.println("\nNo stored messages to display.\n");
return "No messages available.";

    
}

StringBuilder report = new StringBuilder();

report.append("\n   Full Message Report   \n");
report.append("Total Messages: ").append(storedMessages.size()).append("\n");

for (int i = 0; i < storedMessages.size(); i++) {

report.append("Message ").append(i + 1).append(":\n");
report.append("Message ID: ").append(messageIDs.get(i)).append("\n");
report.append("Recipient: ").append(storedRecipients.get(i)).append("\n");
report.append("Hash: ").append(messageHashes.get(i)).append("\n");
report.append("Message: ").append(storedMessages.get(i)).append("\n");

}

System.out.println(report.toString());

return report.toString();

}

//help unit tests

public static ArrayList<String> getStoredMessages() {
    return storedMessages;
}

public static ArrayList<String> getMessageHashes() {
    return messageHashes;
}

public static ArrayList<String> getMessageIDs() {
    return messageIDs;
}

public static ArrayList<String> getStoredRecipients() {
    return storedRecipients;
}
}
