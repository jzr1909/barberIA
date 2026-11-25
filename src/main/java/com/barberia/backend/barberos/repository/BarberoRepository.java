package com.barberia.backend.barberos.repository;

import com.barberia.backend.barberos.entity.Barbero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberoRepository extends JpaRepository<Barbero, Long> {

}