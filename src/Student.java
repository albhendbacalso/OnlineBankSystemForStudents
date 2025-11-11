import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Student {
    private String studentId;
    private String name;
    private String password;
    private double balance;
    private List<String> transactions;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Student(String studentId, String name, String password) {
        this.studentId = studentId;
        this.name = name;
        this.password = password;
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public double getBalance() { return balance; }
    public List<String> getTransactions() { return transactions; }

    public void deposit(double amount) {
        balance += amount;
        String timestamp = LocalDateTime.now().format(formatter);
        transactions.add(timestamp + " — Deposit: ₱" + amount);
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            String timestamp = LocalDateTime.now().format(formatter);
            transactions.add(timestamp + " — Withdraw: ₱" + amount);
            return true;
        }
        return false;
    }
}
