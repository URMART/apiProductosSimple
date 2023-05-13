package com.producto.app.service;

import com.producto.app.models.Producto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface ProductoService {
    public Producto findByNombre(String nombre);

    public List<Producto> findAll();


    public Optional<Producto> findById(Long id);


    public Producto save(Producto producto) ;

    public void deleteByid(Long id);

}
