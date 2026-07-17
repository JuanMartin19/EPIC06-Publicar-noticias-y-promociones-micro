package com.example.royalauto.ms_comercial.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class VehiculoDTO {
    // --- CAMPOS ORIGINALES (Del Pull) ---
    private Long id;
    private String modelo;
    private Integer anio;
    private BigDecimal precio;
    private String descripcion;
    private Boolean disponible;
    private Long marcaId;
    private String marcaNombre;
    private Long categoriaId;
    private String categoriaNombre;
    private List<String> imagenes;

    // --- NUESTROS CAMPOS (Para la vista previa de Promociones) ---
    private BigDecimal precioOriginal;
    private String imagenUrl;
    private String marcaVehiculo;     
    private String categoriaVehiculo; 
    private Long promocionId;
    private String tipoDescuento; 
    private Double valorDescuento; 
    private BigDecimal precioFinal; 
}