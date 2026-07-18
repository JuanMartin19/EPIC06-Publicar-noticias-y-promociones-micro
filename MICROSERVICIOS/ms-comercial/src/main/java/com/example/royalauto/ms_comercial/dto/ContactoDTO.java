package com.example.royalauto.ms_comercial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactoDTO {
    private Long id;
    private String telefono;
    private String email;
    private String direccion;
    private List<HorarioBloqueDTO> bloquesHorario;
}