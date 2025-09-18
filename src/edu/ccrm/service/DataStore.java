package edu.ccrm.service;

public final class DataStore {
    private static final DataStore INSTANCE = new DataStore();
    private final StudentService studentService = new InMemoryStudentService();
    private final CourseService courseService = new InMemoryCourseService();
    private DataStore(){}
    public static DataStore getInstance(){ return INSTANCE; }
    public StudentService students(){ return studentService; }
    public CourseService courses(){ return courseService; }
}
