package com.example.loginabc.service;

import com.example.loginabc.model.Actividad;
import com.example.loginabc.model.ActividadForm;
import com.example.loginabc.model.Curso;
import com.example.loginabc.model.Usuario;
import com.example.loginabc.repository.ActividadRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ActividadService {

    private final ActividadRepository actividadRepository;
    private final CursoService cursoService;

    public ActividadService(ActividadRepository actividadRepository, CursoService cursoService) {
        this.actividadRepository = actividadRepository;
        this.cursoService = cursoService;
    }

    public Map<Long, Long> contarPorCurso(List<Curso> cursos) {
        Map<Long, Long> conteo = new HashMap<>();
        for (Curso curso : cursos) {
            conteo.put(curso.getId(), actividadRepository.countByCursoId(curso.getId()));
        }
        return conteo;
    }

    public List<Actividad> listarPorCurso(Long cursoId) {
        return actividadRepository.findByCursoIdOrderByFechaEntregaAsc(cursoId);
    }

    public Optional<Actividad> buscarGestionable(Long id, Usuario usuario) {
        return actividadRepository.findById(id)
                .filter(actividad -> cursoService.esProfesorDe(usuario, actividad.getCurso()));
    }

    public void crear(Curso curso, ActividadForm form) {
        Actividad actividad = new Actividad();
        actividad.setCurso(curso);
        copiarDatos(form, actividad);
        actividadRepository.save(actividad);
    }

    public void editar(Actividad actividad, ActividadForm form) {
        copiarDatos(form, actividad);
        actividadRepository.save(actividad);
    }

    public void eliminar(Actividad actividad) {
        actividadRepository.delete(actividad);
    }

    private void copiarDatos(ActividadForm form, Actividad actividad) {
        actividad.setTitulo(form.getTitulo().trim());
        actividad.setDescripcion(form.getDescripcion());
        actividad.setFechaEntrega(form.getFechaEntrega());
    }
}