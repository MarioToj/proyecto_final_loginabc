package com.example.loginabc.interceptors;

import com.example.loginabc.model.Rol;
import com.example.loginabc.model.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        if (usuario == null) {
            response.sendRedirect("/login");
            return false;
        }

        String ruta = request.getRequestURI();
        boolean esRutaProfesor = ruta.equals("/profesor") || ruta.startsWith("/profesor");

        if (esRutaProfesor && usuario.getRol() != Rol.PROFESOR) {
            response.sendRedirect("/dashboard");
            return false;
        }

        response.setHeader("Cache-Control", "no-store");
        return true;
    }
}
