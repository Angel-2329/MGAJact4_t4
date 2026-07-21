package com.mgaj.act4.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mgaj.act4.models.Jugador;

public interface  JugadorRepository extends JpaRepository<Jugador, Integer>
{
    
}
