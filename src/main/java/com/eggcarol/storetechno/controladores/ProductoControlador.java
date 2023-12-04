/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eggcarol.storetechno.controladores;

import com.eggcarol.storetechno.entidades.Fabricante;
import com.eggcarol.storetechno.entidades.Producto;
import com.eggcarol.storetechno.exceptions.MyException;
import com.eggcarol.storetechno.servicios.FabricanteService;
import com.eggcarol.storetechno.servicios.ProductoService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Carolina
 */
@Controller
@RequestMapping("/producto")
public class ProductoControlador {
    
    @Autowired
    private FabricanteService fabricanteService;
    
    @Autowired
    private ProductoService productoService;
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo){
        List<Fabricante> fabricantes = fabricanteService.listarFabricantes();
        modelo.addAttribute("fabricantes",fabricantes);
        return "producto_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam(required = false) Double precio, @RequestParam String codigofabricante, ModelMap modelo){
        
       
        try {
            // esto se hace como prueba inicial : System.out.println("Nombre: " + nombre); y si funciona entonces se reemplaza por lo que sigue
            
            productoService.crearProducto(nombre, precio, codigofabricante);
            modelo.put("exito", "El producto fue cargado correctamente");
        } catch (MyException ex) {
            
            modelo.put("error", ex.getMessage());
            
            return "producto_form.html";
        }
        
        
        return "index.html";
            
        
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Producto> productos = productoService.listarProductos();
        modelo.addAttribute("productos",productos);
        return "producto_list.html";
    }
    
    @GetMapping("/modificar/{codigo}")
    public String modificar(@PathVariable String codigo, ModelMap modelo){
        
        List<Fabricante> fabricantes = fabricanteService.listarFabricantes();
        modelo.addAttribute("fabricantes",fabricantes);
        
        modelo.put("producto", productoService.getOne(codigo));
        return "producto_modificar.html";
    }
    
     @PostMapping("/modificar/{codigo}")
    public String modificar(@PathVariable String codigo, @RequestParam String nombre, @RequestParam(required = false) Double precio, @RequestParam String codigofabricante, ModelMap modelo){
        try {
            
            productoService.modificarProducto(codigo, nombre, precio, codigofabricante);
            modelo.put("exito", "El producto fue modificado correctamente");
            
           
            
        } catch (MyException ex) {
            
            modelo.put("error", ex.getMessage());
            return "producto_modificar.html";
            
        }
        return "redirect:../lista";
    }
    
}
