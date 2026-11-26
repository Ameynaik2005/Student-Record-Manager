import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Task6 {

    // Custom checked exception for a missing student
    static class StudentNotFoundException extends Exception {
        private static final long serialVersionUID = 1L;

        StudentNotFoundException(int id) {
            super("No student found with ID " + id + ".");
        }
    }

    static class Student {
        private final int id;
        private final String name;
        private final String course;
        private final int age;

        Student(int id, String name, String course, int age) {
            this.id = id;
            this.name = name;
            this.course = course;
            this.age = age;
        }

        int getId()        { return id; }
        String getName()   { return name; }
        String getCourse() { return course; }
        int getAge()       { return age; }

        // StringBuilder builds a multi-line detail view
        String toDetails() {
            StringBuilder sb = new StringBuilder();
            sb.append("Student ID : ").append(id).append("\n");
            sb.append("Name       : ").append(name).append("\n");
            sb.append("Course     : ").append(course).append("\n");
            sb.append("Age        : ").append(age);
            return sb.toString();
        }
    }

    // HashMap: Student ID (unique key) -> Student object
    private static final Map<Integer, Student> students = new HashMap<>();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            if (!sc.hasNextLine()) {
                break;
            }
            try {
                int choice = Integer.parseInt(sc.nextLine().trim());
                switch (choice) {
                    case 1: addStudent(); break;
                    case 2: viewStudents(); break;
                    case 3: searchStudent(); break;
                    case 4: removeStudent(); break;
                    case 5: displayUniqueCourses(); break;
                    case 6:
                        System.out.println("\nThank you for using Student Record Manager.");
                        running = false;
                        break;
                    default:
                        System.out.println("\nError: Invalid menu choice. Please enter 1 to 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nError: Invalid numeric input. Please enter numbers only.");
            } catch (StudentNotFoundException e) {
                System.out.println("\nError: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("\nError: " + e.getMessage());
            }
        }
    }

    static void printMenu() {
        System.out.println("\n===== Student Record Manager =====");
        System.out.println("1. Add Student");
        System.out.println("2. View Students");
        System.out.println("3. Search Student");
        System.out.println("4. Remove Student");
        System.out.println("5. Display Unique Courses");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    static int readStudentId(String prompt) {
        System.out.print(prompt);
        int id = Integer.parseInt(sc.nextLine().trim());
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid Student ID. ID must be a positive number.");
        }
        return id;
    }

    static void addStudent() {
        System.out.println("\n--- Add Student ---");
        int id = readStudentId("Enter Student ID   : ");
        if (students.containsKey(id)) {
            throw new IllegalArgumentException(
                    "Student ID " + id + " already exists. IDs must be unique.");
        }

        System.out.print("Enter Student Name : ");
        String name = sc.nextLine().trim();
        if (!name.matches("[A-Za-z ]+")) {
            throw new IllegalArgumentException("Invalid name. Use letters and spaces only.");
        }

        System.out.print("Enter Course       : ");
        String course = sc.nextLine().trim();
        if (course.isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be empty.");
        }

        System.out.print("Enter Age          : ");
        int age = Integer.parseInt(sc.nextLine().trim());
        if (age < 10 || age > 100) {
            throw new IllegalArgumentException(
                    "Invalid age. Enter an age between 10 and 100.");
        }

        Student s = new Student(id, toTitleCase(name), toTitleCase(course), age);
        students.put(id, s);
        System.out.println("\nStudent added successfully: " + s.getName()
                + " (ID: " + id + ")");
    }

    // Returns a string of 'count' dashes (used for the table borders)
    static String dashes(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append('-');
        }
        return sb.toString();
    }

    static void viewStudents() {
        System.out.println("\n--- All Students ---");
        if (students.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }

        // Column widths adjust to the longest value in each column
        int idW = 2, nameW = 4, courseW = 6, ageW = 3;
        for (Student s : students.values()) {
            idW = Math.max(idW, String.valueOf(s.getId()).length());
            nameW = Math.max(nameW, s.getName().length());
            courseW = Math.max(courseW, s.getCourse().length());
            ageW = Math.max(ageW, String.valueOf(s.getAge()).length());
        }

        String border = "+" + dashes(idW + 2) + "+" + dashes(nameW + 2) + "+"
                + dashes(courseW + 2) + "+" + dashes(ageW + 2) + "+";
        String rowFormat = "| %-" + idW + "s | %-" + nameW + "s | %-"
                + courseW + "s | %-" + ageW + "s |";

        // StringBuilder builds the whole bordered table
        StringBuilder table = new StringBuilder();
        table.append(border).append("\n");
        table.append(String.format(rowFormat, "ID", "Name", "Course", "Age")).append("\n");
        table.append(border).append("\n");

        // TreeSet gives the IDs in sorted order
        for (int id : new TreeSet<>(students.keySet())) {
            Student s = students.get(id);
            table.append(String.format(rowFormat,
                    s.getId(), s.getName(), s.getCourse(), s.getAge())).append("\n");
        }
        table.append(border).append("\n");
        table.append("Total students: ").append(students.size());
        System.out.println(table);
    }

    static void searchStudent() throws StudentNotFoundException {
        System.out.println("\n--- Search Student ---");
        int id = readStudentId("Enter Student ID to search: ");
        Student found = findStudent(id);
        System.out.println("\nStudent found:");
        System.out.println(found.toDetails());
    }

    static void removeStudent() throws StudentNotFoundException {
        System.out.println("\n--- Remove Student ---");
        int id = readStudentId("Enter Student ID to remove: ");
        Student removed = findStudent(id);
        students.remove(id);
        System.out.println("\nStudent removed successfully: "
                + removed.getName() + " (ID: " + id + ")");
    }

    static void displayUniqueCourses() {
        System.out.println("\n--- Unique Courses ---");
        if (students.isEmpty()) {
            System.out.println("No courses available. Add students first.");
            return;
        }

        // HashSet automatically ignores duplicate course names
        Set<String> uniqueCourses = new HashSet<>();
        for (Student s : students.values()) {
            uniqueCourses.add(s.getCourse());
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Total unique courses: ").append(uniqueCourses.size()).append("\n");
        int count = 1;
        for (String course : uniqueCourses) {
            sb.append(count++).append(". ").append(course).append("\n");
        }
        System.out.print(sb);
    }

    static Student findStudent(int id) throws StudentNotFoundException {
        Student s = students.get(id);
        if (s == null) {
            throw new StudentNotFoundException(id);
        }
        return s;
    }

    // StringBuilder cleans the text: trims spaces and capitalises each word
    static String toTitleCase(String text) {
        StringBuilder sb = new StringBuilder();
        boolean newWord = true;
        for (char c : text.trim().toCharArray()) {
            if (Character.isWhitespace(c)) {
                if (sb.length() > 0 && sb.charAt(sb.length() - 1) != ' ') {
                    sb.append(' ');
                }
                newWord = true;
            } else if (newWord) {
                sb.append(Character.toUpperCase(c));
                newWord = false;
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }
}
