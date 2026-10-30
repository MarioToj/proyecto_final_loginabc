package com.example.loginabc.controllers;

import com.example.loginabc.service.CursoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    private final CursoService cursoService;

    public InicioController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping("/")
    public String inicio() {
        return "inicio";
    }

    @GetMapping("/cursos-disponibles")
    public String cursosDisponibles(Model model) {
        model.addAttribute("cursos", cursoService.listarCursos());
        return "cursos-disponibles";
    }
}