package com.example.royalauto.ms_comercial.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "horario_bloque")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioBloque {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiaSemana diaInicio;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiaSemana diaFin;
    
    @Column(nullable = false)
    private LocalTime horaInicio;
    
    @Column(nullable = false)
    private LocalTime horaFin;
    
    @ManyToOne
    @JoinColumn(name = "contacto_id", nullable = false)
    private Contacto contacto;
}