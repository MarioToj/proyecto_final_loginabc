package com.example.loginabc.controllers;

import com.example.loginabc.model.LoginForm;
import com.example.loginabc.model.Usuario;
import com.example.loginabc.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final AuthService authService;

    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String mostrarLogin(HttpSession session, Model model) {

        if(session.getAttribute("usuario") != null) {
            return "redirect:/dashboard";
        }
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@Valid @ModelAttribute("loginForm") LoginForm form,
                                BindingResult result,
                                HttpSession session,
                                Model model) {
        if(result.hasErrors()){
            return "login";
        }
        Usuario usuario = authService.autenticarCredenciales(form.getCorreo(), form.getPassword());
        if (usuario == null) {
            model.addAttribute("error", "correo o contraseña invalida");
            return "login";
        }
        session.setAttribute("usuario", usuario);
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);
        return "dashboard";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
