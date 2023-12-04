/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eggcarol.storetechno.servicios;

import com.eggcarol.storetechno.entidades.Fabricante;
import com.eggcarol.storetechno.exceptions.MyException;
import com.eggcarol.storetechno.repositorios.FabricanteRepositorio;
import java.util.ArrayList;
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
public class FabricanteService {
    
    @Autowired
    FabricanteRepositorio fabricanteRepositorio;
    
    @Transactional
    public void crearFabricante(String nombre)throws MyException{
        
        validarFabricante(nombre);
        
        Fabricante fabricante = new Fabricante();
        
        fabricante.setNombre(nombre);
        
        fabricanteRepositorio.save(fabricante);
        
    }
    
    public List<Fabricante> listarFabricantes(){
        
        List<Fabricante> fabricantes = new ArrayList();
        fabricantes = fabricanteRepositorio.findAll();
        
        return fabricantes;
    }
    
    public void modificarFabricante(String codigofabricante, String nombre) throws MyException{
        
        validarFabricante(nombre);
        
        Optional<Fabricante> respuesta = fabricanteRepositorio.findById(codigofabricante);
        
        if(respuesta.isPresent()){
            
            Fabricante fabricante = respuesta.get();
            
            fabricante.setNombre(nombre);
            fabricanteRepositorio.save(fabricante);//persistimos mediante el repositorio en la BD
        }
        
    }
    public Fabricante getOne(String codigofabricante){
        return fabricanteRepositorio.getOne(codigofabricante);
    }
    
    public void validarFabricante(String nombre)throws MyException{
        
        if(nombre.isEmpty()|| nombre==null){
            throw new MyException("El nombre no puede ser nulo o no puede estar vacío.");
        }
    }
}
