package edu.ccrm.domain;

import java.time.LocalDate;

public class Enrollment {
    private final Course course;
    private final LocalDate enrolledOn;
    private Grade grade;

    public Enrollment(Course course){
        this.course = course; this.enrolledOn = LocalDate.now();
    }
    public Course getCourse(){ return course; }
    public LocalDate getEnrolledOn(){ return enrolledOn; }
    public Grade getGrade(){ return grade; }
    public void setGrade(Grade g){ this.grade = g; }

    @Override
    public String toString(){
        return String.format("%s | Enrolled: %s | Grade: %s", course.getCode(), enrolledOn, (grade==null?"-":grade));
    }
}
