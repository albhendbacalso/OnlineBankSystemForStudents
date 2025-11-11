import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("Student Bank System - Login");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center on screen
        setLayout(new GridLayout(3, 2, 5, 5));

        JLabel idLabel = new JLabel("Student ID:");
        JTextField idField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");

        add(idLabel);
        add(idField);
        add(passLabel);
        add(passField);
        add(loginBtn);
        add(registerBtn);

        // Simple login action (in-memory)
        loginBtn.addActionListener(e -> {
            String id = idField.getText();
            String pass = new String(passField.getPassword());
            Student s = BankSystem.login(id, pass);
            if (s != null) {
                dispose(); // close login frame
                new DashboardFrame(s);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid ID or Password");
            }
        });

        // Go to registration
        registerBtn.addActionListener(e -> {
            dispose();
            new RegisterFrame();
        });

        setVisible(true); // IMPORTANT! Frame must be visible
    }
}
