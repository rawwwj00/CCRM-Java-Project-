package edu.ccrm.cli;

import edu.ccrm.service.DataStore;
import edu.ccrm.service.StudentService;
import edu.ccrm.service.CourseService;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Grade;
import edu.ccrm.exceptions.DuplicateEnrollmentException;
import edu.ccrm.exceptions.MaxCreditLimitExceededException;
import edu.ccrm.exceptions.NotFoundException;
import edu.ccrm.io.ImportExportService;

import java.util.Scanner;
import java.util.UUID;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class Main {
    private final StudentService studentService = DataStore.getInstance().students();
    private final CourseService courseService = DataStore.getInstance().courses();
    private final ImportExportService ioService = new ImportExportService();

    public static void main(String[] args){
        new Main().run();
    }

    private void run(){
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        while(!exit){
            menu();
            String opt = sc.nextLine().trim();
            try {
                switch(opt){
                    case "1": createStudent(sc); break;
                    case "2": listStudents(); break;
                    case "3": createCourse(sc); break;
                    case "4": listCourses(); break;
                    case "5": enrollStudent(sc); break;
                    case "6": unenrollStudent(sc); break;
                    case "7": recordGrade(sc); break;
                    case "8": exportTranscript(sc); break;
                    case "9": importStudents(sc); break;
                    case "10": exportStudents(sc); break;
                    case "11": importCourses(sc); break;
                    case "12": exportCourses(sc); break;
                    case "0": exit = true; break;
                    default: System.out.println("Unknown option"); break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
        }
        System.out.println("Bye");
        sc.close();
    }

    private void menu(){
        System.out.println("=== CCRM Menu ===");
        System.out.println("1. Add Student");
        System.out.println("2. List Students");
        System.out.println("3. Add Course");
        System.out.println("4. List Courses");
        System.out.println("5. Enroll Student in Course");
        System.out.println("6. Unenroll Student from Course");
        System.out.println("7. Record/Update Grade");
        System.out.println("8. Export Student Transcript (text)");
        System.out.println("9. Import Students from CSV");
        System.out.println("10. Export Students to CSV");
        System.out.println("11. Import Courses from CSV");
        System.out.println("12. Export Courses to CSV");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }

    private void createStudent(Scanner sc){
        System.out.print("RegNo: "); String reg = sc.nextLine().trim();
        System.out.print("Full name: "); String name = sc.nextLine().trim();
        System.out.print("Email: "); String email = sc.nextLine().trim();
        var s = studentService.addStudent(reg, name, email);
        System.out.println("Added: " + s.getId());
    }

    private void listStudents(){
        var list = studentService.listStudents();
        if (list.isEmpty()) System.out.println("No students.");
        list.forEach(s -> System.out.println(s));
    }

    private void createCourse(Scanner sc){
        System.out.print("Course code: "); String code = sc.nextLine().trim();
        System.out.print("Title: "); String title = sc.nextLine().trim();
        System.out.print("Credits: ");
        int credits = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Department: "); String dept = sc.nextLine().trim();
        System.out.print("Semester (SPRING/SUMMER/FALL): ");
        Semester sem = Semester.FALL;
        try { sem = Semester.valueOf(sc.nextLine().trim().toUpperCase()); } catch(Exception ignored){}
        Course c = new Course.Builder(code).title(title).credits(credits).department(dept).semester(sem).build();
        courseService.addCourse(c);
        System.out.println("Added course: " + c.getCode());
    }

    private void listCourses(){
        var list = courseService.listCourses();
        if (list.isEmpty()) System.out.println("No courses.");
        list.forEach(c -> System.out.println(c));
    }

    private void enrollStudent(Scanner sc){
        System.out.print("Student RegNo: "); String reg = sc.nextLine().trim();
        var s = studentService.findByRegNo(reg);
        if (s == null) { System.out.println("Student not found"); return; }
        System.out.print("Course Code: "); String code = sc.nextLine().trim();
        var c = courseService.findByCode(code);
        if (c == null) { System.out.println("Course not found"); return; }
        try {
            studentService.enrollStudent(s.getId(), c);
            System.out.println("Enrolled " + s.getFullName() + " in " + c.getCode());
        } catch (DuplicateEnrollmentException | MaxCreditLimitExceededException | NotFoundException e) {
            System.out.println("Could not enroll: " + e.getMessage());
        }
    }

    private void unenrollStudent(Scanner sc){
        System.out.print("Student RegNo: "); String reg = sc.nextLine().trim();
        var s = studentService.findByRegNo(reg);
        if (s == null) { System.out.println("Student not found"); return; }
        System.out.print("Course Code: "); String code = sc.nextLine().trim();
        try {
            studentService.unenrollStudent(s.getId(), code);
            System.out.println("Unenrolled " + s.getFullName() + " from " + code);
        } catch (NotFoundException e) {
            System.out.println("Could not unenroll: " + e.getMessage());
        }
    }

    private void recordGrade(Scanner sc){
        System.out.print("Student RegNo: "); String reg = sc.nextLine().trim();
        var s = studentService.findByRegNo(reg);
        if (s == null) { System.out.println("Student not found"); return; }
        System.out.print("Course Code: "); String code = sc.nextLine().trim();
        System.out.print("Grade (S/A/B/C/D/E/F): "); String g = sc.nextLine().trim().toUpperCase();
        try {
            Grade grade = Grade.valueOf(g);
            studentService.recordGrade(s.getId(), code, grade);
            System.out.println("Recorded grade " + grade + " for " + s.getFullName() + " in " + code);
        } catch (IllegalArgumentException ia) {
            System.out.println("Invalid grade");
        } catch (NotFoundException e) {
            System.out.println("Could not record grade: " + e.getMessage());
        }
    }

    private void exportTranscript(Scanner sc){
        System.out.print("Student RegNo: "); String reg = sc.nextLine().trim();
        var s = studentService.findByRegNo(reg);
        if (s == null) { System.out.println("Student not found"); return; }
        System.out.print("Output filename (relative): "); String fname = sc.nextLine().trim();
        Path out = Paths.get(fname);
        try {
            studentService.exportTranscript(s.getId(), out);
            System.out.println("Exported transcript to " + out.toAbsolutePath());
        } catch (IOException | NotFoundException e) {
            System.out.println("Could not export: " + e.getMessage());
        }
    }

    private void importStudents(Scanner sc){
        System.out.print("CSV path: "); String p = sc.nextLine().trim();
        try {
            ioService.importStudentsFromCsv(Paths.get(p));
            System.out.println("Imported students from " + p);
        } catch (IOException e) {
            System.out.println("Import failed: " + e.getMessage());
        }
    }

    private void exportStudents(Scanner sc){
        System.out.print("Output CSV path: "); String p = sc.nextLine().trim();
        try {
            ioService.exportStudentsToCsv(Paths.get(p));
            System.out.println("Exported students to " + p);
        } catch (IOException e) {
            System.out.println("Export failed: " + e.getMessage());
        }
    }

    private void importCourses(Scanner sc){
        System.out.print("CSV path: "); String p = sc.nextLine().trim();
        try {
            ioService.importCoursesFromCsv(Paths.get(p));
            System.out.println("Imported courses from " + p);
        } catch (IOException e) {
            System.out.println("Import failed: " + e.getMessage());
        }
    }

    private void exportCourses(Scanner sc){
        System.out.print("Output CSV path: "); String p = sc.nextLine().trim();
        try {
            ioService.exportCoursesToCsv(Paths.get(p));
            System.out.println("Exported courses to " + p);
        } catch (IOException e) {
            System.out.println("Export failed: " + e.getMessage());
        }
    }
}
