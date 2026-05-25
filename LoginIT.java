package com.mycompany.registrationandloginfeature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class LoginIT {

    //captures console output so we can assert on printed text
    
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() {
        
        //redirect System.out to capture printed output
        
        outputStream = new ByteArrayOutputStream();
        originalOut  = System.out;
        System.setOut(new PrintStream(outputStream));

        // Clear Messages state before each test
        Messages.messages.clear();
        Messages.recipients.clear();
        Messages.messagesHash.clear();
        Messages.messageCount = 0;
    }

    //restore System.out after each test
    
    @org.junit.jupiter.api.AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    //helper: feed simulated user input into System.in
    
    private void provideInput(String data) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data.getBytes());
        System.setIn(inputStream);
        // Re-initialize the Scanner in Login to pick up the new input stream
        Login.input = new java.util.Scanner(System.in);
    }
    
    // app Launch / welcome Message Tests
    
    @Test
    public void testWelcomeMessageDisplayed() {
        provideInput("3\n");
        Login.openMenu();

        String output = outputStream.toString();
        assertTrue(output.contains("Welcome to QuickChat."),
            "App should display welcome message on launch.");
    }

    @Test
    public void testAppLogoDisplayed() {
        provideInput("3\n");
        Login.openMenu();

        String output = outputStream.toString();
        assertTrue(output.contains("QUICKCHAT"),
            "App banner should display QUICKCHAT on launch.");
    }
    
    //main menu display tests
   
    @Test
    public void testMainMenuOptionsDisplayed() {
        provideInput("3\n");
        Login.openMenu();

        String output = outputStream.toString();
        assertTrue(output.contains("1.Send Messages"),       "Menu should show option 1.");
        assertTrue(output.contains("2.Show recent messages"),"Menu should show option 2.");
        assertTrue(output.contains("3.Quit"),                "Menu should show option 3.");
    }

    //option 1 — send messages
    
    @Test
    public void testOption1_SendMessages_Selected() {
        // Input: choose 1 → send 0 messages → then quit (3)
        provideInput("1\n0\n3\n");
        Login.openMenu();

        String output = outputStream.toString();
        assertTrue(output.contains("You selected Send Messages."),
            "Selecting option 1 should confirm Send Messages.");
    }

    //option 2 — show recent messages
    
    @Test
    public void testOption2_ShowRecentMessages_Empty() {
        provideInput("2\n3\n");
        Login.openMenu();

        String output = outputStream.toString();
        assertTrue(output.contains("You selected show recent messages."),
            "Selecting option 2 should confirm Show Recent Messages.");
        assertTrue(output.contains("No stored messages."),
            "Should display 'No stored messages.' when list is empty.");
    }

    @Test
    public void testOption2_ShowRecentMessages_WithData() {
        // Pre-load a message into the static lists
        Messages.messages.add("Hi Mike, can you join us for dinner tonight?");
        Messages.recipients.add("718693002");
        Messages.messagesHash.add("00:1:HITONIGHT");

        provideInput("2\n3\n");
        Login.openMenu();

        String output = outputStream.toString();
        assertTrue(output.contains("718693002"),
            "Recipient number should appear in recent messages.");
        assertTrue(output.contains("Hi Mike"),
            "Message text should appear in recent messages.");
    }

    //option 3 — Quit
    
    @Test
    public void testOption3_Quit() {
        provideInput("3\n");
        Login.openMenu();

        String output = outputStream.toString();
        assertTrue(output.contains("Good Bye!"),
            "Selecting option 3 should display 'Good Bye!' and exit.");
    }

    //invalid input tests
    
    @Test
    public void testInvalidNumberOption_ShowsError() {
        // Enter invalid option 99, then quit with 3
        provideInput("99\n3\n");
        Login.openMenu();

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid choice, please try again."),
            "Invalid numeric option should show error message.");
    }

    @Test
    public void testInvalidNonNumericInput_ShowsError() {
        // Enter text input, then quit
        provideInput("abc\n3\n");
        Login.openMenu();

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid choice, please try again."),
            "Non-numeric input should display invalid choice message.");
    }

    @Test
    public void testEmptyInput_ShowsError() {
        provideInput("\n3\n");
        Login.openMenu();

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid choice, please try again."),
            "Empty input should display invalid choice message.");
    }

    // chosen option test
    
    @Test
    public void testChosenOptionIsEchoed() {
        provideInput("2\n3\n");
        Login.openMenu();

        String output = outputStream.toString();
        assertTrue(output.contains("Option you chose:"),
            "App should echo back the user's chosen option.");
    }
}