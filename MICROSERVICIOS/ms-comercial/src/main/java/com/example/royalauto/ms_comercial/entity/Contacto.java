package com.example.royalauto.ms_comercial.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contacto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contacto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 10, nullable = false)
    private String telefono;
    
    @Column(length = 100, nullable = false)
    private String email;
    
    @Column(length = 200, nullable = false)
    private String direccion;
    
    @OneToMany(mappedBy = "contacto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioBloque> bloquesHorario = new ArrayList<>();
}