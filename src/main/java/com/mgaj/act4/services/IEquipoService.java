package com.mgaj.act4.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mgaj.act4.dtos.EquipoDTO;

public interface  IEquipoService 
{
    Page<EquipoDTO> listarEquipos(Pageable pageable);
    EquipoDTO guardarEquipo(EquipoDTO equipoDTO);
    EquipoDTO obtenerPorId(Integer id);
    void eliminarEquipo (Integer id);
    EquipoDTO actualizarEquipo(Integer id, EquipoDTO equipoDTO);
}
