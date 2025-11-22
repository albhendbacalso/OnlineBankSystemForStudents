import javax.swing.*;
import java.awt.*;

public class RegistrationFrame extends JFrame {
    public RegistrationFrame(String preId, String preName) {
        setTitle("Student Bank System - Register");
        setSize(420, 230);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8,8));

        JPanel form = new JPanel(new GridLayout(4,2,8,8));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel idLabel = new JLabel("Student ID:");
        JTextField idField = new JTextField(preId == null ? "" : preId);
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(preName == null ? "" : preName);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JLabel pass2Label = new JLabel("Confirm Password:");
        JPasswordField pass2Field = new JPasswordField();

        form.add(idLabel); form.add(idField);
        form.add(nameLabel); form.add(nameField);
        form.add(passLabel); form.add(passField);
        form.add(pass2Label); form.add(pass2Field);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton createBtn = new JButton("Create Account");
        JButton backBtn = new JButton("Back to Login");
        buttons.add(createBtn);
        buttons.add(backBtn);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        createBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String pw = new String(passField.getPassword()).trim();
            String pw2 = new String(pass2Field.getPassword()).trim();

            if (id.isEmpty() || name.isEmpty() || pw.isEmpty() || pw2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }
            if (!pw.equals(pw2)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.");
                return;
            }
            boolean ok = BankSystem.registerStudent(id, name, pw);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Registration successful. You can now login.");
                new LoginFrame();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Student ID already exists or invalid input.");
            }
        });

        backBtn.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        setVisible(true);
    }
}
