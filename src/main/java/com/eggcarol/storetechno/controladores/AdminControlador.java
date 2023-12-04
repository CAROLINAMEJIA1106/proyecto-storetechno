/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eggcarol.storetechno.controladores;

import com.eggcarol.storetechno.entidades.Usuario;
import com.eggcarol.storetechno.exceptions.MyException;
import com.eggcarol.storetechno.servicios.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Carolina
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/dashboard")
    public String panelAdministrativo(){
        return "panel.html";
    }
    
    @GetMapping("/usuarios")    
    public String listar(ModelMap modelo){
        
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        
        return "usuario_list.html";
    }
    
    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable String id){
        usuarioService.cambiarRol(id);
        return "redirect:/admin/usuarios";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        modelo.addAttribute("usuarios",usuarios);
        
        modelo.put("usuario", usuarioService.getOne(id));
        return "usuario_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(MultipartFile archivo, @PathVariable String id, @RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam String password2, ModelMap modelo){
        try {
            
            usuarioService.actualizar(archivo, id, nombre, email, password, password2);
            modelo.put("exito", "El usuario fue modificado correctamente!!");
            
           
            
        } catch (MyException ex) {
            
            modelo.put("error", ex.getMessage());
            return "usuario_modificar.html";
            
        }
        return "redirect:../usuarios";
    }
}
