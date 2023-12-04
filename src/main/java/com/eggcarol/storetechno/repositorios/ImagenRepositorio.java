/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eggcarol.storetechno.repositorios;

import com.eggcarol.storetechno.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Carolina
 */
@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen,String> {
    
}
