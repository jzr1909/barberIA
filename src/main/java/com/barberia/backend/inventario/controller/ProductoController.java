package com.barberia.backend.inventario.controller;


import com.barberia.backend.inventario.dto.ProductoDto;
import com.barberia.backend.inventario.repository.ProductoRepository;
import com.barberia.backend.inventario.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoRepository productoRepository;
    private final ProductoService productoService;

    public ProductoController(ProductoRepository productoRepository, ProductoService productoService) {
        this.productoRepository = productoRepository;
        this.productoService = productoService;
    }



   @GetMapping("/{id}")
    public ResponseEntity <ProductoDto> getProductoById(@PathVariable Long id){

        ProductoDto productoDto = productoService.getProductById(id);

        if(productoDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productoDto);
    }

    @PostMapping
    public ResponseEntity <ProductoDto> createProducto (@RequestBody ProductoDto productoDto){

        ProductoDto guardado = productoService.saveProducto(productoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);

    }

    @PutMapping("/{id}")
    public ResponseEntity <ProductoDto> updateProducto (@PathVariable Long id, @RequestBody ProductoDto productoDto){

        ProductoDto existingProducto = productoService.getProductById(id);
        if(existingProducto == null){
            return ResponseEntity.notFound().build();
        }

        ProductoDto correctUpdate = productoService.updateProducto(existingProducto, productoDto);

        return ResponseEntity.ok(correctUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletPersona(@PathVariable Long id){

        ProductoDto productoDto = productoService.getProductById(id);

        if(productoDto == null){
            return ResponseEntity.notFound().build();
        }

        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }
}
