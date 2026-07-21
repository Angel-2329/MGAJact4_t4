package com.mgaj.act4.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioRegistroDTO 
{
    private String nombre;

    @Email(message="Debe de ser un email valido")
    @NotBlank(message="El email es obligatorio")
    private String correo;

    @NotBlank(message="La contrseña es obligatoria")
    private String contraseña;
}
