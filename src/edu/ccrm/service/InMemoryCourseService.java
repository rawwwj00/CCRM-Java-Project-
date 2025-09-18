package edu.ccrm.service;

import edu.ccrm.domain.Course;
import java.util.*;

public class InMemoryCourseService implements CourseService {
    private final Map<String, Course> map = new LinkedHashMap<>();
    @Override
    public Course addCourse(Course course){ map.put(course.getCode(), course); return course; }
    @Override
    public List<Course> listCourses(){ return new ArrayList<>(map.values()); }
    @Override
    public Course findByCode(String code){ return map.get(code); }
}
