package com.mgaj.act4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mgaj.act4.models.Usuario;

public interface  UsuarioRepository extends  JpaRepository<Usuario, Integer>
{
    Optional<Usuario> findByEmail (String correo);
}
