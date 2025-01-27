package com.hcely.tareas.service.impl;

import com.hcely.tareas.common.TareaEnum;
import com.hcely.tareas.dto.TareaDto;
import com.hcely.tareas.exception.InvalidDataException;
import com.hcely.tareas.exception.TareaNotFoundException;
import com.hcely.tareas.mapper.TareaMapper;
import com.hcely.tareas.model.Tarea;
import com.hcely.tareas.repository.TareaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TareaServiceImplTest {

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private TareaMapper tareaMapper;

    @InjectMocks
    private TareaServiceImpl tareaService;

    private TareaDto tareaDto;
    private Tarea tarea;

    @BeforeEach
    void setUp() {
        tareaDto = new TareaDto();
        tareaDto.setTitulo("Test Tarea");

        tarea = new Tarea();
        tarea.setId(UUID.randomUUID());
        tarea.setTitulo("Test Tarea");
        tarea.setEstado(TareaEnum.PENDIENTE);
        tarea.setFechaCreacion(Timestamp.from(Instant.now()));
    }

    @Test
    void testCrearTarea() {
        when(tareaMapper.toEntity(tareaDto)).thenReturn(tarea);
        when(tareaRepository.save(tarea)).thenReturn(tarea);

        Tarea result = tareaService.crearTarea(tareaDto);

        assertNotNull(result);
        assertEquals(TareaEnum.PENDIENTE, result.getEstado());
        verify(tareaRepository, times(1)).save(tarea);
    }

    @Test
    void testCrearTareaTituloVacio() {
        tareaDto.setTitulo("");

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            tareaService.crearTarea(tareaDto);
        });

        assertEquals("El título no puede estar vacío", exception.getMessage());
    }

    @Test
    void testActualizarEstado() {
        UUID id = UUID.randomUUID();
        tarea.setEstado(TareaEnum.COMPLETADA);
        tareaDto.setEstado("COMPLETADA");

        when(tareaRepository.findById(id)).thenReturn(Optional.of(tarea));
        when(tareaRepository.save(tarea)).thenReturn(tarea);

        tareaService.actualizarEstado(id, "COMPLETADA");

        verify(tareaRepository, times(1)).findById(id);
        verify(tareaRepository, times(1)).save(tarea);
    }

    @Test
    void testActualizarEstadoTareaNoEncontrada() {
        UUID id = UUID.randomUUID();
        when(tareaRepository.findById(id)).thenReturn(Optional.empty());

        TareaNotFoundException exception = assertThrows(TareaNotFoundException.class, () -> {
            tareaService.actualizarEstado(id, "COMPLETADA");
        });

        assertEquals("No se encontró la tarea con el ID: " + id, exception.getMessage());
    }

    @Test
    void testActualizarEstadoInvalido() {
        UUID id = UUID.randomUUID();
        when(tareaRepository.findById(id)).thenReturn(Optional.of(tarea));

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            tareaService.actualizarEstado(id, "INVALIDO");
        });

        assertEquals("El estado proporcionado no es válido: INVALIDO", exception.getMessage());
    }

    @Test
    void testListarTareas() {
        List<Tarea> tareas = List.of(tarea);
        when(tareaRepository.findAll()).thenReturn(tareas);

        List<TareaDto> result = tareaService.listarTareas();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(tareaRepository, times(1)).findAll();
    }

    @Test
    void testListarTareasNoEncontradas() {
        when(tareaRepository.findAll()).thenReturn(Collections.emptyList());

        TareaNotFoundException exception = assertThrows(TareaNotFoundException.class, () -> {
            tareaService.listarTareas();
        });

        assertEquals("No se encontraron tareas", exception.getMessage());
    }

    @Test
    void testEliminarTarea() {
        UUID id = UUID.randomUUID();
        when(tareaRepository.findById(id)).thenReturn(Optional.of(tarea));

        tareaService.eliminarTarea(id);

        verify(tareaRepository, times(1)).deleteById(id);
    }

    @Test
    void testEliminarTareaNoEncontrada() {
        UUID id = UUID.randomUUID();
        when(tareaRepository.findById(id)).thenReturn(Optional.empty());

        TareaNotFoundException exception = assertThrows(TareaNotFoundException.class, () -> {
            tareaService.eliminarTarea(id);
        });

        assertEquals("No se encontró la tarea con el ID: " + id, exception.getMessage());
    }

    @Test
    void testEliminarTareaError() {
        UUID id = UUID.randomUUID();
        when(tareaRepository.findById(id)).thenReturn(Optional.of(tarea));
        doThrow(new RuntimeException()).when(tareaRepository).deleteById(id);

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            tareaService.eliminarTarea(id);
        });

        assertEquals("No se pudo eliminar la tarea con el ID: " + id, exception.getMessage());
    }
}
