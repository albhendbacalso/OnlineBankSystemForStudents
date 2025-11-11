import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private Student student;
    private JLabel balanceLabel;

    public DashboardFrame(Student student) {
        this.student = student;

        setTitle("Welcome, " + student.getName());
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 5, 5));

        balanceLabel = new JLabel("Balance: ₱" + student.getBalance(), SwingConstants.CENTER);
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton historyBtn = new JButton("Transaction History");
        JButton logoutBtn = new JButton("Logout");

        add(balanceLabel);
        add(depositBtn);
        add(withdrawBtn);
        add(historyBtn);
        add(logoutBtn);

        depositBtn.addActionListener(e -> {
            String amtStr = JOptionPane.showInputDialog("Enter deposit amount:");
            if (amtStr != null && !amtStr.isEmpty()) {
                double amt = Double.parseDouble(amtStr);
                student.deposit(amt); // automatically records transaction
                updateBalance();
            }
        });

        withdrawBtn.addActionListener(e -> {
            String amtStr = JOptionPane.showInputDialog("Enter withdrawal amount:");
            if (amtStr != null && !amtStr.isEmpty()) {
                double amt = Double.parseDouble(amtStr);
                if (student.withdraw(amt)) { // automatically records transaction
                    updateBalance();
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient balance!");
                }
            }
        });

        historyBtn.addActionListener(e -> {
            java.util.List<String> history = student.getTransactions();
            JTextArea area = new JTextArea();
            for (String t : history) {
                area.append(t + "\n");
            }
            area.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(area);
            scrollPane.setPreferredSize(new Dimension(350, 200));
            JOptionPane.showMessageDialog(this, scrollPane, "Transaction History", JOptionPane.INFORMATION_MESSAGE);
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }

    private void updateBalance() {
        balanceLabel.setText("Balance: ₱" + student.getBalance());
    }
}
