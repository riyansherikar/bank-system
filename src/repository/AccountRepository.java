package repository;

import domain.Account;
import domain.Customer;

import java.util.*;

public class AccountRepository {
    private final Map<String, Account> accounts =  new HashMap<>();
    public void addAccount(Account account){
        accounts.put(account.getAccountNumber(),account);
    }

    public List<Account> findAll() {
        return new ArrayList<>(accounts.values());
    }

    public Optional<Account> findByNumber(String accountNumber) {
        return Optional.ofNullable(accounts.get(accountNumber));
    }

    public List<Account> findByCustomerId(String customerId) {
        List<Account> result = new ArrayList<>();
        for(Account a : accounts.values()){
            if(a.getCustomerId().equals(customerId)){
                result.add(a);
            }
        }
        return result;
    }
}
