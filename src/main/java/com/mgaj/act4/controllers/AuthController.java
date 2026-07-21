package com.mgaj.act4.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgaj.act4.dtos.LoginDTO;
import com.mgaj.act4.dtos.UsuarioRegistroDTO;
import com.mgaj.act4.models.Usuario;
import com.mgaj.act4.repository.UsuarioRepository;
import com.mgaj.act4.security.JwtTokenUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/api/auth")
public class AuthController 
{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UsuarioRegistroDTO registroDTO) 
    {
        if (usuarioRepository.findByEmail(registroDTO.getCorreo()).isPresent()) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: El email ya está en uso");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(registroDTO.getNombre());
        nuevoUsuario.setCorreo(registroDTO.getCorreo());
        
        nuevoUsuario.setContraseña(passwordEncoder.encode(registroDTO.getContraseña()));

        usuarioRepository.save(nuevoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) 
    {
        try 
        {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getCorreo(), loginDTO.getContraseña())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            
            String jwt = jwtTokenUtil.generarToken(userDetails.getUsername());

            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);
            
            return ResponseEntity.ok(response);
            
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Credenciales inválidas");
        }
    }
}
