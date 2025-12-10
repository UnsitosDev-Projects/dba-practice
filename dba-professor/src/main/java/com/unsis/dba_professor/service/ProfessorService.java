package com.unsis.dba_professor.service;

import com.unsis.dba_professor.dto.request.ProfessorRequest;
import com.unsis.dba_professor.model.Professor;
import com.unsis.dba_professor.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfessorService {
    
    private final ProfessorRepository professorRepository;
    
    /**
     * Get professor by ID with caching
     * The result will be cached in Memcached with key "professor::{id}"
     */
    @Cacheable(value = "default", key = "'professor::' + #id")
    public Professor getProfessorById(String id) {
        log.info("Fetching professor with id {} from database (cache miss)", id);
        return professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor not found with id: " + id));
    }
    
    /**
     * Get all professors with caching
     */
    @Cacheable(value = "default", key = "'professors::all'")
    public List<Professor> getAllProfessors() {
        log.info("Fetching all professors from database (cache miss)");
        return professorRepository.findAll();
    }
    
    /**
     * Create a new professor and invalidate the list cache
     */
    @CacheEvict(value = "default", key = "'professors::all'")
    public Professor createProfessor(ProfessorRequest request) {
        log.info("Creating new professor: {}", request.getName());
        Professor professor = new Professor();
        professor.setName(request.getName());
        professor.setDepartment(request.getDepartment());
        professor.setEmail(request.getEmail());
        return professorRepository.save(professor);
    }
    
    /**
     * Update professor and update the cache
     */
    @CachePut(value = "default", key = "'professor::' + #id")
    @CacheEvict(value = "default", key = "'professors::all'")
    public Professor updateProfessor(String id, ProfessorRequest request) {
        log.info("Updating professor with id: {}", id);
        Professor professor = getProfessorById(id);
        professor.setName(request.getName());
        professor.setDepartment(request.getDepartment());
        professor.setEmail(request.getEmail());
        return professorRepository.save(professor);
    }
    
    /**
     * Delete professor and evict from cache
     */
    @CacheEvict(value = "default", allEntries = true)
    public void deleteProfessor(String id) {
        log.info("Deleting professor with id: {}", id);
        if (!professorRepository.existsById(id)) {
            throw new RuntimeException("Professor not found with id: " + id);
        }
        professorRepository.deleteById(id);
    }
    
    /**
     * Clear all professor-related caches
     */
    @CacheEvict(value = "default", allEntries = true)
    public void clearCache() {
        log.info("Clearing all professor caches");
    }
}
