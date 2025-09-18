package edu.ccrm.io;

import edu.ccrm.service.DataStore;
import edu.ccrm.service.StudentService;
import edu.ccrm.service.CourseService;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Course.Builder;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Instructor;

import java.nio.file.*;
import java.io.IOException;
import java.util.stream.Stream;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ImportExportService {

    /**
     * Import students CSV format: regNo,fullName,email
     */
    public void importStudentsFromCsv(Path p) throws IOException{
        StudentService ss = DataStore.getInstance().students();
        try(Stream<String> lines = Files.lines(p)) {
            lines.map(String::trim).filter(s -> !s.isEmpty()).forEach(l -> {
                String[] parts = l.split(",");
                if (parts.length >= 3) {
                    ss.addStudent(parts[0].trim(), parts[1].trim(), parts[2].trim());
                }
            });
        }
    }

    /**
     * Export students CSV: regNo,fullName,email
     */
    public void exportStudentsToCsv(Path p) throws IOException {
        var list = DataStore.getInstance().students().listStudents();
        Files.createDirectories(p.getParent());
        List<String> out = new ArrayList<>();
        for (var s : list) {
            out.add(String.format("%s,%s,%s", s.getRegNo(), s.getFullName(), s.getEmail()));
        }
        Files.write(p, out, StandardCharsets.UTF_8);
    }

    /**
     * Import courses CSV: code,title,credits,department,semester
     */
    public void importCoursesFromCsv(Path p) throws IOException {
        CourseService cs = DataStore.getInstance().courses();
        try(Stream<String> lines = Files.lines(p)) {
            lines.map(String::trim).filter(s -> !s.isEmpty()).forEach(l -> {
                String[] parts = l.split(",");
                if (parts.length >= 3) {
                    String code = parts[0].trim();
                    String title = parts[1].trim();
                    int credits = Integer.parseInt(parts[2].trim());
                    String dept = (parts.length >= 4) ? parts[3].trim() : "";
                    Semester sem = Semester.FALL;
                    if (parts.length >= 5) {
                        try { sem = Semester.valueOf(parts[4].trim().toUpperCase()); } catch(Exception ignored) {}
                    }
                    Course c = new Builder(code).title(title).credits(credits).department(dept).semester(sem).build();
                    cs.addCourse(c);
                }
            });
        }
    }

    /**
     * Export courses CSV
     */
    public void exportCoursesToCsv(Path p) throws IOException {
        var list = DataStore.getInstance().courses().listCourses();
        Files.createDirectories(p.getParent());
        List<String> out = new ArrayList<>();
        for (var c : list) {
            out.add(String.format("%s,%s,%d,%s,%s", c.getCode(), c.getTitle(), c.getCredits(), c.getDepartment(), c.getSemester()));
        }
        Files.write(p, out, StandardCharsets.UTF_8);
    }
}
