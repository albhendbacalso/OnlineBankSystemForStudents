import java.util.HashMap;

public class BankSystem {
    private static HashMap<String, Student> students = new HashMap<>();

    public static boolean registerStudent(String id, String name, String password) {
        if (students.containsKey(id)) return false;
        students.put(id, new Student(id, name, password));
        return true;
    }

    public static Student login(String id, String password) {
        Student s = students.get(id);
        if (s != null && s.getPassword().equals(password)) return s;
        return null;
    }
}
