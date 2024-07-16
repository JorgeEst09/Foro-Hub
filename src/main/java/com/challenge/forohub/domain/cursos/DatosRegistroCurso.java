package com.ejemplo.proyecto.datos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record DatosRegistroCurso(
        @NotBlank
        String nombre,
        @Nullable
        String categoria
) {
}
