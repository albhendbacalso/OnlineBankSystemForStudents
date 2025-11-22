import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Student Bank System - Login");
        setSize(400, 180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(2, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel idLabel = new JLabel("Student ID:");
        JTextField idField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        form.add(idLabel);
        form.add(idField);
        form.add(passLabel);
        form.add(passField);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");

        buttons.add(loginBtn);
        buttons.add(registerBtn);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        loginBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String pass = new String(passField.getPassword()).trim();

            if (id.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Student ID and Password.");
                return;
            }

            Student s = BankSystem.login(id, pass);
            if (s != null) {
                new DashboardFrame(s);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Student ID or Password.");
            }
        });

        registerBtn.addActionListener(e -> {
            new RegistrationFrame(null, null);
            dispose();
        });

        setVisible(true);
    }
}
