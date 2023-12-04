/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eggcarol.storetechno.controladores;

import com.eggcarol.storetechno.entidades.Fabricante;
import com.eggcarol.storetechno.exceptions.MyException;
import com.eggcarol.storetechno.servicios.FabricanteService;
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
@RequestMapping("/fabricante")
public class FabricanteControlador {
    
    @Autowired
    private FabricanteService fabricanteService;
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/registrar")
    public String registrar(){
        
        return "fabricante_form.html";
    }
        
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo){
        
        try {
            // esto se hace como prueba inicial : System.out.println("Nombre: " + nombre); y si funciona entonces se reemplaza por lo que sigue
            fabricanteService.crearFabricante(nombre);
            modelo.put("exito","El fabricante fue cargado correctamente !" );
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            return "fabricante_form.html";
        }
        
        return "index.html";
            
        
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Fabricante> fabricantes = fabricanteService.listarFabricantes();
        modelo.addAttribute("fabricantes",fabricantes);
        return "fabricante_list.html";
    }
    
    @GetMapping("/modificar/{codigofabricante}")
    public String modificar(@PathVariable String codigofabricante, ModelMap modelo){
        modelo.put("fabricante", fabricanteService.getOne(codigofabricante));
        return "fabricante_modificar.html";
    }
    
    @PostMapping("/modificar/{codigofabricante}")
    public String modificar(@PathVariable String codigofabricante, String nombre, ModelMap modelo){
        try {
            
            fabricanteService.modificarFabricante(codigofabricante, nombre);
            modelo.put("exito", "El fabricante fue modificado correctamente");
            
            
        } catch (MyException ex) {
            
            modelo.put("error", ex.getMessage());
            return "fabricante_modificar.html";
            
        }
        return "redirect:../lista";
    }
}
