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
        assertTrue(BankAccount.isEmailValid("k.free.man@ithaca.edu"));  // can use more than one period just not consecutively 


        assertFalse(BankAccount.isEmailValid(""));                         // empty string
        assertFalse(BankAccount.isEmailValid("k..freeman@ithaca.edu"));    // consecutive periods 
        assertFalse(BankAccount.isEmailValid("kfreeman@i"));             // less than 2 char domain 
        assertFalse(BankAccount.isEmailValid("-kfreeman@ithaca.edu"));  // special char cannot be at beginning of name
        assertFalse(BankAccount.isEmailValid("kfreeman-@ithaca.edu"));  // special char cannot be at end of name  

        
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