/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eggcarol.storetechno.servicios;

import com.eggcarol.storetechno.entidades.Fabricante;
import com.eggcarol.storetechno.entidades.Producto;
import com.eggcarol.storetechno.exceptions.MyException;
import com.eggcarol.storetechno.repositorios.FabricanteRepositorio;
import com.eggcarol.storetechno.repositorios.ProductoRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Carolina
 */
@Service
public class ProductoService {
    //creamos métodos necesarios para la lógica del negocio

    //Inyección de dependencias
    @Autowired //le indicamos que esta variable va a ser inicializada por él. No necesitamos inicializarla
    private ProductoRepositorio productoRepositorio;//instanciamos un producto repositorio
    
    //ahora seteamos el fabricante
    @Autowired
    private FabricanteRepositorio fabricanteRepositorio;
    
    @Transactional
    public void crearProducto(String nombre, Double precio, String codigofabricante) throws MyException{
        
        validar(nombre, precio, codigofabricante);
        
        Fabricante fabricante = fabricanteRepositorio.findById(codigofabricante).get();//encuentra al fabricante por ID o codigo y lo devuelve
        
        Producto producto = new Producto();//creamos un producto
        
        producto.setNombre(nombre);//seteamos los parámetros, pedimos datos por formulario
        producto.setPrecio(precio);
               
        producto.setFabricante(fabricante);
        
        producto.setAlta(new Date());//ahora llamamos al repositorio
        
        productoRepositorio.save(producto);
        
    }
    
    public List<Producto> listarProductos(){
        
        List<Producto> productos = new ArrayList();
        productos = productoRepositorio.findAll();
        
        return productos;
    }
    
    public void modificarProducto(String codigo, String nombre, Double precio, String codigofabricante) throws MyException{
        
        validar(nombre, precio, codigofabricante);
        
        Optional<Producto> respuesta = productoRepositorio.findById(codigo);
        Optional<Fabricante> respuestaFabricante = fabricanteRepositorio.findById(codigofabricante);
        
        Fabricante fabricante = new Fabricante();//instanciamos un fabricante vacío
        
        if(respuestaFabricante.isPresent()){
            fabricante = respuestaFabricante.get();
        }
        if(respuesta.isPresent()){
            Producto producto = respuesta.get();
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setFabricante(fabricante);
            
            productoRepositorio.save(producto);//persistimos mediante el repositorio en la BD
            
        }
    }
    
    public Producto getOne(String codigo){
        return productoRepositorio.getOne(codigo);
    }
    
    public void validar(String nombre, Double precio, String codigofabricante) throws MyException{
        
        if(nombre.isEmpty()|| nombre==null){
            throw new MyException("El nombre no puede ser nulo o no puede estar vacío.");
        }
        if(precio==null){
            throw new MyException("El precio no puede ser nulo");
        }
        
        if(codigofabricante==null){
            throw new MyException("El codigo de fabricante no puede ser nulo o no puede estar vacío.");
        }
        
    }
    
}
