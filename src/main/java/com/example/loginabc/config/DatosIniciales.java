package com.example.loginabc.config;

import com.example.loginabc.model.Actividad;
import com.example.loginabc.model.Curso;
import com.example.loginabc.model.Rol;
import com.example.loginabc.model.Usuario;
import com.example.loginabc.repository.ActividadRepository;
import com.example.loginabc.repository.CursoRepository;
import com.example.loginabc.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
public class DatosIniciales implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final ActividadRepository actividadRepository;

    public DatosIniciales(
            UsuarioRepository usuarioRepository,
            CursoRepository cursoRepository,
            ActividadRepository actividadRepository) {
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
        this.actividadRepository = actividadRepository;

    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() > 0) {
            return;
        }

        // ========== Profes
        Usuario prof1 = new Usuario();
        prof1.setNombre("Pedro Pol");
        prof1.setCorreo("pedritopol@gmail.com");
        prof1.setPassword("pedritopol123");
        prof1.setRol(Rol.PROFESOR);
        usuarioRepository.save(prof1);

        Usuario prof2 = new Usuario();
        prof2.setNombre("Carlitos Pol");
        prof2.setCorreo("Carlitospol@gmail.com");
        prof2.setPassword("carlitospol123");
        prof2.setRol(Rol.PROFESOR);
        usuarioRepository.save(prof2);


        Usuario prof3 = new Usuario();
        prof3.setNombre("Manuel Pol");
        prof3.setCorreo("manuelitopol@gmail.com");
        prof3.setPassword("manuelitopol123");
        prof3.setRol(Rol.PROFESOR);
        usuarioRepository.save(prof3);


        Usuario prof4 = new Usuario();
        prof4.setNombre("Marcos Pol");
        prof4.setCorreo("marquitospol@gmail.com");
        prof4.setPassword("marquitospol123");
        prof4.setRol(Rol.PROFESOR);
        usuarioRepository.save(prof4);
        // ====== profes

        // ======= estudiantes
        Usuario est1 = new Usuario();
        est1.setNombre("enrique");
        est1.setCorreo("kikin@gmail.com");
        est1.setPassword("kikin69");
        est1.setRol(Rol.ESTUDIANTE);
        usuarioRepository.save(est1);

        Usuario est2 = new Usuario();
        est2.setNombre("mario");
        est2.setCorreo("marito@gmail.com");
        est2.setPassword("marito69");
        est2.setRol(Rol.ESTUDIANTE);
        usuarioRepository.save(est2);
        // ======= estudiantes


        // ======= cursos
        Curso curso1 = new Curso();
        curso1.setNombre("Programación II");
        curso1.setDescripcion("Curso que tiene que ver con desarrollo web");
        curso1.setProfesor(prof1);
        cursoRepository.save(curso1);

        Curso curso2 = new Curso();
        curso2.setNombre("Calculo II");
        curso2.setDescripcion("Curso que tiene que ver con calculo");
        curso2.setProfesor(prof2);
        cursoRepository.save(curso2);

        Curso curso3 = new Curso();
        curso3.setNombre("Microeconomía");
        curso3.setDescripcion("Curso que tiene que ver con Microeconomía");
        curso3.setProfesor(prof3);
        cursoRepository.save(curso3);

        Curso curso4 = new Curso();
        curso4.setNombre("Estadistica");
        curso4.setDescripcion("Curso que tiene que ver con Estadistica");
        curso4.setProfesor(prof4);
        cursoRepository.save(curso4);
        // ======= cursos

        // ======= actividades
        Actividad actividadProgra = new Actividad();
        actividadProgra.setTitulo("Web con Java");
        actividadProgra.setDescripcion("Crear un sitio Web con java");
        actividadProgra.setFechaEntrega(LocalDate.now().plusDays(7));
        actividadProgra.setCurso(curso1);
        actividadRepository.save(actividadProgra);

        Actividad actividadCalculo = new Actividad();
        actividadCalculo.setTitulo("Tabla de verdad");
        actividadCalculo.setDescripcion("Crear una tabla de verdad");
        actividadCalculo.setFechaEntrega(LocalDate.now().plusDays(7));
        actividadCalculo.setCurso(curso2);
        actividadRepository.save(actividadCalculo);

        Actividad actividadMicroecono = new Actividad();
        actividadMicroecono.setTitulo("Cambios de la demanda");
        actividadMicroecono.setDescripcion("Crear una grafica que muestre la demanda de un producto");
        actividadMicroecono.setFechaEntrega(LocalDate.now().plusDays(7));
        actividadMicroecono.setCurso(curso3);
        actividadRepository.save(actividadMicroecono);

        Actividad actividadEstadistica = new Actividad();
        actividadEstadistica.setTitulo("Desviacion estandar");
        actividadEstadistica.setDescripcion("Resolver ejercicios de desviacion estandar");
        actividadEstadistica.setFechaEntrega(LocalDate.now().plusDays(7));
        actividadEstadistica.setCurso(curso4);
        actividadRepository.save(actividadEstadistica);

    }
}
