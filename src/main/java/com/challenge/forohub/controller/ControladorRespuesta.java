package com.ejemplo.proyecto.controlador;

import com.ejemplo.proyecto.dominio.respuestas.*;
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
@RequestMapping("/api/respuestas")
@SecurityRequirement(name = "jwt-bearer")
public class ControladorRespuesta {

    @Autowired
    private ServicioRespuesta servicioRespuesta;

    @PostMapping
    public ResponseEntity<RespuestaListadoDTO> crearRespuesta(@RequestBody @Valid RespuestaRegistroDTO respuestaRegistroDTO) {
        RespuestaListadoDTO respuestaListadoDTO = servicioRespuesta.crearRespuesta(respuestaRegistroDTO);
        return ResponseEntity.ok(respuestaListadoDTO);
    }

    @PutMapping
    public ResponseEntity<RespuestaListadoDTO> actualizarRespuesta(@RequestBody @Valid RespuestaActualizarDTO respuestaActualizarDTO) {
        RespuestaListadoDTO respuestaListadoDTO = servicioRespuesta.actualizarRespuesta(respuestaActualizarDTO);
        return ResponseEntity.ok(respuestaListadoDTO);
    }

    @GetMapping
    public ResponseEntity<Page<RespuestaListadoDTO>> listarRespuestas(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(servicioRespuesta.obtenerRespuestas(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaListadoDTO> obtenerRespuesta(@PathVariable Long id) {
        return ResponseEntity.ok(servicioRespuesta.obtenerRespuesta(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRespuesta(@PathVariable Long id) {
        servicioRespuesta.eliminarRespuesta(id);
        return ResponseEntity.noContent().build();
    }
}
