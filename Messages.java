/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registrationandloginfeature;

/**
 *
 * @author Student
 */
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileWriter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Messages {
   
//to accept user data and save that data
    
static Scanner input = new Scanner(System.in);

//arraylist to stor users messages and data

static ArrayList <String> messages = new ArrayList<>();
static ArrayList <String> recipients = new ArrayList<>();
static ArrayList <String> messagesHash = new ArrayList<>();


static int messageCount = 0;


//methods to check validity of each 

public static boolean checkMessageID(String messageID) {
    
// must not exceed 10 characters

return messageID != null && messageID.length() <= 10;

}

public static boolean checkRecipientCell(String recipientCell) {
    
// must be 9 digits long.

return recipientCell != null && recipientCell.matches("^\\d{9}$");
}

public static String createMessageHash(int messageID, int index, String message) {

String[] words = message.trim().split(" ");

String firstWord = words[0].toUpperCase();
String lastWord = words[words.length - 1].toUpperCase();

return String.valueOf(messageID).substring(0, 2) + ":" + index + ":" + firstWord + lastWord;
}

public static void sendMessages() {
    
//declare before initializing my saved old messages

JSONArray messageArray;

//to store old messages and display them

//checks if the file already excists

try {
    
String content = Files.readString(Paths.get("messages.json"));

//load old messages
messageArray = new JSONArray(content);

} catch (IOException | JSONException e) {
    
//if file does not exist create new array

messageArray = new JSONArray();

}

//prompt user limit

System.out.print("\nHow many messages would you want to send: \n");

int messageOverall;

try {
    
messageOverall = Integer.parseInt(input.nextLine());

} catch (NumberFormatException e)  {
    
System.out.println("\nInvalid number entered.\n");
return;
}

//increase user message limit and loop to ask user

for (int i = 0; i < messageOverall; i++) {
    
System.out.println("\nMessage " + (i + 1));

//recepient personal data

String recipientNum;

while (true) {

//receivers phone number 

System.out.println("\nEnter recipients phone number which is 9 digits long.\n");

System.out.print("\nEnter recipient number:(+27) \n");
recipientNum = input.nextLine();

//regex number validity + phone numbers validated with international code 

Pattern pattern = Pattern.compile("^\\d{9}$");
Matcher matcher = pattern.matcher(recipientNum);

if (matcher.matches()) {
    
System.out.println("\nCell Phone number successfully added. \n");

// end code
                  break;
                  
} else {
    
System.out.println("\nCell phone number incorrectly formatted or does not contain international code.\n");

 }
}

//message vlidation

String userMessage;

//sending messages

while (true) {

System.out.print("Enter message: ");
userMessage = input.nextLine();

//if user sends empty text

if (userMessage.trim().isEmpty()) {
    
System.out.println("Message cannot be an empty text.");

//to try again
                 continue;
}
//if user overexceeds their own given message limit

if (userMessage.length() <= 250) {
    
// end code
                  break;
} else {
    
System.out.println("\nPlease enter a message of less than 250 characters.\n");

  }
}

//create random ID class for user messages

Random random = new Random();
int messageID = 100000000 + random.nextInt(900000000);

//create hash

String[] words = userMessage.split(" ");

String hash = String.valueOf(messageID).substring(0, 2) + ":" + (i+1) + ":" + words[0].toUpperCase() + words[words.length - 1].toUpperCase();

//store data in the above array lists

messages.add(userMessage);
recipients.add(recipientNum);
messagesHash.add(hash);

messageCount++;

//create JSON object

//JSON object message

JSONObject messageObject = new JSONObject();

messageObject.put("MessageID",messageID);
messageObject.put("Recipient", recipientNum);
messageObject.put("Message", userMessage);
messageObject.put("MessageHash", hash);

//menu options

System.out.println("\n   Messages Menu   \n");
System.out.println("1. Send");
System.out.println("2. Store");
System.out.println("3. Disregard");
System.out.print("\nChoose action:\n");

String option = input.nextLine();

switch (option) {
 
//displayed results of selected option
    
case "1":
    
messageArray.put(messageObject);

System.out.println("\nMessage has been successfully sent.\n");

//display message details to user

System.out.println("\nMessage sent: \n" + userMessage);
System.out.println("\nMessage Hash: \n" + hash);

//end code
            break;
                      
case "2":

try {
    
Files.writeString(Paths.get("messages.txt"), userMessage + System.lineSeparator(), java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);

System.out.println("Mesages stored successfuly.");

} catch (IOException e){
    
System.out.println("Error storing messages.\n");

}

//end code
           break;

case "3": 
    
System.out.println("\nMessage disregarded successfully.\n");
    
if (!messages.isEmpty()) {
    
messages.remove(messages.size() - 1);
recipients.remove(recipients.size() - 1);
messagesHash.remove(messagesHash.size() - 1);

}

//end code
          break;
          
default:
    
System.out.println("\nInvalid, please choose valid option.\n");

    }
  }

//save JSON file

try {
    
FileWriter fileWriter =new FileWriter("messages.json");

fileWriter.write(messageArray.toString(4));

fileWriter.close();

System.out.println("\nMessage saved to JSON file.\n");

} catch (IOException e) {
    
System.out.println("\nError writing to file.\n");

  }
}

//send messages code to display stored messages

public static void displayMessages() {
    
 if (messages.isEmpty()) {
     
System.out.println("No stored messages.");

 } else {
     
System.out.println("\nStored Messages:");

for (int i = 0; i < messages.size(); i++) {
    
System.out.println("\n   Message History   ");
System.out.println("Recipient:" + recipients.get(i));
System.out.println("Message:" + messages.get(i));

    }
  }
}

public static void deleteMessage(int index) {
    
if (index>= 0 && index < messages.size()) {
    
messages.remove(index);
recipients.remove(index);

System.out.println("Messages deleted successfully.");

} else {
    
System.out.println("Invalid MessageID.");

 }
}

public static String printMessages() {

if (messages.isEmpty()) {
        
return "No messages sent.";
    
}

StringBuilder output = new StringBuilder();

for (int i = 0; i < messages.size(); i++) {
        
output.append("\nMessage ").append(i + 1).append("\nRecipient: ").append(recipients.get(i)).append("\nMessage: ").append(messages.get(i)).append("\nHash: ").append(messagesHash.get(i)).append("\n----------------------");
    }

    return output.toString();
}

public static int returnTotalMessages() {
    return messageCount;

  }
}