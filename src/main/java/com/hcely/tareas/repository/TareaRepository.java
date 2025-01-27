package com.hcely.tareas.repository;

import com.hcely.tareas.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, UUID> {
}
