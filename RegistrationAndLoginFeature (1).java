/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.registrationandloginfeature;

/**
 *
 * @author Student
 */
import java.util.Scanner;

public class RegistrationAndLoginFeature {
    
//to save user information
    
static String usernameRegister ="";
static String registerPassword ="";
static String firstName ="";
static String surname ="";
 
//receive information from user that the program will save

static Scanner input = new Scanner(System.in);

 public static void main(String[] args) {

//to put user in loop until correct option is selected

while(true) {
    
//system portal for user selection

System.out.println("\n  Login System  \n");
System.out.println("1. Register User");
System.out.println("2. Login");
System.out.println("3. Exit");
System.out.println("Choose option: ");

//user choice made

int choice = Integer.parseInt(input.nextLine());

//menu selection

if (choice == 1) {
    
//program goes to registration system
registerUsers();

} else if (choice ==2 ) {
    
//program goes to login system
LoginUser();

} else if (choice ==3 ) {
    
 System.out.println("Goodbye!");
 
//to end program

                break;

} else if (choice >=4) {
   
System.out.println("\nInvalid option, please try again.");

} else if (choice <=0) {
    
System.out.println("\nInvalid option, please try again.");
         }  
      }
 }

 //registration method 
 
 public static void registerUsers() {
 
//method purpose

System.out.println("\n  Registration Portal  \n");

//propmpt message for user private information

System.out.print("Enter First Name: ");
firstName = input.nextLine();

System.out.print("Enter Last Nmae: ");
surname = input.nextLine();

//prompt message to let the user username creation process

System.out.println("\n Username must contain an underscore and not be more than five characters long. \n");

//username creation

while(true) {
    
System.out.print("Create Username: ");
usernameRegister = input.nextLine();

//conditions when username is correct

boolean checkUserName = usernameRegister.length() <=5;

//selection statement for username creation

if (usernameRegister.contains("_") && usernameRegister.length() <= 5) {
    
System.out.println("\nUsername successfully captured.");

//program execution end

                       break;
}else {
    
System.out.println("\nUsername is not correctly formatted; ensure that your username contains an underscore and is no more than five characters in length.");

  }
}

//prompt message to let the user the password creation process

System.out.println("\n Password meets the following password complexity rules; the passsword must be: \n");
System.out.println("- At least eight characters long.");
System.out.println("- Contain a capital letter.");
System.out.println("- Contain a number.");
System.out.println("- Contain a special character.");

//users password creation

while (true) {
    
System.out.print("\n Create Password: ");
registerPassword = input.nextLine();

//password requiments

boolean checkPasswordComplexity = 
       
registerPassword.length() >= 8 &&
registerPassword.matches(".*[A-Z].*") &&
registerPassword.matches(".*[0-9].*") &&
registerPassword.matches(".*[*&^%$#@!{}|<>].*");
        
//selection statement whether password correct or not
        
if (checkPasswordComplexity) {
    
System.out.println("\nPassword successfully captured.");

//program successfully executed

                            break;
} else {
    
System.out.println("\nPassword is not correctly formatted; please ensure that your password contains at least eight characters, a capital letter, a number, and a special character.");

       }        
 }

//prompt messsage to let user cell phone creation process

System.out.println("\n The cell phone number contains the international country code (+27) followed by the number, which is no more than ten characters long. \n");

//conditions for cell phone length

while(true) {
    
//user enters information

System.out.print("Enter Phone Number: +27");
String cellPhone01 = input.nextLine();

///conditions for correct format of cell phone number

boolean checkCellPhoneNumber = ("+27" + cellPhone01).matches("^\\+27[0-9]{9}$");

if(checkCellPhoneNumber) {

System.out.println("\nCell Phone number succcessfully added. \n");

//program successfully executed 

                           break;
} else {
    
System.out.println("\nCell phone number incorrectly formatted or does not contain international code.");

       }
    }
 }
 
public static void LoginUser() {
   
//login process

System.out.println("\n  Login To Account  \n");
System.out.print("Enter Username: ");
String userLogin = input.nextLine();

System.out.print("Enter Password: ");
String loginPassword = input.nextLine();

//confirmation of successful login with selection structure

if (userLogin.equals(usernameRegister) && loginPassword.equals(registerPassword))  {
 
//transport to login class

Login.openMenu();

System.out.println("\n Welcome " + firstName + " " + surname + ", it is great to see you again.");

} else {
    
System.out.println("\n Username or password not found, please try again.");

      }
}

//code for Junit testing (method helper)

public static boolean checkUsername(String username) {
    return username.contains("_") && username.length() <= 5;
}

public static String checkPassword(String password) {
    boolean valid = password.length() >= 8 &&
            password.matches(".*[A-Z].*") &&
            password.matches(".*[0-9].*") &&
            password.matches(".*[*&^%$#@!{}|<>].*");

    if (valid) {
        return "Password successfully captured.";
    } else {
        return "Password is not correctly formatted; please ensure that your password contains at least eight characters, a capital letter, a number, and a special character.";
    }
}

public static String checkCellPhone(String phone) {
    if (phone.matches("^\\+27[0-9]{9}$")) {
        return "Cell number successfully captured.";
    } else {
        return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
    }
}

public static boolean loginUser(String username, String password) {
    return username.equals(usernameRegister) && password.equals(registerPassword);
    
   }
}

