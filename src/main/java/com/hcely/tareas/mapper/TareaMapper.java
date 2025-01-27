package com.hcely.tareas.mapper;

import com.hcely.tareas.dto.TareaDto;
import com.hcely.tareas.model.Tarea;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface TareaMapper {

    TareaMapper INSTANCE = Mappers.getMapper( TareaMapper.class );
    TareaDto toDto(Tarea tarea);
    TareaDto toDto(Optional<Tarea> tarea);

    Tarea toEntity(TareaDto tareaDto);

}
