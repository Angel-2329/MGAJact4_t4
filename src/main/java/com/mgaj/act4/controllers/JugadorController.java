package com.mgaj.act4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgaj.act4.dtos.JugadorDTO;
import com.mgaj.act4.services.IJugadorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/api/jugadores")
public class JugadorController 
{
    @Autowired
    private IJugadorService servicio;

    @GetMapping
    public ResponseEntity<Page<JugadorDTO>> listarJugadores(@PageableDefault(page = 0, size = 10) Pageable pageable) 
    {
        Page <JugadorDTO> jugadores = servicio.listarJugadores(pageable);
        return ResponseEntity.ok(jugadores); 
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerJugador(@PathVariable Integer id) 
    {
        JugadorDTO jugador = servicio.obtenerPorId(id);
        if (jugador == null) 
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Jugador no encontrado"); 
        }
        return ResponseEntity.ok(jugador);
    }
    
    @PostMapping
    public ResponseEntity<?> guardarJugador(@Valid @RequestBody JugadorDTO jugadorDTO) 
    {
        try 
        {
            JugadorDTO nuevoJugador = servicio.guardarJugador(jugadorDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoJugador); 
        } 
        catch (RuntimeException e) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); 
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarJugador(@PathVariable Integer id, @Valid @RequestBody JugadorDTO jugadorDTO) 
    {
        try 
        {
            JugadorDTO actualizado = servicio.actualizarJugador(id, jugadorDTO);
            if(actualizado == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Jugador no encontrado");
            return ResponseEntity.ok(actualizado);
        } 
        catch (RuntimeException e) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarJugador(@PathVariable Integer id) 
    {
        servicio.eliminarJugador(id);
        return ResponseEntity.noContent().build(); 
    }
}

