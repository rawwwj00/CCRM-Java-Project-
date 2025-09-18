package edu.ccrm.domain;

public class Course {
    private final String code;
    private String title;
    private int credits;
    private Instructor instructor;
    private Semester semester;
    private String department;
    private boolean active = true;

    private Course(Builder b){
        this.code = b.code; this.title = b.title; this.credits = b.credits;
        this.instructor = b.instructor; this.semester = b.semester; this.department = b.department;
    }

    public static class Builder {
        private final String code;
        private String title = "";
        private int credits = 0;
        private Instructor instructor;
        private Semester semester = Semester.FALL;
        private String department = "";

        public Builder(String code){ this.code = code; }
        public Builder title(String t){ this.title = t; return this; }
        public Builder credits(int c){ this.credits = c; return this; }
        public Builder instructor(Instructor i){ this.instructor = i; return this; }
        public Builder semester(Semester s){ this.semester = s; return this; }
        public Builder department(String d){ this.department = d; return this; }
        public Course build(){ return new Course(this); }
    }

    public String getCode(){ return code; }
    public String getTitle(){ return title; }
    public int getCredits(){ return credits; }
    public Instructor getInstructor(){ return instructor; }
    public Semester getSemester(){ return semester; }
    public String getDepartment(){ return department; }
    public void deactivate(){ active=false; }
    public boolean isActive(){ return active; }

    @Override
    public String toString(){
        return String.format("%s - %s (%d credits) [%s] Dept:%s", code, title, credits, semester, department);
    }
}
