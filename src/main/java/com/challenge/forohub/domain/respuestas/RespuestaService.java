package com.ejemplo.proyecto.servicios.respuestas;

import com.ejemplo.proyecto.datos.respuestas.*;
import com.ejemplo.proyecto.datos.topicos.Topico;
import com.ejemplo.proyecto.datos.topicos.TopicoRepository;
import com.ejemplo.proyecto.datos.usuarios.Usuario;
import com.ejemplo.proyecto.datos.usuarios.UsuarioRepository;
import com.ejemplo.proyecto.utils.errores.ErrorDeConsulta;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public DatosListadoRespuestas agregarRespuesta(DatosRegistroRespuesta datosRegistroRespuesta) {
        Topico topico;
        Usuario usuario;

        // Validar topico_id y usuario_id
        if (!topicoRepository.findById(datosRegistroRespuesta.topico_id()).isPresent()) {
            throw new ErrorDeConsulta("No se encontró el tópico");
        }

        if (!usuarioRepository.findById(datosRegistroRespuesta.usuario_id()).isPresent()) {
            throw new ErrorDeConsulta("No se encontró el usuario");
        }

        topico = topicoRepository.findById(datosRegistroRespuesta.topico_id()).get();
        usuario = usuarioRepository.findById(datosRegistroRespuesta.usuario_id()).get();

        Respuesta respuesta = new Respuesta(
                null,
                datosRegistroRespuesta.mensaje(),
                topico,
                LocalDateTime.now(),
                usuario,
                false
        );

        topico.agregarRespuesta(respuesta);
        Respuesta respuestaGuardada = respuestaRepository.save(respuesta);

        return new DatosListadoRespuestas(
                respuestaGuardada.getId(),
                respuestaGuardada.getMensaje(),
                respuestaGuardada.getTopico().getTitulo(),
                respuestaGuardada.getFecha(),
                respuestaGuardada.getUsuario().getNombre(),
                respuestaGuardada.getSolucion()
        );
    }

    public Page<DatosListadoRespuestas> getRespuestas(Pageable pageable) {
        return respuestaRepository.findAll(pageable).map(DatosListadoRespuestas::new);
    }

    public void deleteRespuesta(Long id) {
        respuestaRepository.deleteById(id);
    }

    public DatosListadoRespuestas getRespuesta(Long id) {
        Respuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new ErrorDeConsulta("No se encontró la respuesta"));

        return new DatosListadoRespuestas(respuesta);
    }

    @Transactional
    public DatosListadoRespuestas actualizaRespuesta(DatosActualizarRespuesta datosActualizarRespuesta) {
        Respuesta respuesta = respuestaRepository.findById(datosActualizarRespuesta.id())
                .orElseThrow(() -> new ErrorDeConsulta("No se encontró la respuesta"));

        respuesta.actualiza(datosActualizarRespuesta);

        return new DatosListadoRespuestas(respuesta);
    }
}
