package com.barberia.backend.inicio.service;

import com.barberia.backend.inicio.dto.UsuarioDto;
import com.barberia.backend.inicio.entity.Rol;
import com.barberia.backend.inicio.entity.Usuario;
import com.barberia.backend.inicio.repository.RolRepository;
import com.barberia.backend.inicio.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    // ---- Consultar todos, con filtros opcionales ----
    public List<UsuarioDto> buscarUsuarios(String rol, String nombre) {
        List<Usuario> usuarios;
        if (rol != null && !rol.isBlank()) {
            usuarios = usuarioRepository.findByRolNombreIgnoreCase(rol);
        } else if (nombre != null && !nombre.isBlank()) {
            usuarios = usuarioRepository.findByNombreContainingIgnoreCase(nombre);
        } else {
            usuarios = usuarioRepository.findAll();
        }
        List<UsuarioDto> lista = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            lista.add(convertToDto(usuario));
        }
        return lista;
    }

    // ---- Consultar uno ----
    public UsuarioDto getUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    public boolean existeCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    // ---- Inicio de sesión: solo usuarios activos ----
    public UsuarioDto login(String correo, String password) {
        return usuarioRepository.findByCorreoAndPassword(correo, password)
                .filter(u -> Boolean.TRUE.equals(u.getEstado()))
                .map(this::convertToDto)
                .orElse(null);
    }

    // ---- Registrar ----
    public UsuarioDto saveUsuario(UsuarioDto dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setPassword(dto.getPassword());
        usuario.setFotoPerfil(dto.getFotoPerfil());
        usuario.setEstado(true);
        usuario.setRol(buscarRol(dto.getRol()));
        return convertToDto(usuarioRepository.save(usuario));
    }

    // ---- Actualizar (PUT): reemplaza los datos ----
    public UsuarioDto updateUsuario(Long id, UsuarioDto dto) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return null;
        }
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setFotoPerfil(dto.getFotoPerfil());
        usuario.setRol(buscarRol(dto.getRol()));
        if (dto.getEstado() != null) {
            usuario.setEstado(dto.getEstado());
        }
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuario.setPassword(dto.getPassword());
        }
        return convertToDto(usuarioRepository.save(usuario));
    }

    // ---- Actualizar parcialmente (PATCH): solo cambia los campos enviados ----
    public UsuarioDto patchUsuario(Long id, UsuarioDto dto) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return null;
        }
        if (dto.getNombre() != null) usuario.setNombre(dto.getNombre());
        if (dto.getApellido() != null) usuario.setApellido(dto.getApellido());
        if (dto.getCorreo() != null) usuario.setCorreo(dto.getCorreo());
        if (dto.getTelefono() != null) usuario.setTelefono(dto.getTelefono());
        if (dto.getFotoPerfil() != null) usuario.setFotoPerfil(dto.getFotoPerfil());
        if (dto.getEstado() != null) usuario.setEstado(dto.getEstado());
        if (dto.getRol() != null) usuario.setRol(buscarRol(dto.getRol()));
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuario.setPassword(dto.getPassword());
        }
        return convertToDto(usuarioRepository.save(usuario));
    }

    // ---- Eliminar ----
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // ---- Auxiliares ----

    // Busca el rol por nombre. Si no existe, lanza excepción (la API responde 400).
    private Rol buscarRol(String nombreRol) {
        String nombre = (nombreRol == null || nombreRol.isBlank())
                ? "CLIENTE"
                : nombreRol.trim();
        return rolRepository.findByNombreIgnoreCase(nombre)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El rol '" + nombre + "' no existe. Use ADMIN, BARBERO o CLIENTE."));
    }

    private UsuarioDto convertToDto(Usuario usuario) {
        UsuarioDto dto = new UsuarioDto();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setCorreo(usuario.getCorreo());
        dto.setTelefono(usuario.getTelefono());
        dto.setRol(usuario.getRol().getNombre());
        dto.setFotoPerfil(usuario.getFotoPerfil());
        dto.setEstado(usuario.getEstado());
        // password intencionalmente no se copia
        return dto;
    }
}