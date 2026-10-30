package com.example.loginabc.controllers;

import com.example.loginabc.model.Curso;
import com.example.loginabc.model.Usuario;
import com.example.loginabc.service.ActividadService;
import com.example.loginabc.service.CursoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {

    private final CursoService cursoService;
    private final ActividadService actividadService;

    public DashboardController(CursoService cursoService, ActividadService actividadService) {
        this.cursoService = cursoService;
        this.actividadService = actividadService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        List<Curso> cursos = cursoService.cursosDe(usuario);
        Map<Long, Long> conteo = actividadService.contarPorCurso(cursos);
        long totalActividades = conteo.values().stream().mapToLong(Long::longValue).sum();

        model.addAttribute("usuario", usuario);
        model.addAttribute("totalCursos", cursos.size());
        model.addAttribute("totalActividades", totalActividades);
        return "dashboard";
    }
}