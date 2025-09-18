# Campus Course & Records Manager (CCRM)

A **console-based Java SE application** to manage students, courses, enrollments, grades, and file operations such as import/export and backup.  
This project follows clean OOP design principles and package conventions for academic submission.

---

## ✨ Features

- **Student Management**
  - Add / list students
  - Import / export students from CSV
- **Course Management**
  - Add / list courses
  - Import / export courses from CSV
- **Enrollment**
  - Enroll / unenroll students in courses
  - Prevent duplicate enrollments
  - Enforce maximum credit limit (30 credits)
- **Grades**
  - Record / update grades (S, A, B, C, D, E, F)
- **Transcripts**
  - Auto-calculate GPA
  - Export transcript to a text file
- **File Handling**
  - CSV import/export for students and courses
  - Backup service to copy all data

---

## 📂 Project Structure

```
CCRM-Java-Project/
├── README.md
├── .gitignore
├── test-data/
│   ├── students.csv
│   └── courses.csv
└── src/
    └── edu/
        └── ccrm/
            ├── cli/
            │   └── Main.java
            ├── config/
            │   └── AppConfig.java
            ├── domain/
            │   ├── Course.java
            │   ├── Enrollment.java
            │   ├── Grade.java
            │   ├── Instructor.java
            │   ├── Person.java
            │   ├── Semester.java
            │   └── Student.java
            ├── exceptions/
            │   ├── DuplicateEnrollmentException.java
            │   ├── MaxCreditLimitExceededException.java
            │   └── NotFoundException.java
            ├── io/
            │   ├── BackupService.java
            │   └── ImportExportService.java
            ├── service/
            │   ├── CourseService.java
            │   ├── InMemoryCourseService.java
            │   ├── InMemoryStudentService.java
            │   ├── StudentService.java
            │   └── DataStore.java
            └── util/
                └── Validators.java

```

---

## ⚙️ Requirements

- **Java JDK 17+**
- Works with command-line (`javac`/`java`) or any IDE (Eclipse, IntelliJ, VS Code)

---

## 🚀 How to Run

### Using Terminal
```bash
# Compile all Java files into /out
javac -d out $(find src -name "*.java")

# Run the main class
java -cp out edu.ccrm.cli.Main
```

### Using IntelliJ/Eclipse

1. Import the project as a Java Project
2. Mark src as the source folder
3. Run edu.ccrm.cli.Main

---

## 📖 Usage Guide

### When you run the app, the menu appears:
```
=== CCRM Menu ===
1. Add Student
2. List Students
3. Add Course
4. List Courses
5. Enroll Student in Course
6. Unenroll Student from Course
7. Record/Update Grade
8. Export Student Transcript (text)
9. Import Students from CSV
10. Export Students to CSV
11. Import Courses from CSV
12. Export Courses to CSV
0. Exit
Choose:
```
### Example Run:
```
1. Add Student
RegNo: reg001
Full name: John Doe
Email: john@example.com

3. Add Course
Course code: CS101
Title: Intro to Programming
Credits: 4
Department: CSE
Semester: FALL

5. Enroll Student in Course
Student RegNo: reg001
Course Code: CS101

7. Record/Update Grade
Student RegNo: reg001
Course Code: CS101
Grade: A

8. Export Student Transcript (text)
Output filename: transcript_john.txt
```
### 👉 This will generate a file transcript_john.txt with details and GPA.

## 🧪 Sample Data
```
test-data/students.csv
reg001,John Doe,john@example.com
reg002,Jane Roe,jane@example.com

test-data/courses.csv
CS101,Introduction to Programming,4,CSE,FALL
MA101,Calculus I,3,MATH,FALL

```

### Import these using menu options 9 and 11.

---


## 🏗️ OOP Principles Used

1. Encapsulation → private fields with getters/setters in Student, Course, etc.
2. Inheritance → Student and Instructor extend Person.
3. Polymorphism → profile() implemented differently in Student and Instructor.
4. Abstraction → Person is abstract; StudentService and CourseService are interfaces.
5. Design Patterns:
     1. Singleton → AppConfig, DataStore.
     2. Builder → Course.Builder for flexible course creation.

---

## 📜 Java Evolution Note

This project is implemented using modern Java (JDK 17) features such as enhanced switch, Streams API, java.nio.file for I/O, and enums for type safety.
It reflects how Java evolved from simple procedural coding (JDK 1.0) to robust OOP with generics, streams, and functional style (Java 8–17).

---

## 📦 Future Improvements

1. Unit tests (JUnit) for service layer
2. Export transcripts as PDF instead of plain text
3. Database persistence (MySQL/SQLite) instead of in-memory storage
4. Web-based UI using Spring Boot or JavaFX

---

## 👨‍💻 Author
### Developed by RAJ SACHAN
