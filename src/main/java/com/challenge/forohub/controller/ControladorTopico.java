package com.ejemplo.proyecto.controlador;

import com.ejemplo.proyecto.dominio.respuestas.DTORespuestasTopico;
import com.ejemplo.proyecto.dominio.topicos.DTOActualizarTopico;
import com.ejemplo.proyecto.dominio.topicos.DTOListadoTopico;
import com.ejemplo.proyecto.dominio.topicos.DTORegistroTopico;
import com.ejemplo.proyecto.servicios.ServicioTopico;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/api/topicos")
@SecurityRequirement(name = "jwt-bearer")
public class ControladorTopico {

    @Autowired
    private ServicioTopico servicioTopico;

    @PostMapping
    public ResponseEntity<DTOListadoTopico> crearTopico(@RequestBody @Valid DTORegistroTopico registroTopico) {
        DTOListadoTopico listadoTopico = servicioTopico.crearTopico(registroTopico);
        return ResponseEntity.ok(listadoTopico);
    }

    @GetMapping
    public ResponseEntity<Page<DTOListadoTopico>> listarTopicos(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(servicioTopico.obtenerTopicos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTOListadoTopico> obtenerTopico(@PathVariable Long id) {
        return ResponseEntity.ok(servicioTopico.obtenerTopico(id));
    }

    @PutMapping
    public ResponseEntity<DTOListadoTopico> actualizarTopico(@RequestBody @Valid DTOActualizarTopico actualizarTopico) {
        return ResponseEntity.ok(servicioTopico.actualizarTopico(actualizarTopico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        servicioTopico.desactivarTopico(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/respuestas")
    public ResponseEntity<Page<DTORespuestasTopico>> obtenerRespuestasTopico(@PathVariable Long id, @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(servicioTopico.obtenerRespuestas(id, pageable));
    }
}
