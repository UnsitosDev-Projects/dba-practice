package com.unsis.dba_professor.repository;

import com.unsis.dba_professor.model.Professor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ProfessorRepository {
    
    private final Map<String, Professor> professors = new ConcurrentHashMap<>();
    
    public ProfessorRepository() {
        // Initialize with some sample data
        professors.put("1", new Professor("1", "Dr. John Smith", "Computer Science", "john.smith@unsis.edu"));
        professors.put("2", new Professor("2", "Dr. Jane Doe", "Mathematics", "jane.doe@unsis.edu"));
        professors.put("3", new Professor("3", "Dr. Robert Johnson", "Physics", "robert.johnson@unsis.edu"));
    }
    
    public Optional<Professor> findById(String id) {
        // Simulate database delay
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return Optional.ofNullable(professors.get(id));
    }
    
    public List<Professor> findAll() {
        // Simulate database delay
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return new ArrayList<>(professors.values());
    }
    
    public Professor save(Professor professor) {
        if (professor.getId() == null) {
            professor.setId(UUID.randomUUID().toString());
        }
        professors.put(professor.getId(), professor);
        return professor;
    }
    
    public void deleteById(String id) {
        professors.remove(id);
    }
    
    public boolean existsById(String id) {
        return professors.containsKey(id);
    }
}
