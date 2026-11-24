package com.barberia.backend.gestionservicios.controller;

import com.barberia.backend.gestionservicios.entity.ServicioEntity;
import com.barberia.backend.gestionservicios.service.ServicioService;
import com.barberia.backend.gestionservicios.service.ServicioService.ServicioParcialRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    // GET /api/servicios?activo=true&nombre=corte
    @GetMapping
    public ResponseEntity<List<ServicioEntity>> listarServicios(
            @RequestParam(required = false) Boolean activo,
            @RequestParam(required = false) String nombre) {
        List<ServicioEntity> servicios = servicioService.listarServicios(activo, nombre);
        return ResponseEntity.ok(servicios);
    }

    // GET /api/servicios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ServicioEntity> obtenerServicioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicioService.obtenerPorId(id));
    }

    // POST /api/servicios
    @PostMapping
    public ResponseEntity<ServicioEntity> registrarServicio(@RequestBody ServicioEntity servicio) {
        ServicioEntity creado = servicioService.crearServicio(servicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // PUT /api/servicios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ServicioEntity> actualizarServicio(
            @PathVariable Long id,
            @RequestBody ServicioEntity servicio) {
        ServicioEntity actualizado = servicioService.actualizarServicio(id, servicio);
        return ResponseEntity.ok(actualizado);
    }

    // PATCH /api/servicios/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<ServicioEntity> actualizarParcialmente(
            @PathVariable Long id,
            @RequestBody ServicioParcialRequest cambios) {
        ServicioEntity actualizado = servicioService.actualizarParcialmente(id, cambios);
        return ResponseEntity.ok(actualizado);
    }

    // DELETE /api/servicios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarServicio(@PathVariable Long id) {
        servicioService.eliminarServicio(id);
        return ResponseEntity.noContent().build();
    }
}