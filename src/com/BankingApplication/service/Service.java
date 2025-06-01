package com.BankingApplication.service;

import com.BankingApplication.entity.User;
import com.BankingApplication.repository.Repository;

import java.util.List;
import java.util.Map;

public class Service {
    private Repository repository = new Repository();

    public void printUsers() {
        repository.printUsers();
    }

    public User login(String username, String password) {
        return repository.login(username, password);
    }

    public boolean addNewCustomer(String username, String password, String contact) {
        return repository.addNewCustomer(username, password, contact);
    }

    public Double checkBankBalance(String userId) {
        return repository.checkBankBalance(userId);
    }

    public User getUser(String userId) {
        return repository.getUser(userId);
    }

    public boolean transferAmount(String userId, String payeeUserId, Double amount) {
        return repository.transferAmount(userId, payeeUserId, amount);
    }

    public void printTransactions(String userId) {
        repository.printTransactions(userId);
    }

    public void raiseChequeBookRequest(String userId) {
        repository.raiseChequeBookRequest(userId);
    }

    public Map<String, Boolean> getAllChequeBookRequest() {
        return repository.getAllChequeBookRequest();
    }

    public List<String> getUserIdForCheckBookRequest() {
        return repository.getUserIdForCheckBookRequest();
    }

    public void approveChequeBookRequest(String userId) {
        repository.approveChequeBookRequest(userId);
    }
}
