package repository;

import domain.Transaction;

import java.util.*;

public class TransactionRepository {
    private final Map<String, List<Transaction>> transactionsByAccount = new HashMap<>();

    public void add(Transaction transaction) {
       List<Transaction> list =  transactionsByAccount.computeIfAbsent(transaction.getAccountNumber(),k-> new ArrayList<>());
       list.add(transaction);
    }

    public List<Transaction> findByAccount(String accountNumber) {
        return new ArrayList<>(transactionsByAccount.getOrDefault(accountNumber, Collections.emptyList()));
    }
}
