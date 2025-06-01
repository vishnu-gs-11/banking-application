package com.BankingApplication;

import com.BankingApplication.entity.User;
import com.BankingApplication.service.Service;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
    private static Scanner scanner = new Scanner(System.in);
    static App app = new App();
    static Service service = new Service();

    public static void main(String[] args) {
        while (true) {
            System.out.println("Enter your username");
            String username = scanner.next();

            System.out.println("Enter your password");
            String password = scanner.next();

            User user = service.login(username, password);
            if (user != null && user.getRole().equals("admin")) {
                app.initAdmin();
            } else if (user != null && user.getRole().equals("user")) {
                app.initCustomer(user);
            } else {
                System.out.println("Login failed");
            }
        }
    }

    private void initAdmin() {

        boolean flag = true;
        String userId = "";

        while (flag) {
            System.out.println("1. Exit/Logout");
            System.out.println("2. Create a customer account");
            System.out.println("3. See all transactions");
            System.out.println("4. Check bank balance");
            System.out.println("5. Approve cheque book request");

            int selectedOption = scanner.nextInt();

            switch (selectedOption) {
                case 1:
                    flag = false;
                    System.out.println("You have successfully logged out...");
                    break;
                case 2:
                    app.addNewCustomer();
                    break;
                case 3:
                    System.out.println("Enter user id");
                    userId = scanner.next();
                    printTransactions(userId);
                    break;
                case 4:
                    System.out.println("Enter user id");
                    userId = scanner.next();
                    Double accountBalance = checkBankBalance(userId);
                    System.out.println("Your account balance is " + accountBalance);
                    break;
                case 5:
                    List<String> userIds = getUserIdForCheckBookRequest();
                    System.out.println("Please select user id from below..");
                    System.out.println(userIds);
                    userId = scanner.next();
                    approveChequeBookRequest(userId);
                    System.out.println("Chequebook request is approved..");
                    break;
                default:
                    System.out.println("Wrong choice");
            }
        }
    }

    private void approveChequeBookRequest(String userId) {
        service.approveChequeBookRequest(userId);
    }

    private List<String> getUserIdForCheckBookRequest() {
        return service.getUserIdForCheckBookRequest();
    }

    private void addNewCustomer() {
        System.out.println("Enter username");
        String username = scanner.next();

        System.out.println("Enter password");
        String password = scanner.next();

        System.out.println("Enter contact number");
        String contact = scanner.next();

        boolean result = service.addNewCustomer(username, password, contact);

        if (result) {
            System.out.println("Customer account is created...");
        } else {
            System.out.println("Customer account creation failed...");
        }
    }

    private void initCustomer(User user) {
        boolean flag = true;

        while (flag) {
            System.out.println("1. Exit/Logout");
            System.out.println("2. Check bank balance");
            System.out.println("3. Fund transfer");
            System.out.println("4. See all transactions");
            System.out.println("5. Raise cheque book request");

            int selectedOption = scanner.nextInt();

            switch (selectedOption) {
                case 1:
                    flag = false;
                    System.out.println("You have successfully logged out...");
                    break;
                case 2:
                    Double balance = app.checkBankBalance(user.getUsername());
                    if (balance != null) {
                        System.out.println("Your bank balance is " + balance);
                    } else {
                        System.out.println("Check your username");
                    }
                    break;
                case 3:
                    app.fundTransfer(user);
                    break;
                case 4:
                    app.printTransactions(user.getUsername());
                    break;
                case 5:
                    String userId = user.getUsername();
                    Map<String, Boolean> map = getAllChequeBookRequest();

                    if (map.containsKey(userId) && map.get(userId)) {
                        System.out.println("You have already raised a request and it is already approved");
                    } else if (map.containsKey(userId) && !map.get(userId)) {
                        System.out.println("You have already raised a request and it is pending for approval");
                    } else {
                        raiseChequeBookRequest(userId);
                        System.out.println("Request raised successfully..");
                    }
                    break;
                default:
                    System.out.println("Wrong choice");
            }
        }
    }

    private Map<String, Boolean> getAllChequeBookRequest() {
        return service.getAllChequeBookRequest();
    }

    private void raiseChequeBookRequest(String userId) {
        service.raiseChequeBookRequest(userId);
    }

    private void printTransactions(String userId) {
        service.printTransactions(userId);
    }

    private void fundTransfer(User userDetails) {
        System.out.println("Enter payee account user id");
        String payeeAccountId = scanner.next();
        User user = getUser(payeeAccountId);
        if (user != null) {
            System.out.println("Enter amount to transfer");
            Double amount = scanner.nextDouble();
            Double userAccountBalance = checkBankBalance(userDetails.getUsername());
            if (userAccountBalance >= amount) {
                boolean result = service.transferAmount(userDetails.getUsername(), payeeAccountId, amount);

                if (result) {
                    System.out.println("Amount transferred successfully..");
                } else {
                    System.out.println("Transfer failed...");
                }
            } else {
                System.out.println("Your balance is insufficient: " + userAccountBalance);
            }
        } else {
            System.out.println("Please enter valid username...");
        }
    }

    private User getUser(String userId) {
        return service.getUser(userId);
    }

    private Double checkBankBalance(String userId) {
        return service.checkBankBalance(userId);
    }
}
