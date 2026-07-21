package com.mgaj.act4.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EquipoDTO 
{
    private Integer id;

    @NotBlank (message = "El nombre del equipo es obligatorio")
    private String nombre;

    @NotBlank (message = "La ciudad es obligatoria")
    private String ciudad;
}
