package com.barberia.backend.gestionservicios.service;

import com.barberia.backend.gestionservicios.entity.ServicioEntity;
import com.barberia.backend.gestionservicios.repository.ServicioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    public List<ServicioEntity> listarServicios(Boolean activo, String nombre) {
        if (activo != null && nombre != null && !nombre.isBlank()) {
            return servicioRepository.findByActivoAndNombreContainingIgnoreCase(activo, nombre.trim());
        }
        if (activo != null) {
            return servicioRepository.findByActivo(activo);
        }
        if (nombre != null && !nombre.isBlank()) {
            return servicioRepository.findByNombreContainingIgnoreCase(nombre.trim());
        }
        return servicioRepository.findAll();
    }

    public ServicioEntity obtenerPorId(Long id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Servicio no encontrado con ID: " + id));
    }

    public ServicioEntity crearServicio(ServicioEntity nuevoServicio) {
        if (nuevoServicio.getNombre() == null || nuevoServicio.getNombre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del servicio es obligatorio");
        }
        if (nuevoServicio.getPrecio() == null || nuevoServicio.getPrecio().compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio debe ser mayor o igual a 0");
        }
        if (nuevoServicio.getDuracionMinutos() == null || nuevoServicio.getDuracionMinutos() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La duración en minutos debe ser mayor a 0");
        }
        if (nuevoServicio.getActivo() == null) {
            nuevoServicio.setActivo(true);
        }
        nuevoServicio.setId(null);
        return servicioRepository.save(nuevoServicio);
    }

    public ServicioEntity actualizarServicio(Long id, ServicioEntity datosActualizados) {
        ServicioEntity existente = obtenerPorId(id);

        if (datosActualizados.getNombre() == null || datosActualizados.getNombre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del servicio es obligatorio");
        }
        if (datosActualizados.getPrecio() == null || datosActualizados.getPrecio().compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio debe ser mayor o igual a 0");
        }
        if (datosActualizados.getDuracionMinutos() == null || datosActualizados.getDuracionMinutos() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La duración en minutos debe ser mayor a 0");
        }

        existente.setNombre(datosActualizados.getNombre().trim());
        existente.setDescripcion(datosActualizados.getDescripcion());
        existente.setPrecio(datosActualizados.getPrecio());
        existente.setDuracionMinutos(datosActualizados.getDuracionMinutos());
        existente.setActivo(datosActualizados.getActivo() != null ? datosActualizados.getActivo() : existente.getActivo());

        return servicioRepository.save(existente);
    }

    public ServicioEntity actualizarParcialmente(Long id, ServicioParcialRequest cambios) {
        ServicioEntity existente = obtenerPorId(id);

        if (cambios.getNombre() != null && !cambios.getNombre().isBlank()) {
            existente.setNombre(cambios.getNombre().trim());
        }
        if (cambios.getDescripcion() != null) {
            existente.setDescripcion(cambios.getDescripcion());
        }
        if (cambios.getPrecio() != null) {
            if (cambios.getPrecio().compareTo(BigDecimal.ZERO) < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio debe ser mayor o igual a 0");
            }
            existente.setPrecio(cambios.getPrecio());
        }
        if (cambios.getDuracionMinutos() != null) {
            if (cambios.getDuracionMinutos() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La duración en minutos debe ser mayor a 0");
            }
            existente.setDuracionMinutos(cambios.getDuracionMinutos());
        }
        if (cambios.getActivo() != null) {
            existente.setActivo(cambios.getActivo());
        }

        return servicioRepository.save(existente);
    }

    public void eliminarServicio(Long id) {
        ServicioEntity existente = obtenerPorId(id);
        servicioRepository.delete(existente);
    }

    public static class ServicioParcialRequest {
        private String nombre;
        private String descripcion;
        private BigDecimal precio;
        private Integer duracionMinutos;
        private Boolean activo;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public BigDecimal getPrecio() {
            return precio;
        }

        public void setPrecio(BigDecimal precio) {
            this.precio = precio;
        }

        public Integer getDuracionMinutos() {
            return duracionMinutos;
        }

        public void setDuracionMinutos(Integer duracionMinutos) {
            this.duracionMinutos = duracionMinutos;
        }

        public Boolean getActivo() {
            return activo;
        }

        public void setActivo(Boolean activo) {
            this.activo = activo;
        }
    }
}