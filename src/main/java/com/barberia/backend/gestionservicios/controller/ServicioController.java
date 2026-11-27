
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
@CrossOrigin(origins = "*")
public class ServicioController {

    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    // Consultar servicios (RF15)
    @GetMapping
    public ResponseEntity<List<ServicioEntity>> listarServicios(
            @RequestParam(required = false) Boolean activo,
            @RequestParam(required = false) String nombre) {

        return ResponseEntity.ok(
                servicioService.listarServicios(activo, nombre));
    }

    // Buscar servicio por ID
    @GetMapping("/{id}")
    public ResponseEntity<ServicioEntity> obtenerServicioPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                servicioService.obtenerPorId(id));
    }

    // Registrar servicio (RF14)
    @PostMapping
    public ResponseEntity<ServicioEntity> registrarServicio(
            @RequestBody ServicioEntity servicio) {

        ServicioEntity creado =
                servicioService.crearServicio(servicio);

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // Actualizar servicio (RF16)
    @PutMapping("/{id}")
    public ResponseEntity<ServicioEntity> actualizarServicio(
            @PathVariable Long id,
            @RequestBody ServicioEntity servicio) {

        return ResponseEntity.ok(
                servicioService.actualizarServicio(id, servicio));
    }

    // Inactivar y reactivar servicio (RF17)
    @PatchMapping("/{id}")
    public ResponseEntity<ServicioEntity> actualizarParcialmente(
            @PathVariable Long id,
            @RequestBody ServicioParcialRequest cambios) {

        return ResponseEntity.ok(
                servicioService.actualizarParcialmente(id, cambios));
    }

    // Eliminar servicio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarServicio(
            @PathVariable Long id) {

        servicioService.eliminarServicio(id);

        return ResponseEntity.noContent().build();
    }
}
