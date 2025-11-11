import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    public RegisterFrame() {
        setTitle("Student Bank System - Register");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 5, 5));

        JLabel idLabel = new JLabel("Student ID:");
        JTextField idField = new JTextField();

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JButton registerBtn = new JButton("Register");
        JButton backBtn = new JButton("Back");

        add(idLabel); add(idField);
        add(nameLabel); add(nameField);
        add(passLabel); add(passField);
        add(registerBtn); add(backBtn);

        registerBtn.addActionListener(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String pass = new String(passField.getPassword());

            if (BankSystem.registerStudent(id, name, pass)) {
                JOptionPane.showMessageDialog(this, "Registration Successful!");
                dispose();
                new LoginFrame();
            } else {
                JOptionPane.showMessageDialog(this, "Student ID already exists!");
            }
        });

        backBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true); // IMPORTANT!
    }
}
