package com.mgaj.act4.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JugadorDTO 
{
    private Integer id;

    @NotBlank(message="El nombre del jugador es obligatorio")
    private String nombre;

    @NotBlank(message="La posicion es obligatoria")
    private String posicion;

    @NotNull(message="El numero de camiseta es obligatorio")
    @Min(value=1, message="El numero de camiseta debe de ser mayor a 0")
    private Integer numeroCamiseta;

    @NotNull(message="El ID del equipo es obligatorio")
    private Integer equipoId;
}
