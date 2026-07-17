package com.example.royalauto.ms_comercial.controller;

import com.example.royalauto.ms_comercial.entity.Noticia;
import com.example.royalauto.ms_comercial.service.NoticiaService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/noticias")
@RequiredArgsConstructor
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService;

    // --- NUEVO: Trae TODAS (Para el panel de React) ---
    @GetMapping("/todas")
    public ResponseEntity<List<Noticia>> getTodas() {
        return ResponseEntity.ok(noticiaService.obtenerTodas()); // Ahora sí llama al Service
    }

    // Trae SOLO LAS ACTIVAS (Para el Index público)
    @GetMapping("/ruleta")
    public ResponseEntity<List<Noticia>> getRuletaActivas() {
        return ResponseEntity.ok(noticiaService.obtenerRuletaPrincipal());
    }

    @PostMapping(value = "/subir", consumes = "multipart/form-data")
    public ResponseEntity<Noticia> crearBanner(
            @RequestParam(value = "archivo", required = false) MultipartFile archivo,
            @RequestParam(value = "imagenUrl", required = false) String imagenUrlExterna) {
        try {
            return ResponseEntity.ok(noticiaService.subirImagenRuleta(archivo, imagenUrlExterna));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // --- NUEVO: Endpoint para el Switch de Activar/Desactivar ---
    @PutMapping("/toggle-activo/{id}")
    public ResponseEntity<Noticia> toggleActivo(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(noticiaService.toggleActivo(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/desactivar/{id}")
    public ResponseEntity<Void> desactivarBanner(@PathVariable Long id) {
        try {
            noticiaService.desactivarNoticia(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarBannerDefinitivamente(@PathVariable Long id) {
        try {
            noticiaService.eliminarNoticiaDefinitivamente(id);
            return ResponseEntity.noContent().build(); 
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); 
        }
    }
}