package com.barberia.backend.ventas.dto;

import com.barberia.backend.ventas.model.TipoItem;

public record DetalleVentaRequest(
        TipoItem tipoItem,
        Long itemId,
        Integer cantidad
) {
}