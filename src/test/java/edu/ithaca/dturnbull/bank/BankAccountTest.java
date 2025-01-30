package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        // 'middle' case
        assertEquals(200, bankAccount.getBalance(), 0.001);
        
        // 'edge' case 
        bankAccount.withdraw(200);
        assertEquals(0, bankAccount.getBalance());
        

    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        // 'middle' case
        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance(), 0.001);

        // 'edge' case 
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(300));
        bankAccount.withdraw(0);
        assertEquals(100, bankAccount.getBalance());
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-50));
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
    void isAmountValid() {
         // Valid cases
         assertTrue(BankAccount.isAmountValid(0));      // Zero is valid
         assertTrue(BankAccount.isAmountValid(50.25));  // Standard valid amount
         assertTrue(BankAccount.isAmountValid(100));    // Whole number
         assertTrue(BankAccount.isAmountValid(0.99));   // Two decimal places
 
         // Invalid cases
         assertFalse(BankAccount.isAmountValid(-10));    // Negative amount
         assertFalse(BankAccount.isAmountValid(-0.01));  // Slightly negative
         assertFalse(BankAccount.isAmountValid(5.999));  // More than two decimal places
         assertFalse(BankAccount.isAmountValid(123.456));// More than two decimal places
     }


    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

    @Test
    void depositTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.deposit(25.00);
        assertEquals(225.00,bankAccount.getBalance());
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(-5.00));

    }

    @Test
    void transferTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        BankAccount bankAccount1 = new BankAccount("a@b.com", 200);
        BankAccount bankAccount2 = new BankAccount("kfreeman@ithaca.com", 200);
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.transfer(bankAccount1, 10));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.transfer(bankAccount2, 300));

        
    }

}