package com.barberia.backend.inventario.entity;


import jakarta.persistence.*;




@Entity
@Table(name = "productos")
public class Producto {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_producto")
        private Long idProducto;

        @Column(nullable = false, length = 100)
        private String nombre;

        @Column(length = 255)
        private String descripcion;

        @Column(length = 60)
        private String categoria;

        @Column(name = "precio_venta", nullable = false)
        private double precioVenta;

        @Column(name = "stock_actual", nullable = false)
        private Integer stockActual;

        @Column(nullable = false)
        private Boolean estado = true;

        public Producto() {
        }

        public Producto(String nombre, String descripcion,
                        String categoria, double precioVenta,
                        Integer stockActual) {
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.categoria = categoria;
            this.precioVenta = precioVenta;
            this.stockActual = stockActual;
            this.estado = true;
        }

        public Long getIdProducto() {
            return idProducto;
        }

        public void setIdProducto(Long idProducto) {
            this.idProducto = idProducto;
        }

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

        public String getCategoria() {
            return categoria;
        }

        public void setCategoria(String categoria) {
            this.categoria = categoria;
        }

        public double getPrecioVenta() {
            return precioVenta;
        }

        public void setPrecioVenta(double precioVenta) {
            this.precioVenta = precioVenta;
        }

        public Integer getStockActual() {
            return stockActual;
        }

        public void setStockActual(Integer stockActual) {
            this.stockActual = stockActual;
        }

        public Boolean getEstado() {
            return estado;
        }

        public void setEstado(Boolean estado) {
            this.estado = estado;
        }
}



