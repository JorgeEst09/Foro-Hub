package com.ejemplo.proyecto.controlador;

import com.ejemplo.proyecto.dominio.cursos.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@ResponseBody
@RequestMapping("/api/cursos")
@SecurityRequirement(name = "jwt-bearer")
public class ControladorCurso {

    @Autowired
    private RepositorioCurso repositorioCurso;

    @PostMapping
    public ResponseEntity<CursoListadoDTO> crearCurso(@RequestBody @Valid CursoRegistroDTO cursoRegistroDTO, UriComponentsBuilder uriBuilder) {
        System.out.println(cursoRegistroDTO);
        Curso nuevoCurso = repositorioCurso.save(new Curso(cursoRegistroDTO));
        CursoListadoDTO cursoListadoDTO = new CursoListadoDTO(
                nuevoCurso.getId(),
                nuevoCurso.getNombre(),
                nuevoCurso.getCategoria()
        );
        URI url = uriBuilder.path("/api/cursos/{id}").buildAndExpand(nuevoCurso.getId()).toUri();
        return ResponseEntity.created(url).body(cursoListadoDTO);
    }

    @GetMapping
    public ResponseEntity<Page<CursoListadoDTO>> listarCursos(@PageableDefault(size = 10) Pageable pageable) {
        Page<CursoListadoDTO> cursos = repositorioCurso.findAll(pageable).map(CursoListadoDTO::new);
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoListadoDTO> obtenerCurso(@PathVariable Long id) {
        Curso curso = repositorioCurso.getReferenceById(id);
        CursoListadoDTO cursoListadoDTO
