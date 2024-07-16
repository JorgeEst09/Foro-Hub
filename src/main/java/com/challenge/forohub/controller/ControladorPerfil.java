package com.ejemplo.proyecto.controlador;

import com.ejemplo.proyecto.dominio.perfiles.*;
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
@RequestMapping("/api/perfiles")
@SecurityRequirement(name = "jwt-bearer")
public class ControladorPerfil {

    @Autowired
    private RepositorioPerfil repositorioPerfil;

    @PostMapping
    public ResponseEntity<PerfilListadoDTO> crearPerfil(@RequestBody @Valid PerfilRegistroDTO perfilRegistroDTO, UriComponentsBuilder uriBuilder) {
        System.out.println(perfilRegistroDTO);
        Perfil nuevoPerfil = repositorioPerfil.save(new Perfil(perfilRegistroDTO));
        PerfilListadoDTO perfilListadoDTO = new PerfilListadoDTO(
                nuevoPerfil.getId(),
                nuevoPerfil.getNombre()
        );
        URI url = uriBuilder.path("/api/perfiles/{id}").buildAndExpand(nuevoPerfil.getId()).toUri();
        return ResponseEntity.created(url).body(perfilListadoDTO);
    }

    @GetMapping
    public ResponseEntity<Page<PerfilListadoDTO>> listarPerfiles(@PageableDefault(size = 10) Pageable pageable) {
        Page<PerfilListadoDTO> perfiles = repositorioPerfil.findAll(pageable).map(PerfilListadoDTO::new);
        return ResponseEntity.ok(perfiles);
    }

   
