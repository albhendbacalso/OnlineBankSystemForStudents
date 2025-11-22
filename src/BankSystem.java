import java.util.HashMap;

public class BankSystem {
    private static final HashMap<String, Student> students = new HashMap<>();

    // Optional: add a sample student for quick testing
    static {
        // studentID: S001, password: 1234
        registerStudent("S001", "Test Student", "1234");
    }

    public static boolean registerStudent(String id, String name, String password) {
        if (id == null || id.isBlank() || name == null || name.isBlank() || password == null || password.isBlank()) {
            return false;
        }
        synchronized (students) {
            if (students.containsKey(id)) return false;
            students.put(id, new Student(id, name, password));
            return true;
        }
    }

    public static Student login(String id, String password) {
        if (id == null || password == null) return null;
        Student s;
        synchronized (students) {
            s = students.get(id);
        }
        if (s != null && s.getPassword().equals(password)) return s;
        return null;
    }

    public static Student getStudent(String id) {
        synchronized (students) {
            return students.get(id);
        }
    }
}
