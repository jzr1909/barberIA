package com.barberia.backend.inventario.controller;

import com.barberia.backend.inventario.dto.ProductoDto;
import com.barberia.backend.inventario.repository.ProductoRepository;
import com.barberia.backend.inventario.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoRepository productoRepository;

    public ProductoController(ProductoService productoService, ProductoRepository productoRepository) {
        this.productoService = productoService;
        this.productoRepository = productoRepository;
    }


   /* @GetMapping("{id}")
    public ResponseEntity <ProductoDto> getProductoById( @PathVariable int id){

        return ResponseEntity.ok();
    }*/
}
