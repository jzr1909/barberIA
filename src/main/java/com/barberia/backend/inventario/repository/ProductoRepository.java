package com.barberia.backend.inventario.repository;

import com.barberia.backend.inventario.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends  JpaRepository <Producto, Long>{
}
