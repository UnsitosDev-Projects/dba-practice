package com.unsis.dba_professor.controller;

import com.unsis.dba_professor.dto.request.ProfessorRequest;
import com.unsis.dba_professor.model.Professor;
import com.unsis.dba_professor.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professors")
@RequiredArgsConstructor
public class ProfessorController {
    
    private final ProfessorService professorService;
    
    /**
     * GET /api/professors/{id}
     * First call: fetches from database (slow)
     * Subsequent calls: fetches from Memcached (fast)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Professor> getProfessor(@PathVariable String id) {
        return ResponseEntity.ok(professorService.getProfessorById(id));
    }
    
    /**
     * GET /api/professors
     * Returns all professors (cached)
     */
    @GetMapping
    public ResponseEntity<List<Professor>> getAllProfessors() {
        return ResponseEntity.ok(professorService.getAllProfessors());
    }
    
    /**
     * POST /api/professors
     * Creates a new professor and invalidates cache
     */
    @PostMapping
    public ResponseEntity<Professor> createProfessor(@RequestBody ProfessorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(professorService.createProfessor(request));
    }
    
    /**
     * PUT /api/professors/{id}
     * Updates professor and updates cache
     */
    @PutMapping("/{id}")
    public ResponseEntity<Professor> updateProfessor(
            @PathVariable String id,
            @RequestBody ProfessorRequest request) {
        return ResponseEntity.ok(professorService.updateProfessor(id, request));
    }
    
    /**
     * DELETE /api/professors/{id}
     * Deletes professor and evicts from cache
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable String id) {
        professorService.deleteProfessor(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * POST /api/professors/cache/clear
     * Clears all caches
     */
    @PostMapping("/cache/clear")
    public ResponseEntity<String> clearCache() {
        professorService.clearCache();
        return ResponseEntity.ok("Cache cleared successfully");
    }
}
