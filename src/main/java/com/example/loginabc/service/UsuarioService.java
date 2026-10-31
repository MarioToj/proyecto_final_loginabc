package com.example.loginabc.service;

import com.example.loginabc.model.PerfilForm;
import com.example.loginabc.model.RegistroForm;
import com.example.loginabc.model.Rol;
import com.example.loginabc.model.Usuario;
import com.example.loginabc.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public boolean registrar(RegistroForm form) {
        String correo = form.getCorreo().trim().toLowerCase();

        if (usuarioRepository.existsByCorreo(correo)) {
            return false;
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(form.getNombre().trim());
        usuario.setCorreo(correo);
        usuario.setPassword(form.getPassword());
        usuario.setRol(Rol.ESTUDIANTE);
        usuarioRepository.save(usuario);
        return true;
    }

    public Usuario actualizarPerfil(Long usuarioId, PerfilForm form) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();
        usuario.setNombre(form.getNombre().trim());

        if (form.getPassword() != null && !form.getPassword().isBlank()) {
            usuario.setPassword(form.getPassword());
        }
        return usuarioRepository.save(usuario);
    }

    public boolean eliminarCuenta(Usuario usuario) {
        if (!usuario.esEstudiante()) {
            return false;
        }
        usuarioRepository.deleteById(usuario.getId());
        return true;
    }
}