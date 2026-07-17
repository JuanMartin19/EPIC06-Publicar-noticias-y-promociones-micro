package com.example.royalauto.ms_comercial.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "noticias")
public class Noticia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "imagen_url", nullable = false, length = 500)
    private String imagenUrl;
    
    private boolean activo = true;
}