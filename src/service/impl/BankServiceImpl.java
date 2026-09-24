package service.impl;

import domain.Account;
import domain.Customer;
import domain.Transaction;
import domain.Type;
import repository.AccountRepository;
import repository.CustomerRepository;
import repository.TransactionRepository;
import service.BankService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BankServiceImpl implements BankService {

    private final AccountRepository accountRepository = new AccountRepository();
    private final TransactionRepository transactionRepository = new TransactionRepository();
    private final CustomerRepository customerRepository = new CustomerRepository();

    @Override
    public String openAccount(String name, String email, String accountType) {
        String customerId = UUID.randomUUID().toString();
        // Create customer
        Customer customer = new Customer(email,customerId,name);
        customerRepository.save(customer);
        //String accountNumber = UUID.randomUUID().toString();
        String accountNumber = getAccountNumber();
        Account account = new Account(accountNumber,accountType, (double) 0,customerId);
        accountRepository.addAccount(account);
        return accountNumber;
    }

    @Override
    public List<Account> listAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public void deposit(String accountNumber, Double amount, String note) {
        Account account = accountRepository.findByNumber(accountNumber).orElseThrow(()-> new RuntimeException("Account not found"));
        account.setBalance(account.getBalance() + amount);
        Transaction transaction = new Transaction(account.getAccountNumber(),amount,UUID.randomUUID().toString(),note, LocalDateTime.now(), Type.DEPOSIT);
        transactionRepository.add(transaction);
    }

    @Override
    public void withdraw(String accountNumber, Double amount, String note) {
        Account account = accountRepository.findByNumber(accountNumber).orElseThrow(()-> new RuntimeException("Account not found"));
        if (account.getBalance().compareTo(amount) <0){
            throw new RuntimeException("Insufficient balance");
        }
        account.setBalance(account.getBalance() - amount);
        Transaction transaction = new Transaction(account.getAccountNumber(),amount,UUID.randomUUID().toString(),note, LocalDateTime.now(), Type.WITHDRAW);
        transactionRepository.add(transaction);
    }

    @Override
    public void transfer(String fromAcc, String toAcc, Double amount, String note) {
        if(fromAcc.equals(toAcc)){
            throw new RuntimeException("Cannot transfer from your own account account");
        }
        Account from = accountRepository.findByNumber(fromAcc).orElseThrow(()-> new RuntimeException("Account not found"));
        Account to = accountRepository.findByNumber(toAcc).orElseThrow(()-> new RuntimeException("Account not found"));
        if(from.getBalance().compareTo(amount) <0){
            throw new RuntimeException("Insufficient balance");
        }
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
        Transaction fromTransaction = new Transaction(from.getAccountNumber(),amount,UUID.randomUUID().toString(),note,LocalDateTime.now(),Type.TRANSFER_OUT);
        transactionRepository.add(fromTransaction);
        Transaction toTransaction = new Transaction(to.getAccountNumber(),amount,UUID.randomUUID().toString(),note,LocalDateTime.now(),Type.TRANSFER_IN);
        transactionRepository.add(toTransaction);
    }

    @Override
    public List<Transaction> getStatement(String accountNumber) {
        return transactionRepository.findByAccount(accountNumber).stream().sorted(Comparator.comparing(Transaction::getTimestamp)).collect(Collectors.toList());
    }

    @Override
    public List<Account> searchNameByCustomerName(String name) {
        String query = (name == null) ? "" : name.toLowerCase();
        List<Account> result = new ArrayList<>();
        for(Customer c : customerRepository.findAll()){
            if(c.getCustomerName().toLowerCase().contains(query)){
                result.addAll(accountRepository.findByCustomerId(c.getCustomerId()));
            }
        }
        result.sort(Comparator.comparing(Account::getAccountNumber));
        return result;
    }

    private String getAccountNumber() {
        int size = accountRepository.findAll().size() + 1;
        return String.format("AC%06d",size);
    }
}
