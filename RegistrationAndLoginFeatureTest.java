/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.registrationandloginfeature;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class RegistrationAndLoginFeatureTest {

    //username validity test

    @Test
    public void testUsernameCorrectlyFormatted() {
        boolean result = RegistrationAndLoginFeature.checkUsername("kyl_1");
        assertTrue(result);
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        boolean result = RegistrationAndLoginFeature.checkUsername("kyle!!!!!!");
        assertFalse(result);
    }

    //password validity test

    @Test
    public void testPasswordMeetsComplexity() {
        String result = RegistrationAndLoginFeature.checkPassword("Ch&&sec@ke99!");
        assertEquals("Password successfully captured.", result);
    }

    @Test
    public void testPasswordFailsComplexity() {
        String result = RegistrationAndLoginFeature.checkPassword("password");
        assertEquals(
            "Password is not correctly formatted; please ensure that your password contains at least eight characters, a capital letter, a number, and a special character.",
            result
        );
    }

    //cell phone validity test

    @Test
    public void testCellPhoneCorrect() {
        String result = RegistrationAndLoginFeature.checkCellPhone("+27838968976");
        assertEquals("Cell number successfully captured.", result);
    }

    @Test
    public void testCellPhoneIncorrect() {
        String result = RegistrationAndLoginFeature.checkCellPhone("0838968976");
        assertEquals(
            "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.",
            result
        );
    }

    //login validity test

    @Test
    public void testLoginSuccess() {
        RegistrationAndLoginFeature.usernameRegister = "kyl_1";
        RegistrationAndLoginFeature.registerPassword = "Ch&&sec@ke99!";

        boolean result = RegistrationAndLoginFeature.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertTrue(result);
    }

    @Test
    public void testLoginFail() {
        RegistrationAndLoginFeature.usernameRegister = "kyl_1";
        RegistrationAndLoginFeature.registerPassword = "Ch&&sec@ke99!";

        boolean result = RegistrationAndLoginFeature.loginUser("wrong", "wrong");
        assertFalse(result);
    }
}