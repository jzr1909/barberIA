package com.barberia.backend.inventario.service;

import com.barberia.backend.inventario.dto.ProductoDto;
import com.barberia.backend.inventario.entity.Producto;
import com.barberia.backend.inventario.repository.ProductoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public ProductoDto getProductById(Long id){

        Producto producto = productoRepository.findById(id).orElse(null);

        if(producto == null){
            return null;
        }
        return convertToDto(producto);
    }

    public ProductoDto saveProducto (ProductoDto productoDto){


        Producto producto = convertToEntity(productoDto);
        Producto savedProducto = productoRepository.save(producto);

        return convertToDto(producto);
    }

    public ProductoDto updateProducto(ProductoDto existingProducto, ProductoDto productoDto){

        existingProducto.setNombre(productoDto.getNombre());
        existingProducto.setDescripcion(productoDto.getDescripcion());
        existingProducto.setCategoria(productoDto.getCategoria());
        existingProducto.setPrecioVenta(productoDto.getPrecioVenta());
        existingProducto.setStockActual(productoDto.getStockActual());
        existingProducto.setEstado(productoDto.getEstado());

        Producto productoEntity = convertToEntity(existingProducto);
        Producto savedEntity = productoRepository.save(productoEntity);


        return existingProducto;

    }

    public void deleteProducto (Long id){

        productoRepository.deleteById(id);
    }

    public ProductoDto convertToDto(Producto producto){

        ProductoDto dto = new ProductoDto();
        dto.setIdProdcuto(producto.getIdProducto());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setCategoria(producto.getCategoria());
        dto.setPrecioVenta(producto.getPrecioVenta());
        dto.setStockActual(producto.getStockActual());

        return dto;
    }

    public Producto convertToEntity(ProductoDto productoDto){

        Producto producto = new Producto();
        producto.setIdProducto(productoDto.getIdProdcuto());
        producto.setNombre(productoDto.getNombre());
        producto.setDescripcion(productoDto.getDescripcion());
        producto.setCategoria(productoDto.getCategoria());
        producto.setPrecioVenta(productoDto.getPrecioVenta());
        producto.setStockActual(productoDto.getStockActual());

        return producto;
    }
}
