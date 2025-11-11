import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Start GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new LoginFrame(); // Opens the login window
        });
    }
}
