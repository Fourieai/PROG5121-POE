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

public class Login {
    
//accept user data into program 

static Scanner input = new Scanner(System.in);

public static void openMenu() {

//to validate true or false statements

boolean running = true;

//logo of app 

System.out.println("""
       
--------------------------------
|                               |
|          QUICKCHAT            |
| because silence is overrated. |
|                               |
--------------------------------
""");

System.out.println("Welcome to QuickChat.");

while (running) {
    
//display menu

System.out.println("\n  Main Menu  \n ");
System.out.println("1.Send Messages");
System.out.println("2.Show recent messages");
System.out.println("3.Quit");

System.out.print("\nChoose Option: \n");

String userChoice = input.nextLine();

int choice;

//to prevent loop in "invalid option"

try {
    
choice = Integer.parseInt(userChoice);

} catch (NumberFormatException e) {
    
System.out.println("\nInvalid choice, please try again.\n");
continue;
}

System.out.println("\nOption you chose: \n" + choice);

//selection main menu

if (choice == 1) {
                  
System.out.println("\nYou selected Send Messages. \n");
System.out.println("\nThis feature will be implemented here. \n");

//transoprtto send messages

Messages.sendMessages();

}
else if (choice == 2) {
     
System.out.println("\nYou selected show recent messages.\n");

//show recent messages
Messages.displayMessages();
    
 }
 
else if(choice == 3 ) {
     
//exit option

running = false;

System.out.println("\nGood Bye!\n");

//end code
          break;
                  
 } else {
     
System.out.println("\nInvalid choice, please try again.");

} 
    }
  }
}

