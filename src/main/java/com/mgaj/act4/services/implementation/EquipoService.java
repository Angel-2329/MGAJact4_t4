package com.mgaj.act4.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mgaj.act4.dtos.EquipoDTO;
import com.mgaj.act4.models.Equipo;
import com.mgaj.act4.repository.EquipoRepository;
import com.mgaj.act4.services.IEquipoService;

@Service
public class EquipoService implements IEquipoService 
{
    @Autowired
    private EquipoRepository repositorio;

    @Override
    public Page<EquipoDTO> listarEquipos (Pageable pageable)
    {
        Page<Equipo> equipos = repositorio.findAll(pageable);
        return equipos.map(this::convertirADTO);
    }

    @Override
    public EquipoDTO guardarEquipo(EquipoDTO equipoDTO) 
    {
        Equipo equipo = new Equipo();
        equipo.setNombre(equipoDTO.getNombre());
        equipo.setCiudad(equipoDTO.getCiudad());
        
        Equipo equipoGuardado = repositorio.save(equipo);
        
        return convertirADTO(equipoGuardado);
    }

    @Override
    public EquipoDTO obtenerPorId(Integer id) 
    {
        Equipo equipo = repositorio.findById(id).orElse(null);
        if(equipo == null) return null;
        return convertirADTO(equipo);
    }

    @Override
    public void eliminarEquipo(Integer id) 
    {
        repositorio.deleteById(id);
    }
    
    @Override
    public EquipoDTO actualizarEquipo(Integer id, EquipoDTO equipoDTO) 
    {
        Equipo equipoExistente = repositorio.findById(id).orElse(null);
        if (equipoExistente == null) 
        {
            return null; 
        }
        equipoExistente.setNombre(equipoDTO.getNombre());
        equipoExistente.setCiudad(equipoDTO.getCiudad());
        
        Equipo equipoActualizado = repositorio.save(equipoExistente);
        return convertirADTO(equipoActualizado);
    }

    private EquipoDTO convertirADTO(Equipo equipo) 
    {
        EquipoDTO dto = new EquipoDTO();
        dto.setId(equipo.getId());
        dto.setNombre(equipo.getNombre());
        dto.setCiudad(equipo.getCiudad());
        return dto;
    }
}
