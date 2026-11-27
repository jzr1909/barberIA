
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

    // Consultar servicios (RF15)
    public List<ServicioEntity> listarServicios(
            Boolean activo, String nombre) {

        if (activo != null && nombre != null && !nombre.isBlank()) {
            return servicioRepository
                    .findByActivoAndNombreContainingIgnoreCase(
                            activo, nombre.trim());
        }

        if (activo != null) {
            return servicioRepository.findByActivo(activo);
        }

        if (nombre != null && !nombre.isBlank()) {
            return servicioRepository
                    .findByNombreContainingIgnoreCase(nombre.trim());
        }

        return servicioRepository.findAll();
    }

    // Buscar por ID
    public ServicioEntity obtenerPorId(Long id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Servicio no encontrado con ID: " + id));
    }

    // Validar información
    private void validarServicio(ServicioEntity servicio) {

        if (servicio.getNombre() == null ||
                servicio.getNombre().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El nombre es obligatorio");
        }

        if (servicio.getDescripcion() == null ||
                servicio.getDescripcion().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La descripción es obligatoria");
        }

        if (servicio.getPrecio() == null ||
                servicio.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El precio debe ser mayor que cero");
        }

        if (servicio.getDuracionMinutos() == null ||
                servicio.getDuracionMinutos() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La duración debe ser mayor que cero");
        }
    }

    // Registrar servicio (RF14)
    public ServicioEntity crearServicio(ServicioEntity nuevoServicio) {

        validarServicio(nuevoServicio);

        String nombre = nuevoServicio.getNombre().trim();

        if (servicioRepository.existsByNombreIgnoreCase(nombre)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un servicio con ese nombre");
        }

        nuevoServicio.setNombre(nombre);
        nuevoServicio.setId(null);
        nuevoServicio.setActivo(true);

        return servicioRepository.save(nuevoServicio);
    }

    // Actualizar servicio (RF16)
    public ServicioEntity actualizarServicio(
            Long id, ServicioEntity datosActualizados) {

        ServicioEntity existente = obtenerPorId(id);

        validarServicio(datosActualizados);

        String nombre = datosActualizados.getNombre().trim();

        if (servicioRepository
                .existsByNombreIgnoreCaseAndIdNot(nombre, id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe otro servicio con ese nombre");
        }

        existente.setNombre(nombre);
        existente.setDescripcion(datosActualizados.getDescripcion());
        existente.setPrecio(datosActualizados.getPrecio());
        existente.setDuracionMinutos(
                datosActualizados.getDuracionMinutos());

        return servicioRepository.save(existente);
    }

    // Actualizar parcialmente (RF17)
    public ServicioEntity actualizarParcialmente(
            Long id, ServicioParcialRequest cambios) {

        ServicioEntity existente = obtenerPorId(id);

        if (cambios.getNombre() != null) {
            String nombre = cambios.getNombre().trim();

            if (nombre.isBlank()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "El nombre no puede estar vacío");
            }

            if (servicioRepository
                    .existsByNombreIgnoreCaseAndIdNot(nombre, id)) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Ya existe otro servicio con ese nombre");
            }

            existente.setNombre(nombre);
        }

        if (cambios.getDescripcion() != null) {
            if (cambios.getDescripcion().isBlank()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "La descripción no puede estar vacía");
            }
            existente.setDescripcion(cambios.getDescripcion());
        }

        if (cambios.getPrecio() != null) {
            if (cambios.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "El precio debe ser mayor que cero");
            }
            existente.setPrecio(cambios.getPrecio());
        }

        if (cambios.getDuracionMinutos() != null) {
            if (cambios.getDuracionMinutos() <= 0) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "La duración debe ser mayor que cero");
            }
            existente.setDuracionMinutos(
                    cambios.getDuracionMinutos());
        }

        if (cambios.getActivo() != null) {
            existente.setActivo(cambios.getActivo());
        }

        return servicioRepository.save(existente);
    }

    // Eliminar servicio físicamente
    public void eliminarServicio(Long id) {
        ServicioEntity existente = obtenerPorId(id);
        servicioRepository.delete(existente);
    }

    // Datos para actualización parcial
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
