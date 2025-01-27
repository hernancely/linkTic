package com.hcely.tareas.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
public class TareaDto {

    private UUID id;

    @NotEmpty(message = "El título no puede estar vacío")
    private String titulo;

    private String descripcion;

    private String estado;

    private Timestamp fechaCreacion;
}
