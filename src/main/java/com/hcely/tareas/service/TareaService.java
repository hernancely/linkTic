package com.hcely.tareas.service;

import com.hcely.tareas.dto.TareaDto;
import com.hcely.tareas.model.Tarea;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TareaService {
    Tarea crearTarea(TareaDto tarea);

    List<TareaDto> listarTareas();

    void actualizarEstado(UUID id, String estado);

    void eliminarTarea(UUID id);
}
