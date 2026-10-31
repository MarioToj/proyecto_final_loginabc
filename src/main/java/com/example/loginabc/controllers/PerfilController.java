package com.example.loginabc.controllers;

import com.example.loginabc.model.PerfilForm;
import com.example.loginabc.model.Usuario;
import com.example.loginabc.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    private final UsuarioService usuarioService;

    public PerfilController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String ver(HttpSession session, Model model) {
        model.addAttribute("usuario", session.getAttribute("usuario"));
        return "perfil";
    }

    @GetMapping("/editar")
    public String editar(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        PerfilForm form = new PerfilForm();
        form.setNombre(usuario.getNombre());
        model.addAttribute("perfilForm", form);
        return "perfil-editar";
    }

    @PostMapping("/editar")
    public String guardar(@Valid @ModelAttribute("perfilForm") PerfilForm form,
                          BindingResult result,
                          HttpSession session,
                          RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "perfil-editar";
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Usuario actualizado = usuarioService.actualizarPerfil(usuario.getId(), form);

        session.setAttribute("usuario", actualizado);   // el menú muestra el nombre nuevo
        redirect.addFlashAttribute("mensaje", "Perfil actualizado");
        return "redirect:/perfil";
    }

    @PostMapping("/eliminar")
    public String eliminar(HttpSession session, RedirectAttributes redirect) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (!usuarioService.eliminarCuenta(usuario)) {
            redirect.addFlashAttribute("error", "Los profesores no pueden eliminar su cuenta");
            return "redirect:/perfil";
        }

        session.invalidate();
        redirect.addFlashAttribute("mensaje", "Tu cuenta fue eliminada");
        return "redirect:/login";
    }
}