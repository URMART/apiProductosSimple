package com.producto.app.controller;

import com.producto.app.models.Producto;
import com.producto.app.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public List<Producto> getProductos(){
        return  productoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable("id") long productoId){
        return productoService.findById(productoId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Producto guardarEmpleado(@RequestBody Producto producto){
        return productoService.save(producto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable("id") long productoId){
        productoService.deleteByid(productoId);
        return new ResponseEntity<String>("Producto Eliminado",HttpStatus.OK);

    }


    @PutMapping("/{id}")
    public ResponseEntity<Producto>  update(@PathVariable Long id,@RequestBody Producto producto){

        return productoService.findById(id)
                .map(productoGuardado->{
                    productoGuardado.setNombre(producto.getNombre());
                    productoGuardado.setPrecio(producto.getPrecio());
                    Producto productoActualizado = productoService.save(productoGuardado);
                    return new ResponseEntity<>(productoActualizado,HttpStatus.OK);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
