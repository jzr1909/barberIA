package com.barberia.backend.ventas.service;

import com.barberia.backend.ventas.dto.*;
import com.barberia.backend.ventas.model.*;
import com.barberia.backend.ventas.repository.VentaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class VentaService {

    private final VentaRepository repository;

    public VentaService(VentaRepository repository) {
        this.repository = repository;
    }

    // Consultar todas las ventas
    public List<Venta> listar() {
        return repository.findAll();
    }

    // Consultar por estado
    public List<Venta> listarPorEstado(EstadoVenta estado) {
        return repository.findByEstado(estado);
    }

    // Consultar una venta
    public Venta buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Venta no encontrada"));
    }

    // Registrar una venta
    public Venta registrar(VentaRequest request) {

        if (request.metodoPago() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El metodo de pago es obligatorio");
        }

        if (request.detalles() == null ||
                request.detalles().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La venta debe tener al menos un item");
        }

        Venta venta = new Venta();
        venta.setClienteId(request.clienteId());
        venta.setMetodoPago(request.metodoPago());

        BigDecimal total = BigDecimal.ZERO;

        for (DetalleVentaRequest item : request.detalles()) {

            if (item == null || item.tipoItem() == null ||
                    item.itemId() == null ||
                    item.cantidad() == null ||
                    item.cantidad() <= 0) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Datos del item incorrectos");
            }

            BigDecimal precio = obtenerPrecio(
                    item.tipoItem(), item.itemId());

            BigDecimal subtotal = precio.multiply(
                    BigDecimal.valueOf(item.cantidad()));

            DetalleVenta detalle = new DetalleVenta();

            detalle.setTipoItem(item.tipoItem());
            detalle.setItemId(item.itemId());
            detalle.setNombre(obtenerNombre(
                    item.tipoItem(), item.itemId()));
            detalle.setCantidad(item.cantidad());
            detalle.setPrecioUnitario(precio);
            detalle.setSubtotal(subtotal);

            venta.agregarDetalle(detalle);

            total = total.add(subtotal);
        }

        venta.setTotal(total);

        return repository.save(venta);
    }

    // Actualizar datos de una venta
    public Venta actualizar(Long id, VentaRequest request) {

        Venta venta = buscarPorId(id);

        validarEstado(venta);

        if (request.metodoPago() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El metodo de pago es obligatorio");
        }

        venta.setClienteId(request.clienteId());
        venta.setMetodoPago(request.metodoPago());

        return repository.save(venta);
    }

    // Actualizar parcialmente el metodo de pago
    public Venta actualizarMetodoPago(Long id,
                                      MetodoPago metodoPago) {

        Venta venta = buscarPorId(id);

        validarEstado(venta);

        if (metodoPago == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Metodo de pago obligatorio");
        }

        venta.setMetodoPago(metodoPago);

        return repository.save(venta);
    }

    // Anular una venta
    public Venta anular(Long id, String motivo) {

        Venta venta = buscarPorId(id);

        validarEstado(venta);

        if (motivo == null || motivo.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El motivo de anulacion es obligatorio");
        }

        venta.setEstado(EstadoVenta.ANULADA);
        venta.setMotivoAnulacion(motivo);

        return repository.save(venta);
    }

    // Validar que la venta no este anulada
    private void validarEstado(Venta venta) {

        if (venta.getEstado() == EstadoVenta.ANULADA) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "La venta ya se encuentra anulada");
        }
    }

    // Catalogo provisional de precios
    private BigDecimal obtenerPrecio(TipoItem tipo, Long id) {

        if (tipo == TipoItem.PRODUCTO) {
            if (id == 1L) return new BigDecimal("15000");
            if (id == 2L) return new BigDecimal("20000");
        }

        if (tipo == TipoItem.SERVICIO) {
            if (id == 1L) return new BigDecimal("18000");
            if (id == 2L) return new BigDecimal("10000");
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Producto o servicio no encontrado");
    }

    // Catalogo provisional de nombres
    private String obtenerNombre(TipoItem tipo, Long id) {

        if (tipo == TipoItem.PRODUCTO) {
            if (id == 1L) return "Cera para cabello";
            if (id == 2L) return "Shampoo";
        }

        if (tipo == TipoItem.SERVICIO) {
            if (id == 1L) return "Corte de cabello";
            if (id == 2L) return "Arreglo de barba";
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Item no encontrado");
    }
}