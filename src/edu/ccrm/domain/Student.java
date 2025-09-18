package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.*;

public class Student extends Person {
    private final String regNo;
    private boolean active = true;
    private final Map<String, Enrollment> enrolled = new LinkedHashMap<>();
    private final LocalDate enrollmentDate;

    public Student(String id, String regNo, String fullName, String email) {
        super(id, fullName, email);
        this.regNo = regNo;
        this.enrollmentDate = LocalDate.now();
    }

    public String getRegNo() { return regNo; }

    public void enroll(Enrollment e) {
        enrolled.put(e.getCourse().getCode(), e);
    }

    public void unenroll(String courseCode) {
        enrolled.remove(courseCode);
    }

    public boolean isEnrolled(String courseCode) {
        return enrolled.containsKey(courseCode);
    }

    public Collection<Enrollment> getEnrollments() {
        return Collections.unmodifiableCollection(enrolled.values());
    }

    public void setGrade(String courseCode, Grade g) {
        Enrollment e = enrolled.get(courseCode);
        if (e != null) e.setGrade(g);
    }

    public int currentTotalCredits() {
        int sum = 0;
        for (Enrollment e : enrolled.values()) {
            if (e.getCourse() != null) sum += e.getCourse().getCredits();
        }
        return sum;
    }

    public void deactivate() { active = false; }
    public boolean isActive() { return active; }

    @Override
    public String profile() {
        return String.format("Student: %s | RegNo: %s | Active: %s", fullName, regNo, active);
    }

    public String transcriptText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Transcript for: ").append(fullName).append(" (").append(regNo).append(")\n");
        sb.append("Generated: ").append(LocalDate.now()).append("\n\n");
        double totalPoints = 0.0;
        int totalCredits = 0;
        for (Enrollment e : enrolled.values()) {
            sb.append(String.format("%s - %s | Credits: %d | Enrolled: %s | Grade: %s\n",
                    e.getCourse().getCode(), e.getCourse().getTitle(),
                    e.getCourse().getCredits(), e.getEnrolledOn(), e.getGrade() == null ? "-" : e.getGrade()));
            if (e.getGrade() != null) {
                totalPoints += (double) e.getGrade().getPoints() * e.getCourse().getCredits();
                totalCredits += e.getCourse().getCredits();
            }
        }
        double gpa = (totalCredits == 0) ? 0.0 : (totalPoints / totalCredits);
        sb.append(String.format("\nTotal Credits (graded): %d\n", totalCredits));
        sb.append(String.format("GPA: %.2f\n", gpa));
        return sb.toString();
    }
}
