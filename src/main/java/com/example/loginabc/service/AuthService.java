package com.example.loginabc.service;

import com.example.loginabc.model.Usuario;
import com.example.loginabc.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario autenticarCredenciales(String correo, String password) {
        return usuarioRepository.findByCorreo(correo.trim())
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);
    }
}
