package com.example.loginabc.repository;

import com.example.loginabc.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findByProfesorId(Long profesorId);
}
