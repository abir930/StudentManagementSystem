/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Login;

/**
 *
 * @author Rifat
 */
import java.util.ArrayList;
import java.util.Scanner;

class TeacherHome {
    private final String teacherName;
    private final String teacherId;
    private final ArrayList<String> courses;

    public TeacherHome(String teacherName, String teacherId) {
        this.teacherName = teacherName;
        this.teacherId = teacherId;
        this.courses = new ArrayList<>();
    }

    public void addCourse(String course) {
        courses.add(course);
        System.out.println("Course \"" + course + "\" has been added successfully.");
    }

    public void viewCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses assigned yet.");
        } else {
            System.out.println("Courses assigned to " + teacherName + ":");
            for (int i = 0; i < courses.size(); i++) {
                System.out.println((i + 1) + ". " + courses.get(i));
            }
        }
    }

    public void displayInfo() {
        System.out.println("Teacher Name: " + teacherName);
        System.out.println("Teacher ID: " + teacherId);
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter teacher's name: ");
            String name = scanner.nextLine();
            System.out.print("Enter teacher's ID: ");
            String id = scanner.nextLine();
            
            TeacherHome teacher = new TeacherHome(name, id);
            
            int choice;
            do {
                System.out.println("\n--- Teacher Home Menu ---");
                System.out.println("1. Add Course");
                System.out.println("2. View Courses");
                System.out.println("3. View Teacher Info");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        System.out.print("Enter course name: ");
                        String course = scanner.nextLine();
                        teacher.addCourse(course);
                        break;
                    case 2:
                        teacher.viewCourses();
                        break;
                    case 3:
                        teacher.displayInfo();
                        break;
                    case 4:
                        System.out.println("Exiting Teacher Home. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 4);
        }
    }
}
