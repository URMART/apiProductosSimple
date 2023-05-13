package com.producto.app.service;

import com.producto.app.models.Dao.ProductoRepositorio;
import com.producto.app.models.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductoServiceTest {


    @Mock
    private ProductoRepositorio productoRepositorio;

    @InjectMocks
    private ProductoServiceImp productoService;


    private Producto producto;

    @BeforeEach
    void setup(){
        producto = Producto.builder()
                .id(1L)
                .nombre("Arroz Diana")
                .precio(2000.0)
                .build();
    }

    @Test
    @Rollback(false)
    void findByNombre() {

        //given
        given(productoRepositorio.findByNombre(producto.getNombre()))
                .willReturn(producto);
        //wen
        Producto productoEncontrado = productoService.findByNombre(producto.getNombre());
        //then
        assertEquals(productoEncontrado.getNombre(),producto.getNombre());

    }

    @Test
    void findAll() {

        //given
        List<Producto> listProducts = new ArrayList<>();
        listProducts.add(producto);
        given(productoRepositorio.findAll())
                .willReturn(listProducts);
        //when
        List<Producto> productos = productoService.findAll();
        //then
        assertNotNull(productos);
    }

    @Test
    void findById() {

        //given
        given(productoRepositorio.findById(producto.getId()))
                .willReturn(Optional.of(producto));
        //when
        Optional<Producto> productoId= productoService.findById(producto.getId());
        //then
        assertEquals(productoId.get().getId(), producto.getId());


    }

    @Test
    void save() {
        //given
        given(productoRepositorio.findById(producto.getId()))
                .willReturn(Optional.of(producto));
        given(productoRepositorio.save(producto))
                .willReturn(producto);
        String edicion = "Arroz Caribe";

        //when
        Optional<Producto> productoId= productoService.findById(producto.getId());

        productoId.get().setNombre(edicion);
        Producto productoGuardado = productoService.save(productoId.get());

        //then
        assertEquals(productoGuardado.getNombre(),edicion);

    }

    @Test
    void deleteByid() {

        //given
        long productoId = 1L;
        willDoNothing().given(productoRepositorio).deleteById(productoId);


        //when
        productoService.deleteByid(productoId);

        //then
        verify(productoRepositorio,times(1)).deleteById(productoId);

    }
}