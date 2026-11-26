package com.barberia.backend.ventas.controller;

import com.barberia.backend.ventas.dto.*;
import com.barberia.backend.ventas.model.*;
import com.barberia.backend.ventas.service.VentaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService service;

    public VentaController(VentaService service) {
        this.service = service;
    }

    // GET: Consultar todas las ventas
    @GetMapping
    public ResponseEntity<List<Venta>> listar(
            @RequestParam(required = false) EstadoVenta estado) {

        if (estado != null) {
            return ResponseEntity.ok(
                    service.listarPorEstado(estado));
        }

        return ResponseEntity.ok(service.listar());
    }

    // GET: Consultar una venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Venta> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // POST: Registrar una venta
    @PostMapping
    public ResponseEntity<Venta> registrar(
            @RequestBody VentaRequest request) {

        Venta venta = service.registrar(request);

        return ResponseEntity
                .created(URI.create("/api/ventas/" + venta.getId()))
                .body(venta);
    }

    // PUT: Actualizar datos de una venta
    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizar(
            @PathVariable Long id,
            @RequestBody VentaRequest request) {

        return ResponseEntity.ok(
                service.actualizar(id, request));
    }

    // PATCH: Actualizar metodo de pago
    @PatchMapping("/{id}")
    public ResponseEntity<Venta> actualizarParcial(
            @PathVariable Long id,
            @RequestBody Map<String, MetodoPago> request) {

        return ResponseEntity.ok(
                service.actualizarMetodoPago(
                        id, request.get("metodoPago")));
    }

    // DELETE: Anular una venta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> anular(
            @PathVariable Long id,
            @RequestParam String motivo) {

        service.anular(id, motivo);

        return ResponseEntity.noContent().build();
    }

    // GET: Consultar comprobante interno
    @GetMapping("/{id}/comprobante")
    public ResponseEntity<?> comprobante(
            @PathVariable Long id) {

        Venta venta = service.buscarPorId(id);

        return ResponseEntity.ok(Map.of(
                "numeroComprobante", "BIA-" + venta.getId(),
                "fecha", venta.getFecha(),
                "metodoPago", venta.getMetodoPago(),
                "estado", venta.getEstado(),
                "detalles", venta.getDetalles(),
                "total", venta.getTotal()
        ));
    }
}