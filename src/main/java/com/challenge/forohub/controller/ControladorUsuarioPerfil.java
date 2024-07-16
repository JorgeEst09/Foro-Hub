package com.ejemplo.proyecto.controlador;

import com.ejemplo.proyecto.dominio.usuario_perfil.DTOUsuarioPerfilListado;
import com.ejemplo.proyecto.dominio.usuario_perfil.DTOUsuarioPerfilRegistro;
import com.ejemplo.proyecto.servicios.ServicioUsuarioPerfil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/api/usuarioperfil")
@SecurityRequirement(name = "jwt-bearer")
public class ControladorUsuarioPerfil {

    @Autowired
    private ServicioUsuarioPerfil servicioUsuarioPerfil;

    @PostMapping
    public ResponseEntity<DTOUsuarioPerfilListado> registrarUsuarioPerfil(@RequestBody @Valid DTOUsuarioPerfilRegistro registroUsuarioPerfil) {
        return ResponseEntity.ok(servicioUsuarioPerfil.registrarPerfil(registroUsuarioPerfil));
    }

    @GetMapping
    public ResponseEntity<List<DTOUsuarioPerfilListado>> listarUsuariosPerfiles() {
        return ResponseEntity.ok(servicioUsuarioPerfil.listarUsuarioPerfil());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuarioPerfil(@PathVariable Long id) {
        servicioUsuarioPerfil.eliminarUsuarioPerfil(id);
        return ResponseEntity.noContent().build();
    }

}
