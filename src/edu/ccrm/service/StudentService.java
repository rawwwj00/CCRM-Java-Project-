package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Grade;
import edu.ccrm.exceptions.DuplicateEnrollmentException;
import edu.ccrm.exceptions.MaxCreditLimitExceededException;
import edu.ccrm.exceptions.NotFoundException;

import java.nio.file.Path;
import java.io.IOException;
import java.util.List;

public interface StudentService {
    Student addStudent(String regNo, String fullName, String email);
    List<Student> listStudents();
    Student findById(String id);
    Student findByRegNo(String regNo);
    void enrollStudent(String studentId, Course course) throws DuplicateEnrollmentException, MaxCreditLimitExceededException, NotFoundException;
    void unenrollStudent(String studentId, String courseCode) throws NotFoundException;
    void recordGrade(String studentId, String courseCode, Grade g) throws NotFoundException;
    void exportTranscript(String studentId, Path out) throws IOException, NotFoundException;
}
