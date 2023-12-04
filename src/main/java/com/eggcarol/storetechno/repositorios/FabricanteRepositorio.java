/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.eggcarol.storetechno.repositorios;

import com.eggcarol.storetechno.entidades.Fabricante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Carolina
 */
@Repository
public interface FabricanteRepositorio extends JpaRepository<Fabricante, String> {
    
    @Query("SELECT f FROM Fabricante f WHERE f.nombre = :nombre")
    public Fabricante buscarPorNombre(@Param("nombre") String nombre);
}
