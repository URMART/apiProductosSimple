package com.producto.app.models;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Builder
@Entity
@Table(name = "productos_test")
public class Producto {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private  Long id;


    @Column(unique = true)
    private String nombre;

    private  Double precio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Producto(Long id, String nombre, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public Producto(String nombre, Double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public Producto() {
    }
}
