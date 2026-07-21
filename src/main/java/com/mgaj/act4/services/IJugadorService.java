package com.mgaj.act4.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mgaj.act4.dtos.JugadorDTO;

public interface  IJugadorService 
{
    Page<JugadorDTO> listarJugadores(Pageable pageable);
    JugadorDTO guardarJugador (JugadorDTO jugadorDTO);
    JugadorDTO obtenerPorId (Integer id);
    void eliminarJugador (Integer id);
    JugadorDTO actualizarJugador(Integer id, JugadorDTO jugadorDTO);
}
