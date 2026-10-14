package com.example.loginabc.model;

import jakarta.validation.constraints.NotBlank;

public class LoginForm {

    @NotBlank(message = "el correo es obligatorio")
    private String correo;

    @NotBlank(message = "la contraseña es obligatoria")
    private String password;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
