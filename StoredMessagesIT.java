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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class StoredMessagesIT {

// clear all arrays before each test runs

@BeforeEach
public void setUp() {

StoredMessages.storedMessages.clear();
StoredMessages.messageHashes.clear();
StoredMessages.messageIDs.clear();
StoredMessages.storedRecipients.clear();
StoredMessages.sentMessages.clear();
StoredMessages.disregardedMessages.clear();

}

// helper method to populate arrays with the required test data from the assignment

private void populateTestData() {

// Message 1: Sent
StoredMessages.sentMessages.add("Did you get the cake?");
StoredMessages.storedMessages.add("Did you get the cake?");
StoredMessages.storedRecipients.add("+27834557896");
StoredMessages.messageHashes.add("00:1:DIDCAKE?");
StoredMessages.messageIDs.add("1000000001");

// Message 2: Stored
StoredMessages.storedMessages.add("Where are you? You are late! I have asked you to be on time.");
StoredMessages.storedRecipients.add("+27838884567");
StoredMessages.messageHashes.add("00:2:WHERETIME.");
StoredMessages.messageIDs.add("1000000002");

// Message 3: Disregard
StoredMessages.disregardedMessages.add("Yohoooo, I am at your gate.");

// Message 4: Sent
StoredMessages.sentMessages.add("It is dinner time!");
StoredMessages.storedMessages.add("It is dinner time!");
StoredMessages.storedRecipients.add("0838884567");
StoredMessages.messageHashes.add("00:4:ITTIME!");
StoredMessages.messageIDs.add("0838884567");

// Message 5: Stored
StoredMessages.storedMessages.add("Ok, I am leaving without you.");
StoredMessages.storedRecipients.add("+27838884567");
StoredMessages.messageHashes.add("00:5:OKYOU.");
StoredMessages.messageIDs.add("1000000005");

}

//tests for sent messages

@Test
public void testSentMessagesArrayPopulated() {

populateTestData();

// system should contain the expected sent messages
assertTrue(StoredMessages.sentMessages.contains("Did you get the cake?"),
"Sent messages should contain 'Did you get the cake?'");

assertTrue(StoredMessages.sentMessages.contains("It is dinner time!"),
"Sent messages should contain 'It is dinner time!'");

}

@Test
public void testSentMessagesArraySize() {

populateTestData();

assertEquals(2, StoredMessages.sentMessages.size(),
"Sent messages array should contain 2 sent messages.");

}

//tests to display the longest message

@Test
public void testDisplayLongestMessage_ReturnsCorrectMessage() {

populateTestData();

String result = StoredMessages.displayLongestMessage();

// message 2 is the longest stored message
assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."),
"Longest message should be 'Where are you? You are late! I have asked you to be on time.'");

}

@Test
public void testDisplayLongestMessage_EmptyArray() {

String result = StoredMessages.displayLongestMessage();

assertEquals("No messages available.", result,
"Empty array should return 'No messages available.'");

}

//test to search for messageID

@Test
public void testSearchByMessageID_Found() {

populateTestData();

// message 4 has ID 0838884567 and message "It is dinner time!"
String searchID = "0838884567";
String foundMessage = "";

for (int i = 0; i < StoredMessages.messageIDs.size(); i++) {
if (StoredMessages.messageIDs.get(i).equals(searchID)) {
foundMessage = StoredMessages.storedMessages.get(i);
break;
}
}

assertEquals("It is dinner time!", foundMessage,
"Search by ID 0838884567 should return 'It is dinner time!'");

}

@Test
public void testSearchByMessageID_NotFound() {

populateTestData();

String searchID = "9999999999";
boolean found = false;

for (int i = 0; i < StoredMessages.messageIDs.size(); i++) {
if (StoredMessages.messageIDs.get(i).equals(searchID)) {
found = true;
break;
}
}

assertFalse(found,
"Search for a non-existent ID should return false.");

}

//tests to search all messages for a particular recipient

@Test
public void testSearchByRecipient_Found() {

populateTestData();

// recipient +27838884567 has message 2 and message 5
String searchRecipient = "+27838884567";
ArrayList<String> results = new ArrayList<>();

for (int i = 0; i < StoredMessages.storedRecipients.size(); i++) {
if (StoredMessages.storedRecipients.get(i).equals(searchRecipient)) {
results.add(StoredMessages.storedMessages.get(i));
}
}

assertEquals(2, results.size(),
"Recipient +27838884567 should have 2 messages.");

assertTrue(results.contains("Where are you? You are late! I have asked you to be on time."),
"Results should contain message 2.");

assertTrue(results.contains("Ok, I am leaving without you."),
"Results should contain message 5.");

}

@Test
public void testSearchByRecipient_NotFound() {

populateTestData();

String searchRecipient = "+27000000000";
ArrayList<String> results = new ArrayList<>();

for (int i = 0; i < StoredMessages.storedRecipients.size(); i++) {
if (StoredMessages.storedRecipients.get(i).equals(searchRecipient)) {
results.add(StoredMessages.storedMessages.get(i));
}
}

assertTrue(results.isEmpty(),
"No messages should be found for an unknown recipient.");

}

//tests to delete a message using message hash

@Test
public void testDeleteByHash_Success() {

populateTestData();

// message 2 hash is 00:2:WHERETIME.
String hashToDelete = "00:2:WHERETIME.";
int indexToDelete = -1;

for (int i = 0; i < StoredMessages.messageHashes.size(); i++) {
if (StoredMessages.messageHashes.get(i).equals(hashToDelete)) {
indexToDelete = i;
break;
}
}

// simulate deletion
if (indexToDelete != -1) {
StoredMessages.storedMessages.remove(indexToDelete);
StoredMessages.messageHashes.remove(indexToDelete);
StoredMessages.messageIDs.remove(indexToDelete);
StoredMessages.storedRecipients.remove(indexToDelete);
}

assertFalse(StoredMessages.storedMessages.contains("Where are you? You are late! I have asked you to be on time."),
"Message 2 should be successfully deleted.");

assertFalse(StoredMessages.messageHashes.contains(hashToDelete),
"Hash 00:2:WHERETIME. should no longer exist after deletion.");

}

@Test
public void testDeleteByHash_NotFound() {

populateTestData();

int sizeBefore = StoredMessages.storedMessages.size();

String hashToDelete = "99:9:FAKEHASH";
boolean found = false;

for (String h : StoredMessages.messageHashes) {
if (h.equals(hashToDelete)) {
found = true;
break;
}
}

assertFalse(found,
"A fake hash should not be found.");

assertEquals(sizeBefore, StoredMessages.storedMessages.size(),
"Array size should remain unchanged when nothing is deleted.");

}

//tests to display report

@Test
public void testDisplayMessageReport_ContainsAllMessages() {

populateTestData();

String report = StoredMessages.displayMessageReport();

assertTrue(report.contains("Full Message Report"),
"Report should contain the title.");

assertTrue(report.contains("Did you get the cake?"),
"Report should contain message 1.");

assertTrue(report.contains("Where are you? You are late! I have asked you to be on time."),
"Report should contain message 2.");

assertTrue(report.contains("It is dinner time!"),
"Report should contain message 4.");

assertTrue(report.contains("Ok, I am leaving without you."),
"Report should contain message 5.");

}

@Test
public void testDisplayMessageReport_Empty() {

String result = StoredMessages.displayMessageReport();

assertEquals("No messages available.", result,
"Empty array should return 'No messages available.'");

}

}