package com.ejemplo.proyecto.controlador;

import com.ejemplo.proyecto.dominio.usuarios.CredencialesUsuario;
import com.ejemplo.proyecto.dominio.usuarios.Usuario;
import com.ejemplo.proyecto.seguridad.TokenRespuesta;
import com.ejemplo.proyecto.seguridad.TokenServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class ControladorAutenticacion {

    @Autowired
    private AuthenticationManager gestorAutenticacion;

    @Autowired
    private TokenServicio servicioToken;

    @PostMapping("/iniciar-sesion")
    public ResponseEntity autenticarUsuario(@RequestBody @Valid CredencialesUsuario credenciales) {
        Authentication tokenAutenticacion = new UsernamePasswordAuthenticationToken(credenciales.getEmail(), credenciales.getContrasena());
        Authentication usuarioAutenticado = gestorAutenticacion.authenticate(tokenAutenticacion);
        String jwt = servicioToken.crearToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new TokenRespuesta(jwt));
    }
}
