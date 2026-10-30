package com.example.loginabc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ActividadForm {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100, message = "El título no puede pasar de 100 caracteres")
    private String titulo;

    @Size(max = 500, message = "La descripción no puede pasar de 500 caracteres")
    private String descripcion;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaEntrega;

    /** Crea un formulario con los datos de una actividad existente (para editar). */
    public static ActividadForm desde(Actividad actividad) {
        ActividadForm form = new ActividadForm();
        form.setTitulo(actividad.getTitulo());
        form.setDescripcion(actividad.getDescripcion());
        form.setFechaEntrega(actividad.getFechaEntrega());
        return form;
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }
}