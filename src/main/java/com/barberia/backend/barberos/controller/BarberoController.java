package com.barberia.backend.barberos.controller;

import com.barberia.backend.barberos.entity.Barbero;
import com.barberia.backend.barberos.service.BarberoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/barberos")
public class BarberoController {

    private final BarberoService barberoService;

    public BarberoController(BarberoService barberoService) {
        this.barberoService = barberoService;
    }

    // Registrar barbero
    @PostMapping
    public Barbero guardar(@RequestBody Barbero barbero) {
        return barberoService.guardarBarbero(barbero);
    }

    // Consultar barbero por ID
    @GetMapping("/{id}")
    public Barbero buscar(@PathVariable Long id) {
        return barberoService.buscarBarbero(id).orElse(null);
    }

    // Listar todos los barberos
    @GetMapping
    public List<Barbero> listar() {
        return barberoService.listarBarberos();
    }

    // Actualizar barbero
    @PutMapping("/{id}")
    public Barbero actualizar(
            @PathVariable Long id,
            @RequestBody Barbero barbero) {
        return barberoService.actualizarBarbero(id, barbero);
    }

    // Eliminar barbero
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        barberoService.eliminarBarbero(id);
    }
}