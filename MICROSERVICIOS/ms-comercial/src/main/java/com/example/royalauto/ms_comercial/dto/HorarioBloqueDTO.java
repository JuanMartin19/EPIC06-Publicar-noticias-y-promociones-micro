package com.example.royalauto.ms_comercial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioBloqueDTO {
    private Long id;
    private String diaInicio;
    private String diaFin;
    private String horaInicio;
    private String horaFin;
}
