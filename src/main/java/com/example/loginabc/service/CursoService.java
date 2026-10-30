package com.example.loginabc.service;

import com.example.loginabc.model.Curso;
import com.example.loginabc.model.Usuario;
import com.example.loginabc.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<Curso> listarCursos() {
        return cursoRepository.findAll();
    }

    public List<Curso> cursosDe(Usuario usuario) {
        if (usuario.esProfesor()) {
            return cursoRepository.findByProfesorId(usuario.getId());
        }
        return cursoRepository.findAll();
    }

    public Optional<Curso> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }
    public boolean esProfesorDe(Usuario usuario, Curso curso) {
        return usuario.esProfesor()
                && Objects.equals(curso.getProfesor().getId(), usuario.getId());
    }

    public Optional<Curso> buscarGestionable(Long cursoId, Usuario usuario) {
        return cursoRepository.findById(cursoId)
                .filter(curso -> esProfesorDe(usuario, curso));
    }
}