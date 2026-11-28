package com.barberia.backend.ventas.dto;

import com.barberia.backend.ventas.model.MetodoPago;
import java.util.List;

public record VentaRequest(
        Long clienteId,
        MetodoPago metodoPago,
        List<DetalleVentaRequest> detalles
) {
}