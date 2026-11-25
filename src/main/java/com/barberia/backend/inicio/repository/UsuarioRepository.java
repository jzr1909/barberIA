package com.barberia.backend.inicio.repository;

import com.barberia.backend.inicio.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreoAndPassword(String correo, String password);

    boolean existsByCorreo(String correo);

    // "Rol_Nombre" recorre la relación con la tabla roles
    List<Usuario> findByRolNombreIgnoreCase(String nombre);

    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
}