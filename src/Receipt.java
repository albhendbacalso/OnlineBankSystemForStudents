import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;

public class Receipt extends JFrame {

    public Receipt(String title, String content) {
        setTitle(title);
        setSize(420, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8,8));

        JTextArea area = new JTextArea(content);
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton saveBtn = new JButton("Download Receipt (.txt)");
        JButton closeBtn = new JButton("Close");
        bottom.add(saveBtn);
        bottom.add(closeBtn);

        add(scroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        saveBtn.addActionListener(e -> {
            try {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Save Receipt");
                chooser.setSelectedFile(new java.io.File("receipt.txt"));
                int option = chooser.showSaveDialog(this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    FileWriter fw = new FileWriter(chooser.getSelectedFile());
                    fw.write(content);
                    fw.close();
                    JOptionPane.showMessageDialog(this, "Receipt saved successfully!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving receipt: " + ex.getMessage());
            }
        });

        closeBtn.addActionListener(e -> dispose());

        setVisible(true);
    }
}
