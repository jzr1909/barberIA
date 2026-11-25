package com.barberia.backend.barberos.service;

import com.barberia.backend.barberos.entity.Barbero;
import com.barberia.backend.barberos.repository.BarberoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BarberoService {

    private final BarberoRepository barberoRepository;

    public BarberoService(BarberoRepository barberoRepository) {
        this.barberoRepository = barberoRepository;
    }

    // Registrar barbero
    public Barbero guardarBarbero(Barbero barbero) {
        barbero.setId(null);
        return barberoRepository.save(barbero);
    }

    // Buscar barbero por ID
    public Optional<Barbero> buscarBarbero(Long id) {
        return barberoRepository.findById(id);
    }

    // Listar todos los barberos
    public List<Barbero> listarBarberos() {
        return barberoRepository.findAll();
    }

    // Actualizar barbero
    public Barbero actualizarBarbero(Long id, Barbero barbero) {
        barbero.setId(id);
        return barberoRepository.save(barbero);
    }

    // Eliminar barbero
    public void eliminarBarbero(Long id) {
        barberoRepository.deleteById(id);
    }
}