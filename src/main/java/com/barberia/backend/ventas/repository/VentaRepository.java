package com.barberia.backend.ventas.repository;

import com.barberia.backend.ventas.model.Venta;
import com.barberia.backend.ventas.model.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByEstado(EstadoVenta estado);

    List<Venta> findByClienteId(Long clienteId);
}