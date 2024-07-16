package com.ejemplo.proyecto.controlador;

import com.ejemplo.proyecto.dominio.usuarios.*;
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
@RequestMapping("/api/usuarios")
@SecurityRequirement(name = "jwt-bearer")
public class ControladorUsuario {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @PostMapping
    public ResponseEntity<DTOUsuarioListado> registrarUsuario(@RequestBody @Valid DTOUsuarioRegistro registroUsuario, UriComponentsBuilder uriComponentsBuilder) {
        System.out.println(registroUsuario);
        Usuario usuario = repositorioUsuario.save(new Usuario(registroUsuario));
        DTOUsuarioListado usuarioListado = new DTOUsuarioListado(
                usuario.getId(),
                usuario.getNombre()
        );
        URI url = uriComponentsBuilder.path("usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(url).body(usuarioListado);
    }

    @GetMapping
    public ResponseEntity<Page<DTOUsuarioListado>> listarUsuarios(@PageableDefault(size = 10) Pageable pageable) {
        Page<DTOUsuarioListado> listaUsuarios = repositorioUsuario.findAll(pageable).map(DTOUsuarioListado::new);
        return ResponseEntity.ok(listaUsuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTOUsuarioListado> obtenerUsuario(@PathVariable Long id) {
        Usuario usuario = repositorioUsuario.getReferenceById(id);
        DTOUsuarioListado usuarioListado = new DTOUsuarioListado(
                usuario.getId(),
                usuario.getNombre()
        );
        return ResponseEntity.ok(usuarioListado);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DTOUsuarioListado> actualizarUsuario(@RequestBody @Valid DTOUsuarioActualizar actualizarUsuario) {
        Usuario usuario = repositorioUsuario.getReferenceById(actualizarUsuario.id());
        usuario.actualizarUsuario(actualizarUsuario);
        return ResponseEntity.ok(new DTOUsuarioListado(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        repositorioUsuario.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
