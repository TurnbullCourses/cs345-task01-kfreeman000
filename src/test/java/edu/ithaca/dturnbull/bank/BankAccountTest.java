package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address
        // P: Equivalence class: domain & prefix exist, is an edge case because the prefix and first part of domain are just long enough (could be more of an edge case if the top-level domain was 2 characters)
        assertTrue(BankAccount.isEmailValid("k.free.man@ithaca.edu"));  // can use more than one period just not consecutively 
        // P: Equivalence class: special characters in prefix, is not an edge case because it has 2 periods instead of 1


        assertFalse(BankAccount.isEmailValid(""));                         // empty string
        // P: Equivalence class: email exists, is an edge case because the email does not exist
        assertFalse(BankAccount.isEmailValid("k..freeman@ithaca.edu"));    // consecutive periods 
        // P: Equivalence class: consecutive punctuation in prefix, is an edge case because one less period would be allowed
        assertFalse(BankAccount.isEmailValid("kfreeman@i"));             // less than 2 char domain 
        // P: Equivalence class: top-level domain does not exist, is an edge case
        assertFalse(BankAccount.isEmailValid("-kfreeman@ithaca.edu"));  // special char cannot be at beginning of name
        // P: Equivalence class: special character at beginning of prefix, is an edge case
        assertFalse(BankAccount.isEmailValid("kfreeman-@ithaca.edu"));  // special char cannot be at end of name 
        // P: Equivalence class: special character at beginning of prefix, is the other edge case

        // I think there are two more equivalence classes; the top-level domain being less than two characters, and the email containing illegal characters
        // I would also add a couple edge cases for the other equivalence classes:
        // - there being one dash in the domain
        // - individual parts of the email being empty strings (i.e. "@c.edu")


        
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}