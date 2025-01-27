package com.hcely.tareas.model;

import com.hcely.tareas.common.TareaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tarea implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String titulo;
    private String descripcion;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TareaEnum estado;
    @Column(name = "fecha_creacion")
    private Timestamp fechaCreacion;
}
