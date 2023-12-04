/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eggcarol.storetechno.repositorios;

import com.eggcarol.storetechno.entidades.Producto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Carolina
 */
@Repository
public interface ProductoRepositorio extends JpaRepository<Producto,String> {
    
    @Query("SELECT p FROM Producto p WHERE p.nombre = :nombre")
    public Producto buscarPorNombre(@Param("nombre") String nombre);
    
    @Query("SELECT p FROM Producto p WHERE p.fabricante.nombre = :nombre")
    public List<Producto> buscarPorFabricante(@Param("nombre") String nombre);

}
