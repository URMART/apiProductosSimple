package com.producto.app.models.Dao;

import com.producto.app.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;




public interface ProductoRepositorio  extends JpaRepository<Producto,Long> {
    public Producto findByNombre(String nombre);
}
