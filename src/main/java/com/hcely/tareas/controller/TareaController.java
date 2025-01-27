package com.hcely.tareas.controller;


import com.hcely.tareas.dto.TareaDto;
import com.hcely.tareas.model.Tarea;
import com.hcely.tareas.service.TareaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tareas")
@RequiredArgsConstructor
public class TareaController {

    private final TareaService tareaService;

    @PostMapping("/crear")
    public ResponseEntity<Tarea> crearTarea(@Valid @RequestBody TareaDto tarea) {
        Tarea nuevaTarea = tareaService.crearTarea(tarea);
        return ResponseEntity.ok(nuevaTarea);
    }

    @GetMapping("/obtenerTareas")
    public ResponseEntity<List<TareaDto>> listarTareas() {
        List<TareaDto> tareas = tareaService.listarTareas();
        return ResponseEntity.ok(tareas);
    }

    @PutMapping("/actualizar/{id}")
    public void actualizarEstado(@PathVariable UUID id, @RequestParam String estado) {
         tareaService.actualizarEstado(id, estado);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarTarea(@PathVariable UUID id) {
        tareaService.eliminarTarea(id);
    }
}
