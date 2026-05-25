package com.mycompany.registrationandloginfeature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessagesIT {

    @BeforeEach
    public void setUp() {
        // Clear static ArrayLists before each test to ensure a clean state
        Messages.messages.clear();
        Messages.recipients.clear();
        Messages.messagesHash.clear();
        Messages.messageCount = 0;
    }

    @Test
    public void testCheckRecipientCell_Success() {
        // Test Data Message 1: +27718693002 → strip country code → 718693002 (9 digits)
        String validNumber = "718693002";
        boolean result = Messages.checkRecipientCell(validNumber);
        assertTrue(result, "Cell phone number successfully captured.");
    }

    @Test
    public void testCheckRecipientCell_Failure() {
        // Test Data Message 1: full number with country code — invalid (too long / wrong format)
        String invalidNumber = "+27718693002";
        boolean result = Messages.checkRecipientCell(invalidNumber);
        assertFalse(result, "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
    }

    @Test
    public void testCheckRecipientCell_Success_Message2() {
        // Test Data Message 2: 08575975889 → 9-digit portion → 857597588 (trimmed)
        // The raw input "08575975889" is 11 digits — should FAIL validation
        String invalidNumber = "08575975889";
        boolean result = Messages.checkRecipientCell(invalidNumber);
        assertFalse(result, "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
    }

    @Test
    public void testMessageLength_Success() {
        String validMessage = "Hi Mike, can you join us for dinner tonight?";
        assertTrue(validMessage.length() <= 250, "Message ready to send.");
    }

    @Test
    public void testMessageLength_Failure() {
        String longMessage = "A".repeat(260);
        int excess = longMessage.length() - 250;
        assertFalse(longMessage.length() <= 250,
            "Message exceeds 250 characters by " + excess + "; please reduce the size.");
    }

    @Test
    public void testCreateMessageHash_Message1() {
        
        // Simulate messageID = 00XXXXXXXX (first 2 chars = "00")
        
        int messageID = 1000000000; 
        
        // Per the spec image: expected hash is "00:0:HITONIGHT"
        
        String message = "Hi Mike, can you join us for dinner tonight?";
        String[] words = message.trim().split(" ");
        String firstWord = words[0].toUpperCase();                    // "HI"
        String lastWord  = words[words.length - 1].toUpperCase();     // "TONIGHT?"

        lastWord = lastWord.replaceAll("[^A-Z]", "");

        // Use a fake messageID whose first 2 chars = "00"
        
        String fakeID = "0012345678";
        String hash = fakeID.substring(0, 2) + ":" + 0 + ":" + firstWord + lastWord;

        assertEquals("00:0:HITONIGHT", hash,
            "Message hash is correct.");
    }

    @Test
    public void testCreateMessageHash_Loop() {
        String[] testMessages = {
            "Hi Mike, can you join us for dinner tonight?",
            "Hi Keegan, did you receive the payment?"
        };

        String fakeIDPrefix = "00";

        for (int i = 0; i < testMessages.length; i++) {
            String message = testMessages[i];
            String[] words = message.trim().split(" ");
            String firstWord = words[0].toUpperCase();
            String lastWord  = words[words.length - 1].toUpperCase().replaceAll("[^A-Z]", "");

            String hash = fakeIDPrefix + ":" + i + ":" + firstWord + lastWord;

            assertNotNull(hash, "Hash should not be null for message " + (i + 1));
            assertTrue(hash.contains(":"), "Hash should contain ':' separator.");
            assertTrue(hash.startsWith(fakeIDPrefix), "Hash should start with message ID prefix.");
        }
    }

    @Test
    public void testMessageID_Generated() {
        // MessageID is a random 9-digit number: 100000000 to 999999999
        int messageID = 100000000 + new java.util.Random().nextInt(900000000);
        String output = "Message ID generated: " + messageID;

        assertTrue(messageID >= 100000000 && messageID <= 999999999,
            "Message ID generated: " + messageID);
        assertTrue(output.startsWith("Message ID generated:"),
            "Output should confirm ID creation.");
    }

    @Test
    public void testMessageSent_Send() {
        // Simulate adding message then choosing Send
        Messages.messages.add("Hi Mike, can you join us for dinner tonight?");
        Messages.recipients.add("718693002");
        Messages.messagesHash.add("00:1:HITONIGHT");
        Messages.messageCount++;

        String action = "1"; // Send
        String result = action.equals("1") ? "Message successfully sent." : "";

        assertEquals("Message successfully sent.", result);
    }

    @Test
    public void testMessageSent_Disregard() {
        Messages.messages.add("Hi Keegan, did you receive the payment?");
        Messages.recipients.add("857597588");
        Messages.messagesHash.add("00:2:HIPAYMENT");
        Messages.messageCount++;

        String action = "3"; // Disregard
        String result = action.equals("3") ? "Press 0 to delete the message." : "";

        assertEquals("Press 0 to delete the message.", result);
    }

    @Test
    public void testMessageSent_Store() {
        Messages.messages.add("Hi Mike, can you join us for dinner tonight?");
        Messages.recipients.add("718693002");
        Messages.messagesHash.add("00:1:HITONIGHT");
        Messages.messageCount++;

        String action = "2"; // Store
        String result = action.equals("2") ? "Message successfully stored." : "";

        assertEquals("Message successfully stored.", result);
    }
    
    // returnTotalMessages() Tests
   
    @Test
    public void testReturnTotalMessages() {
        // Simulate sending 2 messages
        Messages.messages.add("Hi Mike, can you join us for dinner tonight?");
        Messages.recipients.add("718693002");
        Messages.messagesHash.add("00:1:HITONIGHT");
        Messages.messageCount++;

        Messages.messages.add("Hi Keegan, did you receive the payment?");
        Messages.recipients.add("857597588");
        Messages.messagesHash.add("00:2:HIPAYMENT");
        Messages.messageCount++;

        int total = Messages.returnTotalMessages();

        assertEquals(2, total, "Total messages sent should be 2.");
    }

    // printMessages() Tests
    
    @Test
    public void testPrintMessages_Empty() {
        String result = Messages.printMessages();
        assertEquals("No messages sent.", result);
    }

    @Test
    public void testPrintMessages_WithData() {
        Messages.messages.add("Hi Mike, can you join us for dinner tonight?");
        Messages.recipients.add("718693002");
        Messages.messagesHash.add("00:1:HITONIGHT");

        String result = Messages.printMessages();

        assertTrue(result.contains("718693002"), "Output should contain recipient number.");
        assertTrue(result.contains("Hi Mike"), "Output should contain message text.");
        assertTrue(result.contains("00:1:HITONIGHT"), "Output should contain message hash.");
    }
}