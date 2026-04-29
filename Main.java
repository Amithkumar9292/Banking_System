import javax.swing.*;
import java.awt.event.*;


interface AccountOperations {
    void deposit(double amount);
    void withdraw(double amount);
}

abstract class BankAccount implements AccountOperations {
    private double balance;
    private double interestRate;

    public BankAccount(double balance, double interestRate) {
        this.balance = balance;
        this.interestRate = interestRate;
    }

    public final double calculateInterest() {
        return balance * interestRate / 100;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getInterestRate() {
        return interestRate;
    }

    abstract String accountType();
}

class SavingsAccount extends BankAccount {

    public SavingsAccount(double balance, double rate) {
        super(balance, rate); // full interest
    }

    public void deposit(double amount) {
        setBalance(getBalance() + amount);
    }

    public void withdraw(double amount) {
        if (amount <= getBalance()) {
            setBalance(getBalance() - amount);
        } else {
            System.out.println("Insufficient balance in Savings");
        }
    }

    String accountType() {
        return "Savings Account";
    }
}


class CurrentAccount extends BankAccount {

    public CurrentAccount(double balance, double rate) {
        super(balance, 0); // 🔥 No interest for current account
    }

    public void deposit(double amount) {
        setBalance(getBalance() + amount);
    }

    public void withdraw(double amount) {
        setBalance(getBalance() - amount); // overdraft allowed
    }

    String accountType() {
        return "Current Account";
    }
}


public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Banking System");

        JLabel lblBalance = new JLabel("Balance:");
        lblBalance.setBounds(50, 50, 100, 30);

        JTextField txtBalance = new JTextField();
        txtBalance.setBounds(150, 50, 120, 30);

        JLabel lblRate = new JLabel("Interest Rate:");
        lblRate.setBounds(50, 100, 120, 30);

        JTextField txtRate = new JTextField();
        txtRate.setBounds(150, 100, 120, 30);

        String[] options = {"Savings", "Current"};
        JComboBox<String> accountType = new JComboBox<>(options);
        accountType.setBounds(150, 150, 120, 30);

        JButton btnCalculate = new JButton("Calculate Interest");
        btnCalculate.setBounds(80, 200, 200, 30);

        JLabel result = new JLabel("");
        result.setBounds(50, 250, 300, 30);


        btnCalculate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double balance = Double.parseDouble(txtBalance.getText());
                    double rate = Double.parseDouble(txtRate.getText());

                    BankAccount account;

                    if (accountType.getSelectedItem().equals("Savings")) {
                        account = new SavingsAccount(balance, rate);
                    } else {
                        account = new CurrentAccount(balance, rate);
                    }

                    double interest = account.calculateInterest();

                    result.setText(account.accountType() +
                            " Interest: " + String.format("%.2f", interest));

                } catch (Exception ex) {
                    result.setText("Invalid Input!");
                }
            }
        });

        frame.add(lblBalance);
        frame.add(txtBalance);
        frame.add(lblRate);
        frame.add(txtRate);
        frame.add(accountType);
        frame.add(btnCalculate);
        frame.add(result);

        frame.setSize(400, 350);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}