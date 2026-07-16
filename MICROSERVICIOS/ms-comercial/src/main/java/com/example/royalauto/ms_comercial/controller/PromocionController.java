package com.example.royalauto.ms_comercial.controller;

import com.example.royalauto.ms_comercial.entity.Promocion;
import com.example.royalauto.ms_comercial.service.PromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/promociones")
@CrossOrigin(origins = "http://localhost:5173") // CORRECCIÓN: Permite la entrada directa desde tu frontend en React
public class PromocionController {

    @Autowired
    private PromocionService promocionService;

    // --- NUEVO: Trae TODAS (Para el panel de React) ---
    @GetMapping("/todas")
    public ResponseEntity<List<Promocion>> getTodas() {
        return ResponseEntity.ok(promocionService.obtenerTodas());
    }

    // Trae SOLO LAS ACTIVAS (Para el Index público)
    @GetMapping("/tarjetas")
    public ResponseEntity<List<Promocion>> getTarjetas() {
        return ResponseEntity.ok(promocionService.obtenerTarjetasPromocion());
    }

    @PostMapping(value = "/vincular", consumes = "multipart/form-data")
    public ResponseEntity<Promocion> crearPromocion(
            @RequestParam(value = "archivo", required = false) MultipartFile archivo,
            @RequestParam(value = "imagenUrl", required = false) String imagenUrlExterna,
            @RequestParam("vehiculoId") Long vehiculoId) {
        try {
            return ResponseEntity
                    .ok(promocionService.vincularPromocionAVehiculo(archivo, imagenUrlExterna, vehiculoId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // --- NUEVO: Endpoint para el Switch de Activar/Desactivar ---
    @PutMapping("/toggle-activo/{id}")
    public ResponseEntity<Promocion> toggleActivo(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(promocionService.toggleActivo(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/desactivar/{id}")
    public ResponseEntity<Void> desactivarPromocion(@PathVariable Long id) {
        try {
            promocionService.desactivarPromocion(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para borrar físicamente (DELETE)
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarBannerDefinitivamente(@PathVariable Long id) {
        try {
            promocionService.eliminarPromocionDefinitivamente(id);
            return ResponseEntity.noContent().build(); 
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); 
        }
    }
}