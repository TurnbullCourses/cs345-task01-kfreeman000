package edu.ithaca.dturnbull.bank;

import java.util.List;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        //if (!isAmountValid(startingBalance)) throw new IllegalArgumentException("Amount: " + startingBalance + " is invalid"); // for when isAmountValid is implemented
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * @throws illegalargument exception if withdrawl amount is larger than balance OR if balance is negative 
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount < 0) throw new IllegalArgumentException("Cannot withdraw a negative amount");
        //if (!isAmountValid(amount)) throw new IllegalArgumentException("Amount: " + amount + " is invalid"); // for when isAmountValid is implemented
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }

    /**
     * @post increases the balance by amount if amount is non-negative
     */
    public void deposit (double amount) throws InsufficientFundsException{
        if (amount < 0) throw new IllegalArgumentException("Cannot deposit a negative amount");
        //if (!isAmountValid(amount)) throw new IllegalArgumentException("Amount: " + amount + " is invalid"); // for when isAmountValid is implemented
        balance += amount;
    }

    /**
     * @post increases the balance of the other account and decreases the balance of this account by the amount if it is non-negative and less than the balance of this account
     */
    public void transfer (BankAccount other, int amount) throws InsufficientFundsException{
        withdraw(amount);
        other.deposit(amount);
    }


    private static boolean checkPart(String part, List<Character> allowedPunctuation) {
        if (allowedPunctuation.contains(part.charAt(0)) || allowedPunctuation.contains(part.charAt(part.length()-1))) return false;
        for (int i = 0; i < part.length(); i++) {
            if (!Character.isLetterOrDigit(part.charAt(i)) && !allowedPunctuation.contains(part.charAt(i))) return false;
            if (allowedPunctuation.contains(part.charAt(i)) && allowedPunctuation.contains(part.charAt(i-1))) return false;
        }
        return true;
    }


    public static boolean isEmailValid(String email){
        String[] parts = email.split("@");
        if (parts.length != 2 || parts[0].length() == 0 || parts[1].length() == 0) return false;
        List<Character> allowedPunctuation = List.of('-', '.', '_');
        if (!checkPart(parts[0], allowedPunctuation)) return false;

        String[] domainParts = parts[1].split("\\.");
        if (domainParts.length != 2 || domainParts[0].length() == 0 || domainParts[1].length() == 0) return false;

        List<Character> allowedPunctuationDomain = List.of('-');
        if (!checkPart(domainParts[0], allowedPunctuationDomain)) return false;

        if (domainParts[1].length() < 2) return false;

        if (!checkPart(domainParts[1], allowedPunctuationDomain)) return false;
        return true;
    }
}