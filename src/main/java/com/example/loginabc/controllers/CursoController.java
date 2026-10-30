package com.example.loginabc.controllers;

import com.example.loginabc.model.Curso;
import com.example.loginabc.model.Usuario;
import com.example.loginabc.service.ActividadService;
import com.example.loginabc.service.CursoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class CursoController {

    private final CursoService cursoService;
    private final ActividadService actividadService;

    public CursoController(CursoService cursoService, ActividadService actividadService) {
        this.cursoService = cursoService;
        this.actividadService = actividadService;
    }

    @GetMapping("/mis-cursos")
    public String misCursos(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        List<Curso> cursos = cursoService.cursosDe(usuario);

        model.addAttribute("usuario", usuario);
        model.addAttribute("cursos", cursos);
        model.addAttribute("conteo", actividadService.contarPorCurso(cursos));
        return "mis-cursos";
    }

    @GetMapping("/cursos/{id}")
    public String detalle(@PathVariable Long id, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        Optional<Curso> curso = cursoService.buscarPorId(id);
        if (curso.isEmpty()) {
            return "redirect:/mis-cursos";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("curso", curso.get());
        model.addAttribute("actividades", actividadService.listarPorCurso(id));
        model.addAttribute("puedeGestionar", cursoService.esProfesorDe(usuario, curso.get()));
        return "curso-detalle";
    }
}