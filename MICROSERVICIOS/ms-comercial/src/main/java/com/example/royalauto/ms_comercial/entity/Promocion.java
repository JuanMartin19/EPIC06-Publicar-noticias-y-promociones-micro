package com.example.royalauto.ms_comercial.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "promociones")
public class Promocion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "imagen_url", nullable = false, length = 500)
    private String imagenUrl;
    
    // Guardamos solo el número, sin intentar mapear la tabla física
    @Column(name = "vehiculo_id", nullable = false)
    private Long vehiculoId;
    
    private boolean activo = true;
}