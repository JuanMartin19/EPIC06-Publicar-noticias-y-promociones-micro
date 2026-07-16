package com.example.royalauto.ms_comercial.service;

import com.example.royalauto.ms_comercial.entity.Noticia;
import com.example.royalauto.ms_comercial.entity.Promocion;
import com.example.royalauto.ms_comercial.repository.PromocionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class PromocionService {

    @Autowired
    private PromocionRepository promocionRepository;
    
    private final String UPLOAD_DIR = "uploads/";

    public List<Promocion> obtenerTarjetasPromocion() {
        return promocionRepository.findByActivoTrue();
    }

    public Promocion vincularPromocionAVehiculo(MultipartFile archivo, String imagenUrlExterna, Long vehiculoId) throws IOException {
        Promocion promocion = new Promocion();

        if (archivo != null && !archivo.isEmpty()) {
            // NUEVO: Validar que el archivo sea estrictamente una imagen
            String contentType = archivo.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new IllegalArgumentException("El archivo debe ser una imagen válida (JPG, PNG, GIF, etc.).");
            }

            String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();
            Path rutaCompleta = Paths.get(UPLOAD_DIR + nombreArchivo);
            Files.createDirectories(rutaCompleta.getParent());
            Files.write(rutaCompleta, archivo.getBytes());
            promocion.setImagenUrl("http://localhost:8082/api/imagenes/" + nombreArchivo);
            
        } else if (imagenUrlExterna != null && !imagenUrlExterna.trim().isEmpty()) {
            promocion.setImagenUrl(imagenUrlExterna);
        } else {
            throw new IllegalArgumentException("Debes proporcionar un archivo de imagen o una URL válida.");
        }

        promocion.setVehiculoId(vehiculoId);
        promocion.setActivo(true);
        return promocionRepository.save(promocion);
    }

    // Agregar este método al final
    public void desactivarPromocion(Long id) {
        Promocion promocion = promocionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promoción no encontrada con ID: " + id));
        promocion.setActivo(false);
        promocionRepository.save(promocion);
    }

    // Este es el NUEVO método para eliminar por completo
    public void eliminarPromocionDefinitivamente(Long id) {
        Promocion promocion = promocionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promoción no encontrada con ID: " + id));

        // 1. Validar si la imagen es local y borrar el archivo físico
        if (promocion.getImagenUrl() != null && promocion.getImagenUrl().contains("/api/imagenes/")) {
            try {
                // Extraemos solo el nombre del archivo al final de la URL
                String nombreArchivo = promocion.getImagenUrl().substring(promocion.getImagenUrl().lastIndexOf("/") + 1);
                Path rutaArchivo = Paths.get(UPLOAD_DIR + nombreArchivo);
                Files.deleteIfExists(rutaArchivo); // Destruye el archivo del disco duro
            } catch (IOException e) {
                System.err.println("Error al borrar el archivo físico: " + e.getMessage());
            }
        }

        // 2. Eliminar por completo de la base de datos
        promocionRepository.delete(promocion);
    }

    // Método para traer TODAS las promociones (activas e inactivas)
    public List<Promocion> obtenerTodas() {
        return promocionRepository.findAll();
    }

    // Método para hacer el "Switch" de estado
    public Promocion toggleActivo(Long id) {
        Promocion promocion = promocionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promoción no encontrada con ID: " + id));
        
        promocion.setActivo(!promocion.isActivo()); // Si es true, lo hace false y viceversa
        return promocionRepository.save(promocion);
    }
}