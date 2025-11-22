import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DashboardFrame extends JFrame {
    private final Student student;
    private final JLabel balanceLabel;
    private final JTextArea historyArea;
    private final JPanel receiptPanel;

    public DashboardFrame(Student student) {
        this.student = student;

        setTitle("Student Bank - Dashboard (" + student.getStudentId() + ")");
        setSize(950, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ---------------- TOP PANEL ----------------
        JPanel top = new JPanel(new BorderLayout());
        JLabel welcome = new JLabel("Welcome, " + student.getName(), SwingConstants.LEFT);
        welcome.setFont(welcome.getFont().deriveFont(Font.BOLD, 16f));

        balanceLabel = new JLabel("Balance: ₱" + student.getBalance(), SwingConstants.RIGHT);
        balanceLabel.setFont(balanceLabel.getFont().deriveFont(16f));

        top.add(welcome, BorderLayout.WEST);
        top.add(balanceLabel, BorderLayout.EAST);
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        add(top, BorderLayout.NORTH);

        // ---------------- CENTER PANEL ----------------
        JPanel center = new JPanel(new GridLayout(1, 3, 10, 10));

        // ---------------- CONTROLS ----------------
        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
        controls.setBorder(BorderFactory.createTitledBorder("Transactions"));

        // Deposit Panel
        JPanel depositPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        depositPanel.setBorder(BorderFactory.createTitledBorder("Deposit"));

        JTextField depositField = new JTextField(10);
        String[] methods = {"GCash", "PayMaya", "Credit Card", "Debit Card", "Cash"};
        JComboBox<String> methodCombo = new JComboBox<>(methods);
        JButton depositBtn = new JButton("Deposit");

        depositPanel.add(new JLabel("Amount:"));
        depositPanel.add(depositField);
        depositPanel.add(new JLabel("Method:"));
        depositPanel.add(methodCombo);
        depositPanel.add(depositBtn);

        // Withdraw Panel
        JPanel withdrawPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        withdrawPanel.setBorder(BorderFactory.createTitledBorder("Withdraw"));

        JTextField withdrawField = new JTextField(10);
        JButton withdrawBtn = new JButton("Withdraw");

        withdrawPanel.add(new JLabel("Amount:"));
        withdrawPanel.add(withdrawField);
        withdrawPanel.add(withdrawBtn);

        // Tuition Payment Panel
        JPanel tuitionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tuitionPanel.setBorder(BorderFactory.createTitledBorder("Tuition Payment"));

        JTextField tuitionField = new JTextField(10);
        JButton payTuitionBtn = new JButton("Pay Tuition");

        tuitionPanel.add(new JLabel("Amount:"));
        tuitionPanel.add(tuitionField);
        tuitionPanel.add(payTuitionBtn);

        controls.add(depositPanel);
        controls.add(Box.createVerticalStrut(10));
        controls.add(withdrawPanel);
        controls.add(Box.createVerticalStrut(10));
        controls.add(tuitionPanel);

        // ---------------- HISTORY PANEL ----------------
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder("Transaction History"));

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        JScrollPane historyScroll = new JScrollPane(historyArea);

        JPanel histBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshBtn = new JButton("Refresh");
        JButton clearBtn = new JButton("Clear All");
        histBtns.add(refreshBtn);
        histBtns.add(clearBtn);

        historyPanel.add(historyScroll, BorderLayout.CENTER);
        historyPanel.add(histBtns, BorderLayout.SOUTH);

        // ---------------- RECEIPTS PANEL ----------------
        receiptPanel = new JPanel();
        receiptPanel.setBorder(BorderFactory.createTitledBorder("Receipts"));
        receiptPanel.setLayout(new BoxLayout(receiptPanel, BoxLayout.Y_AXIS));
        JScrollPane receiptScroll = new JScrollPane(receiptPanel);

        // Add all panels to center
        center.add(controls);
        center.add(historyPanel);
        center.add(receiptScroll);

        add(center, BorderLayout.CENTER);

        // ---------------- BOTTOM PANEL ----------------
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutBtn = new JButton("Logout");
        bottom.add(logoutBtn);
        add(bottom, BorderLayout.SOUTH);

        // ---------------- ACTION LISTENERS ----------------
        Runnable refresh = () -> {
            balanceLabel.setText("Balance: ₱" + student.getBalance());
            loadHistory();
            loadReceiptButtons();
        };

        // Deposit action
        depositBtn.addActionListener(e -> {
            try {
                double amt = Double.parseDouble(depositField.getText());
                if (amt <= 0) throw new NumberFormatException();

                String method = (String) methodCombo.getSelectedItem();
                student.deposit(amt);
                student.addTransaction("Deposited ₱" + amt + " via " + method);
                refresh.run();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount.");
            }
        });

        // Withdraw action
        withdrawBtn.addActionListener(e -> {
            try {
                double amt = Double.parseDouble(withdrawField.getText());
                if (amt <= 0) throw new NumberFormatException();

                if (student.withdraw(amt)) {
                    student.addTransaction("Withdrew ₱" + amt);
                    refresh.run();
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient balance!");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount.");
            }
        });

        // Tuition Payment action
        payTuitionBtn.addActionListener(e -> {
            try {
                double amt = Double.parseDouble(tuitionField.getText());
                if (amt <= 0) throw new NumberFormatException();

                if (student.withdraw(amt)) {
                    student.addTransaction("Paid Tuition: ₱" + amt);
                    refresh.run();
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient balance for tuition.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount.");
            }
        });

        // Refresh and Clear History
        refreshBtn.addActionListener(e -> refresh.run());

        clearBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Clear all history and reciept?");
            if (confirm == JOptionPane.YES_OPTION) {
                student.getTransactions().clear();
                refresh.run();
            }
        });

        // Logout
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        refresh.run();
        setVisible(true);
    }

    // ---------------- HELPER METHODS ----------------
    private void loadHistory() {
        List<String> list = student.getTransactions();
        historyArea.setText("");
        for (int i = list.size() - 1; i >= 0; i--) {
            historyArea.append(list.get(i) + "\n");
        }
        historyArea.setCaretPosition(0);
    }

    private void loadReceiptButtons() {
        receiptPanel.removeAll();
        List<String> transactions = student.getTransactions();
        for (int i = transactions.size() - 1; i >= 0; i--) {
            String transaction = transactions.get(i);
            JButton receiptBtn = new JButton("View Receipt #" + (i + 1));
            receiptBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

            receiptBtn.addActionListener(e -> {
                String receiptText = "------ RECEIPT ------\n"
                        + transaction + "\nBalance: ₱" + student.getBalance() + "\n"
                        + "Time: " + java.time.LocalDateTime.now();

                JTextArea receiptArea = new JTextArea(receiptText, 15, 30);
                receiptArea.setEditable(false);
                JScrollPane scroll = new JScrollPane(receiptArea);

                JButton printBtn = new JButton("Print Receipt");
                printBtn.addActionListener(ev -> {
                    try {
                        boolean done = receiptArea.print();
                        if (done) JOptionPane.showMessageDialog(null, "Receipt printed successfully!");
                        else JOptionPane.showMessageDialog(null, "Printing cancelled.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Printing failed: " + ex.getMessage());
                    }
                });

                JPanel panel = new JPanel(new BorderLayout());
                panel.add(scroll, BorderLayout.CENTER);
                panel.add(printBtn, BorderLayout.SOUTH);

                JOptionPane.showMessageDialog(null, panel, "Receipt", JOptionPane.PLAIN_MESSAGE);
            });

            receiptPanel.add(receiptBtn);
            receiptPanel.add(Box.createVerticalStrut(5));
        }
        receiptPanel.revalidate();
        receiptPanel.repaint();
    }
}
