package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Grade;
import edu.ccrm.config.AppConfig;
import edu.ccrm.exceptions.DuplicateEnrollmentException;
import edu.ccrm.exceptions.MaxCreditLimitExceededException;
import edu.ccrm.exceptions.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class InMemoryStudentService implements StudentService {
    private final Map<String, Student> map = new LinkedHashMap<>();

    @Override
    public Student addStudent(String regNo, String fullName, String email){
        String id = UUID.randomUUID().toString();
        Student s = new Student(id, regNo, fullName, email);
        map.put(id, s);
        return s;
    }

    @Override
    public List<Student> listStudents(){ return new ArrayList<>(map.values()); }

    @Override
    public Student findById(String id){ return map.get(id); }

    @Override
    public Student findByRegNo(String regNo){
        for (Student s : map.values()) if (s.getRegNo().equalsIgnoreCase(regNo)) return s;
        return null;
    }

    @Override
    public void enrollStudent(String studentId, Course course) throws DuplicateEnrollmentException, MaxCreditLimitExceededException, NotFoundException {
        Student s = map.get(studentId);
        if (s == null) throw new NotFoundException("Student not found: " + studentId);
        if (s.isEnrolled(course.getCode())) throw new DuplicateEnrollmentException("Already enrolled in " + course.getCode());
        int newCredits = s.currentTotalCredits() + course.getCredits();
        int limit = AppConfig.getInstance().getMaxCreditsPerStudent();
        if (newCredits > limit) throw new MaxCreditLimitExceededException("Enrolling would exceed max credits (" + limit + ")");
        s.enroll(new Enrollment(course));
    }

    @Override
    public void unenrollStudent(String studentId, String courseCode) throws NotFoundException {
        Student s = map.get(studentId);
        if (s == null) throw new NotFoundException("Student not found: " + studentId);
        if (!s.isEnrolled(courseCode)) throw new NotFoundException("Student not enrolled in " + courseCode);
        s.unenroll(courseCode);
    }

    @Override
    public void recordGrade(String studentId, String courseCode, Grade g) throws NotFoundException {
        Student s = map.get(studentId);
        if (s == null) throw new NotFoundException("Student not found: " + studentId);
        if (!s.isEnrolled(courseCode)) throw new NotFoundException("Student not enrolled in " + courseCode);
        s.setGrade(courseCode, g);
    }

    @Override
    public void exportTranscript(String studentId, Path out) throws IOException, NotFoundException {
        Student s = map.get(studentId);
        if (s == null) throw new NotFoundException("Student not found: " + studentId);
        String txt = s.transcriptText();
        Files.createDirectories(out.getParent());
        Files.write(out, txt.getBytes(StandardCharsets.UTF_8));
    }
}
