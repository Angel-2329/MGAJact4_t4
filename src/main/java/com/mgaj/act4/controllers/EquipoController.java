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

import com.mgaj.act4.dtos.EquipoDTO;
import com.mgaj.act4.services.IEquipoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController 
{
    @Autowired
    private IEquipoService equipoServicio;

    @GetMapping
    public ResponseEntity<Page<EquipoDTO>> listarEquipos(@PageableDefault(page = 0, size = 10) Pageable pageable) 
    {
        Page <EquipoDTO> equipos = equipoServicio.listarEquipos(pageable);
        return ResponseEntity.ok(equipos);
    }

    @GetMapping("/{id}")
    public ResponseEntity <?> obtenerEquipo (@PathVariable Integer id) 
    {
        Object equipo = equipoServicio.obtenerPorId(id);
        if (equipo == null) 
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipo no encontrado"); 
        }
        return ResponseEntity.ok(equipo);
    }

    @PostMapping
    public ResponseEntity<?> guardarEquipo(@Valid @RequestBody EquipoDTO equipoDTO) 
    {
        equipoServicio.guardarEquipo(equipoDTO); 
        return ResponseEntity.status(HttpStatus.CREATED).body("Equipo creado exitosamente"); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEquipo(@PathVariable Integer id, @Valid @RequestBody EquipoDTO equipoDTO) 
    {
        EquipoDTO equipoActualizado = equipoServicio.actualizarEquipo(id, equipoDTO);
        if (equipoActualizado == null) 
            {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipo no encontrado");
        }
        return ResponseEntity.ok(equipoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEquipo(@PathVariable Integer id) 
    {
        equipoServicio.eliminarEquipo(id);
        return ResponseEntity.noContent().build();
    }
}
