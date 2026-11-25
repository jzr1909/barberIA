package com.barberia.backend.inicio.controller;

import com.barberia.backend.inicio.dto.UsuarioDto;
import com.barberia.backend.inicio.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // ---- Inicio de sesión: POST /api/usuarios/login ----
    @PostMapping("/login")
    public ResponseEntity<UsuarioDto> login(@RequestBody UsuarioDto usuarioDto) {
        UsuarioDto usuario = usuarioService.login(usuarioDto.getCorreo(), usuarioDto.getPassword());
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(usuario);
    }

    // ---- Consultar todos: GET /api/usuarios?rol=CLIENTE o ?nombre=ana ----
    @GetMapping
    public List<UsuarioDto> getUsuarios(
            @RequestParam(required = false) String rol,
            @RequestParam(required = false) String nombre) {
        return usuarioService.buscarUsuarios(rol, nombre);
    }

    // ---- Consultar uno: GET /api/usuarios/{id} ----
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> getUsuarioById(@PathVariable Long id) {
        UsuarioDto usuario = usuarioService.getUsuarioById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    // ---- Registrar: POST /api/usuarios ----
    @PostMapping
    public ResponseEntity<UsuarioDto> createUsuario(@RequestBody UsuarioDto usuarioDto) {
        if (usuarioService.existeCorreo(usuarioDto.getCorreo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        UsuarioDto creado = usuarioService.saveUsuario(usuarioDto);
        URI ubicacion = URI.create("/api/usuarios/" + creado.getIdUsuario());
        return ResponseEntity.created(ubicacion).body(creado);
    }

    // ---- Actualizar: PUT /api/usuarios/{id} ----
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> updateUsuario(@PathVariable Long id, @RequestBody UsuarioDto usuarioDto) {
        UsuarioDto actualizado = usuarioService.updateUsuario(id, usuarioDto);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    // ---- Actualizar parcialmente: PATCH /api/usuarios/{id} ----
    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioDto> patchUsuario(@PathVariable Long id, @RequestBody UsuarioDto usuarioDto) {
        UsuarioDto actualizado = usuarioService.patchUsuario(id, usuarioDto);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    // ---- Eliminar: DELETE /api/usuarios/{id} ----
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        if (usuarioService.getUsuarioById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // ---- Errores de negocio (rol inexistente) -> 400 ----
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}