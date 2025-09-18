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

CCRM-Java-Project/
├── README.md
├── .gitignore
├── test-data/
│ ├── students.csv
│ └── courses.csv
└── src/
└── edu/
└── ccrm/
├── cli/ # CLI entry point
│ └── Main.java
├── config/ # Config (Singleton)
│ └── AppConfig.java
├── domain/ # Core domain classes
│ ├── Course.java
│ ├── Enrollment.java
│ ├── Grade.java
│ ├── Instructor.java
│ ├── Person.java
│ ├── Semester.java
│ └── Student.java
├── exceptions/ # Custom exceptions
│ ├── DuplicateEnrollmentException.java
│ ├── MaxCreditLimitExceededException.java
│ └── NotFoundException.java
├── io/ # File I/O and Backup
│ ├── BackupService.java
│ └── ImportExportService.java
├── service/ # Services and datastore
│ ├── CourseService.java
│ ├── InMemoryCourseService.java
│ ├── InMemoryStudentService.java
│ ├── StudentService.java
│ └── DataStore.java
└── util/
└── Validators.java

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

Using IntelliJ/Eclipse

1. Import the project as a Java Project
2. Mark src as the source folder
3. Run edu.ccrm.cli.Main

---

📖 Usage Guide

When you run the app, the menu appears:

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

Example Run:
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
