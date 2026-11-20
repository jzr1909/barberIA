package com.barberia.backend.inventario.dto;

public class ProductoDto {

    private Long idProdcuto;
    private String nombre;
    private String descripcion;
    private String categoria;
    private double precioVenta;
    private Integer stockActual;
    private Boolean estado;

    public ProductoDto(Long idProdcuto, String nombre, String descripcion, double precioVenta, String categoria, Integer stockActual, Boolean estado) {
        this.idProdcuto = idProdcuto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.categoria = categoria;
        this.stockActual = stockActual;
        this.estado = estado;
    }

    public ProductoDto() {
    }

    public Long getIdProdcuto() {
        return idProdcuto;
    }

    public void setIdProdcuto(Long idProdcuto) {
        this.idProdcuto = idProdcuto;
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
