package com.example.loginabc.controllers;

import com.example.loginabc.model.Actividad;
import com.example.loginabc.model.ActividadForm;
import com.example.loginabc.model.Curso;
import com.example.loginabc.model.Usuario;
import com.example.loginabc.service.ActividadService;
import com.example.loginabc.service.CursoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/profesor")
public class ActividadController {

    private final CursoService cursoService;
    private final ActividadService actividadService;

    public ActividadController(CursoService cursoService, ActividadService actividadService) {
        this.cursoService = cursoService;
        this.actividadService = actividadService;
    }

    @GetMapping("/cursos/{cursoId}/actividades/nueva")
    public String nueva(@PathVariable Long cursoId, HttpSession session,
                        Model model, RedirectAttributes redirect) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        Optional<Curso> curso = cursoService.buscarGestionable(cursoId, usuario);
        if (curso.isEmpty()) {
            return sinPermiso(redirect);
        }

        prepararFormulario(model, curso.get(), "Nueva actividad",
                "/profesor/cursos/" + cursoId + "/actividades");
        model.addAttribute("actividadForm", new ActividadForm());
        return "profesor/actividad-form";
    }

    @PostMapping("/cursos/{cursoId}/actividades")
    public String crear(@PathVariable Long cursoId,
                        @Valid @ModelAttribute("actividadForm") ActividadForm form,
                        BindingResult result, HttpSession session,
                        Model model, RedirectAttributes redirect) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        Optional<Curso> curso = cursoService.buscarGestionable(cursoId, usuario);
        if (curso.isEmpty()) {
            return sinPermiso(redirect);
        }

        if (result.hasErrors()) {
            prepararFormulario(model, curso.get(), "Nueva actividad",
                    "/profesor/cursos/" + cursoId + "/actividades");
            return "profesor/actividad-form";
        }

        actividadService.crear(curso.get(), form);
        redirect.addFlashAttribute("mensaje", "Actividad creada");
        return "redirect:/cursos/" + cursoId;
    }

    @GetMapping("/actividades/{id}/editar")
    public String editar(@PathVariable Long id, HttpSession session,
                         Model model, RedirectAttributes redirect) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        Optional<Actividad> actividad = actividadService.buscarGestionable(id, usuario);
        if (actividad.isEmpty()) {
            return sinPermiso(redirect);
        }

        prepararFormulario(model, actividad.get().getCurso(), "Editar actividad",
                "/profesor/actividades/" + id);
        model.addAttribute("actividadForm", ActividadForm.desde(actividad.get()));
        return "profesor/actividad-form";
    }

    @PostMapping("/actividades/{id}")
    public String actualizar(@PathVariable Long id,
                             @Valid @ModelAttribute("actividadForm") ActividadForm form,
                             BindingResult result, HttpSession session,
                             Model model, RedirectAttributes redirect) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        Optional<Actividad> actividad = actividadService.buscarGestionable(id, usuario);
        if (actividad.isEmpty()) {
            return sinPermiso(redirect);
        }

        Curso curso = actividad.get().getCurso();

        if (result.hasErrors()) {
            prepararFormulario(model, curso, "Editar actividad", "/profesor/actividades/" + id);
            return "profesor/actividad-form";
        }

        actividadService.editar(actividad.get(), form);
        redirect.addFlashAttribute("mensaje", "Actividad actualizada");
        return "redirect:/cursos/" + curso.getId();
    }

    @PostMapping("/actividades/{id}/eliminar")
    public String eliminar(@PathVariable Long id, HttpSession session, RedirectAttributes redirect) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        Optional<Actividad> actividad = actividadService.buscarGestionable(id, usuario);
        if (actividad.isEmpty()) {
            return sinPermiso(redirect);
        }

        Long cursoId = actividad.get().getCurso().getId();
        actividadService.eliminar(actividad.get());
        redirect.addFlashAttribute("mensaje", "Actividad eliminada");
        return "redirect:/cursos/" + cursoId;
    }

    private void prepararFormulario(Model model, Curso curso, String tituloPagina, String urlAccion) {
        model.addAttribute("curso", curso);
        model.addAttribute("tituloPagina", tituloPagina);
        model.addAttribute("urlAccion", urlAccion);
    }

    private String sinPermiso(RedirectAttributes redirect) {
        redirect.addFlashAttribute("error", "No tienes permiso para gestionar ese curso o actividad");
        return "redirect:/mis-cursos";
    }
}