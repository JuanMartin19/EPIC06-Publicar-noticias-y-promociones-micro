package com.example.royalauto.ms_comercial.repository;

import com.example.royalauto.ms_comercial.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    // E1HU2: Filtrar vehículos por marca
    List<Vehiculo> findByMarca_NombreIgnoreCase(String nombreMarca);

    // Filtros extra útiles para el catálogo (E1HU2 / E1HU3)
    List<Vehiculo> findByCategoria_NombreIgnoreCase(String nombreCategoria);

    List<Vehiculo> findByDisponibleTrue();

    List<Vehiculo> findByAnio(Integer anio);

    List<Vehiculo> findByPrecioBetween(BigDecimal precioMin, BigDecimal precioMax);
}