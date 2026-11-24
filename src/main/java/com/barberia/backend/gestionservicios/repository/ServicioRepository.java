package com.barberia.backend.gestionservicios.repository;

import com.barberia.backend.gestionservicios.entity.ServicioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<ServicioEntity, Long> {

    List<ServicioEntity> findByActivo(Boolean activo);

    List<ServicioEntity> findByNombreContainingIgnoreCase(String nombre);

    List<ServicioEntity> findByActivoAndNombreContainingIgnoreCase(Boolean activo, String nombre);
}