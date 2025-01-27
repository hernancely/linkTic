package com.hcely.tareas.service.impl;

import com.hcely.tareas.common.TareaEnum;
import com.hcely.tareas.dto.TareaDto;
import com.hcely.tareas.exception.InvalidDataException;
import com.hcely.tareas.exception.TareaNotFoundException;
import com.hcely.tareas.mapper.TareaMapper;
import com.hcely.tareas.model.Tarea;
import com.hcely.tareas.repository.TareaRepository;
import com.hcely.tareas.service.TareaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TareaServiceImpl implements TareaService {

    private final TareaRepository tareaRepository;
    private final TareaMapper tareaMapper;

    private static final String TITULO_VACIO_ERROR = "El título no puede estar vacío";
    private static final String TAREA_NO_ENCONTRADA_ERROR = "No se encontró la tarea con el ID: ";
    private static final String ESTADO_INVALIDO_ERROR = "El estado proporcionado no es válido: ";
    private static final String TAREAS_NO_ENCONTRADAS_ERROR = "No se encontraron tareas";
    private static final String TAREA_ELIMINACION_ERROR = "No se pudo eliminar la tarea con el ID: ";


    public Tarea crearTarea(TareaDto tareaDto) {
        if (tareaDto.getTitulo() == null || tareaDto.getTitulo().isEmpty()) {
            throw new InvalidDataException(TITULO_VACIO_ERROR);
        }
        Tarea tarea = tareaMapper.toEntity(tareaDto);
        tarea.setEstado(TareaEnum.PENDIENTE);
        tarea.setFechaCreacion(Timestamp.from(Instant.now()));
        return tareaRepository.save(tarea);
    }

    public void actualizarEstado(UUID id, String estado) {
        Optional<Tarea> tareaOptional = tareaRepository.findById(id);
        if (tareaOptional.isEmpty()) {
            throw new TareaNotFoundException(TAREA_NO_ENCONTRADA_ERROR + id);
        }

        TareaEnum tareaEstado;
        try {
            tareaEstado = TareaEnum.valueOf(estado);
        } catch (IllegalArgumentException e) {
            throw new InvalidDataException(ESTADO_INVALIDO_ERROR + estado);
        }

        Tarea tarea = tareaOptional.get();
        tarea.setEstado(tareaEstado);
        tareaRepository.save(tarea);
    }

    public List<TareaDto> listarTareas() {
        List<Tarea> tareas = tareaRepository.findAll();
        if (tareas.isEmpty()) {
            throw new TareaNotFoundException(TAREAS_NO_ENCONTRADAS_ERROR);
        }
        return tareas.stream()
                .map(TareaMapper.INSTANCE::toDto)
                .toList();
    }

    public void eliminarTarea(UUID id) {
        Optional<Tarea> tareaOptional = tareaRepository.findById(id);
        if (tareaOptional.isEmpty()) {
            throw new TareaNotFoundException(TAREA_NO_ENCONTRADA_ERROR + id);
        }
        try {
            tareaRepository.deleteById(id);
        } catch (Exception e) {
            throw new InvalidDataException(TAREA_ELIMINACION_ERROR + id);
        }
    }
}
