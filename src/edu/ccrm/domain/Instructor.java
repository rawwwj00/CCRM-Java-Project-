package edu.ccrm.domain;

public class Instructor extends Person {
    public Instructor(String id, String fullName, String email){
        super(id, fullName, email);
    }
    @Override
    public String profile(){ return String.format("Instructor: %s <%s>", fullName, email); }
}
