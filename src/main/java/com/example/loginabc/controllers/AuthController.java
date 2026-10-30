package com.example.loginabc.controllers;

import com.example.loginabc.model.LoginForm;
import com.example.loginabc.model.RegistroForm;
import com.example.loginabc.model.Usuario;
import com.example.loginabc.service.AuthService;
import com.example.loginabc.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final AuthService authService;
    private final UsuarioService usuarioService;

    AuthController(AuthService authService, UsuarioService usuarioService) {
        this.authService = authService;
        this.usuarioService = usuarioService;
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

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String mostrarRegistro(HttpSession session, Model model) {
        if (session.getAttribute("usuario") != null) {
            return "redirect:/dashboard";
        }
        model.addAttribute("registroForm", new RegistroForm());
        return "register";
    }

    @PostMapping("/register")
    public String procesarRegistro(@Valid @ModelAttribute("registroForm") RegistroForm form,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "register";
        }

        if (!usuarioService.registrar(form)) {
            result.rejectValue("correo", "correo.duplicado", "Ese correo ya está registrado");
            return "register";
        }

        redirectAttributes.addFlashAttribute("mensaje", "Cuenta creada. Ya puedes iniciar sesión.");
        return "redirect:/login";
    }
}
