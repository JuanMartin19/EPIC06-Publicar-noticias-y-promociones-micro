package com.example.royalauto.ms_comercial.repository;

import com.example.royalauto.ms_comercial.entity.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Long> {
    List<Noticia> findByActivoTrue(); // Trae solo las imágenes que deben salir en la ruleta
}