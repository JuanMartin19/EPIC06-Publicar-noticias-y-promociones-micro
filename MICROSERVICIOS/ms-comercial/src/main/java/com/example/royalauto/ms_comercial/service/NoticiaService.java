package com.example.royalauto.ms_comercial.service;

import com.example.royalauto.ms_comercial.entity.Noticia;
import com.example.royalauto.ms_comercial.repository.NoticiaRepository;
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
public class NoticiaService {

    @Autowired
    private NoticiaRepository noticiaRepository;

    private final String UPLOAD_DIR = "uploads/";

    public List<Noticia> obtenerRuletaPrincipal() {
        return noticiaRepository.findByActivoTrue();
    }

    public Noticia subirImagenRuleta(MultipartFile archivo, String imagenUrlExterna) throws IOException {
        Noticia noticia = new Noticia();

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
            noticia.setImagenUrl("http://localhost:8082/api/imagenes/" + nombreArchivo);
            
        } else if (imagenUrlExterna != null && !imagenUrlExterna.trim().isEmpty()) {
            noticia.setImagenUrl(imagenUrlExterna);
        } else {
            throw new IllegalArgumentException("Debes proporcionar un archivo de imagen o una URL válida.");
        }

        noticia.setActivo(true);
        return noticiaRepository.save(noticia);
    }

    // Agregar este método al final
    public void desactivarNoticia(Long id) {
        Noticia noticia = noticiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada con ID: " + id));
        noticia.setActivo(false);
        noticiaRepository.save(noticia);
    }

    // Este es el NUEVO método para eliminar por completo
    public void eliminarNoticiaDefinitivamente(Long id) {
        Noticia noticia = noticiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada con ID: " + id));

        // 1. Validar si la imagen es local y borrar el archivo físico
        if (noticia.getImagenUrl() != null && noticia.getImagenUrl().contains("/api/imagenes/")) {
            try {
                // Extraemos solo el nombre del archivo al final de la URL
                String nombreArchivo = noticia.getImagenUrl().substring(noticia.getImagenUrl().lastIndexOf("/") + 1);
                Path rutaArchivo = Paths.get(UPLOAD_DIR + nombreArchivo);
                Files.deleteIfExists(rutaArchivo); // Destruye el archivo del disco duro
            } catch (IOException e) {
                System.err.println("Error al borrar el archivo físico: " + e.getMessage());
            }
        }

        // 2. Eliminar por completo de la base de datos
        noticiaRepository.delete(noticia);
    }
    
    // Método para traer TODAS las noticias (activas e inactivas)
    public List<Noticia> obtenerTodas() {
        return noticiaRepository.findAll();
    }

    // Método para hacer el "Switch" de estado
    public Noticia toggleActivo(Long id) {
        Noticia noticia = noticiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada con ID: " + id));
        
        noticia.setActivo(!noticia.isActivo()); // Si es true, lo hace false y viceversa
        return noticiaRepository.save(noticia);
    }
}