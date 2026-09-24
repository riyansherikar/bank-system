package app;

import service.BankService;
import service.impl.BankServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        BankService bankService = new BankServiceImpl();
        System.out.println("Welcome to BANK");
        boolean running = true;
        while (running) {
            System.out.println("""
                1. Open Account
                2. Diposit
                3. Withdraw
                4. Transfer
                5. Account Satatement
                6. List Accounts
                7. Search Account By Customer Name
                0. Exit
                """);
            System.out.print("Please enter your choice ");
            String choice = input.nextLine().trim();
            System.out.println("Your choice is " + choice);
            switch (choice) {
                case "1" -> openAccount(input,bankService);
                case "2" -> deposit(input,bankService);
                case "3" -> withdraw(input,bankService);
                case "4" -> transfer(input,bankService);
                case "5" -> statement(input,bankService);
                case "6" -> listAccount(input,bankService);
                case "7" -> searchByName(input,bankService);
                case "0"-> running = false;
            }
        }



    }

    private static void openAccount(Scanner input,BankService bankService) {
        System.out.println("Customer Name: ");
        String name = input.nextLine().trim();
        System.out.println("Customer email: ");
        String email = input.nextLine().trim();
        System.out.println("Accont type: ");
        String accountType = input.nextLine().trim();
        System.out.println("Initial Deposit: ");
        String amountStr = input.nextLine().trim();
        double initialDeposit = Double.parseDouble(amountStr);
        String accountNumber = bankService.openAccount(name,email,accountType);
        if(initialDeposit > 0)
            bankService.deposit(accountNumber, initialDeposit,"Initial Deposit");
        System.out.println("Account opened with account number " + accountNumber);
    }

    private static void deposit(Scanner input,BankService bankService) {
        System.out.println("Account Number: ");
        String accountNumber = input.nextLine().trim();
        System.out.println("Amount to deposit: ");
        Double amount = Double.valueOf(input.nextLine().trim());
        bankService.deposit(accountNumber,amount,"Deposit");
        System.out.println("Amount deposited with account number " + accountNumber);
    }

    private static void withdraw(Scanner input,BankService bankService) {
        System.out.println("Account Number: ");
        String accountNumber = input.nextLine().trim();
        System.out.println("Amount to withdraw: ");
        Double amount = Double.valueOf(input.nextLine().trim());
        bankService.withdraw(accountNumber,amount,"withdraw");
        System.out.println("Withdrawn");
    }

    private static void transfer(Scanner input,BankService bankService) {
        System.out.println("From Account: ");
        String from = input.nextLine().trim();
        System.out.println("To Account: ");
        String to = input.nextLine().trim();
        System.out.println("Amount to transfer: ");
        Double amount = Double.valueOf(input.nextLine().trim());
        bankService.transfer(from,to,amount,"Transfer");
        System.out.println("Transfer successful");
    }

    private static void statement(Scanner input,BankService bankService) {
        System.out.println("Enter account number: ");
        String accountNumber = input.nextLine().trim();
        bankService.getStatement(accountNumber).forEach(transaction ->  System.out.println(transaction.getTimestamp()+" | "+transaction.getTransactionType()+" | "+transaction.getAmount()+" | "+transaction.getNote()));
    }

    private static void listAccount(Scanner input, BankService bankService) {
        bankService.listAccounts().forEach(account -> {
            System.out.println(account.getAccountNumber() +" | "+ account.getAccountType()+" | "+ account.getBalance());
        });
    }

    private static void searchByName(Scanner input,BankService bankService) {
        System.out.println("Enter customer name: ");
        String name = input.nextLine().trim();
        bankService.searchNameByCustomerName(name).forEach(account -> {
            System.out.println(account.getAccountNumber() +" | "+account.getAccountType()+" | "+ account.getBalance());
        });
    }
}
