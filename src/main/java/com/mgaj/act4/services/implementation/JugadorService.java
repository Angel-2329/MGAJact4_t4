package com.mgaj.act4.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mgaj.act4.dtos.JugadorDTO;
import com.mgaj.act4.models.Equipo;
import com.mgaj.act4.models.Jugador;
import com.mgaj.act4.repository.EquipoRepository;
import com.mgaj.act4.repository.JugadorRepository;
import com.mgaj.act4.services.IJugadorService;

@Service
public class JugadorService implements IJugadorService 
{
    @Autowired
    private JugadorRepository repositorioJugador;
    
    @Autowired
    private EquipoRepository repositorioEquipo;

    @Override
    public Page<JugadorDTO> listarJugadores(Pageable pageable)
    {
        Page<Jugador> jugadores = repositorioJugador.findAll(pageable);
        return jugadores.map(this::convertirADTO);
    }

    @Override
    public JugadorDTO guardarJugador(JugadorDTO jugadorDTO)
    {
        Jugador jugador = new Jugador();
        jugador.setNombre(jugadorDTO.getNombre());
        jugador.setPosicion(jugadorDTO.getPosicion());
        jugador.setNumeroCamiseta(jugadorDTO.getNumeroCamiseta());
        
        Equipo equipo = repositorioEquipo.findById(jugadorDTO.getEquipoId())
            .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        jugador.setEquipo(equipo);

        Jugador jugadorGuardado = repositorioJugador.save(jugador);
        return convertirADTO(jugadorGuardado);
    }

    @Override
    public JugadorDTO obtenerPorId(Integer id)
    {
        Jugador jugador = repositorioJugador.findById(id).orElse(null);
        if(jugador == null) return null;
        return convertirADTO(jugador);
    }

    @Override
    public void eliminarJugador(Integer id)
    {
        repositorioJugador.deleteById(id);
    }
    
    @Override
    public JugadorDTO actualizarJugador(Integer id, JugadorDTO jugadorDTO) 
    {
        Jugador jugadorExistente = repositorioJugador.findById(id).orElse(null);
        if (jugadorExistente == null) return null;

        jugadorExistente.setNombre(jugadorDTO.getNombre());
        jugadorExistente.setPosicion(jugadorDTO.getPosicion());
        jugadorExistente.setNumeroCamiseta(jugadorDTO.getNumeroCamiseta());
        
        Equipo equipo = repositorioEquipo.findById(jugadorDTO.getEquipoId())
            .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        jugadorExistente.setEquipo(equipo);

        Jugador jugadorActualizado = repositorioJugador.save(jugadorExistente);
        return convertirADTO(jugadorActualizado);
    }

    private JugadorDTO convertirADTO(Jugador jugador) 
    {
        JugadorDTO dto = new JugadorDTO();
        dto.setId(jugador.getId());
        dto.setNombre(jugador.getNombre());
        dto.setPosicion(jugador.getPosicion());
        dto.setNumeroCamiseta(jugador.getNumeroCamiseta());
        dto.setEquipoId(jugador.getEquipo().getId()); 
        return dto;
    }
}
