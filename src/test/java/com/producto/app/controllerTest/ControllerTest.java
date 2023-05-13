package com.producto.app.controllerTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.producto.app.models.Producto;
import com.producto.app.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void testGuardarProducto() throws Exception {

        //given

        given(productoService.save(any(Producto.class)))
                .willAnswer((invocation)->invocation.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)));
        //then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre",is(producto.getNombre())));

    }

    @Test
    void testObtenerTodosLosProductos() throws Exception {

        //given
        List<Producto> listProducts = new ArrayList<>();
        listProducts.add(producto);

        given(productoService.findAll()).willReturn(listProducts);

        //when

        ResultActions  response  = mockMvc.perform(get("/api/productos"));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(listProducts.size())));

    }

    @Test
    void testObtenerProductoPorId() throws Exception {

        Long productoId = 1L;

        given(productoService.findById(productoId)).willReturn(Optional.of(producto));


        ResultActions response = mockMvc.perform(get("/api/productos/{id}",productoId));


        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombre",is(producto.getNombre())));

    }

    @Test
    void ProductoNoEncontrado() throws Exception{
        //given
        long productoId = 1L;

        given(productoService.findById(productoId)).willReturn(Optional.empty());

        //when
        ResultActions response = mockMvc.perform(get("/api/productos/{id}",productoId));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void testActualizarProdcuto() throws Exception{
        //given
        long productoId = 1L;

        Producto productoActualizado = Producto.builder()
                .nombre("Christian Raul")
                .precio(15000.0)
                .build();


        given(productoService.findById(productoId)).willReturn(Optional.of(producto));
        given(productoService.save(any(Producto.class)))
                .willAnswer((invocation)->invocation.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(put("/api/productos/{id}",productoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoActualizado)));


        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombre",is(productoActualizado.getNombre())));

    }

    @Test
    void testActualizarEmpleadoNoEncontrado() throws Exception{

        //given
        long productoId = 1L;

        Producto productoActualizado = Producto.builder()
                .nombre("Christian Raul")
                .precio(15000.0)
                .build();

        given(productoService.findById(productoId)).willReturn(Optional.empty());
        given(productoService.save(any(Producto.class)))
                .willAnswer((invocation)->invocation.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(put("/api/productos/{id}",productoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoActualizado)));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void testEliminarEmpleado() throws Exception{
        //given
        long productoId = 1L;

        willDoNothing().given(productoService).deleteByid(productoId);

        //when
        ResultActions response = mockMvc.perform(delete("/api/productos/{id}",productoId));

        //then
        response.andExpect(status().isOk())
                .andDo(print());
    }







}
