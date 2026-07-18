package com.example.royalauto.ms_comercial.repository;

import com.example.royalauto.ms_comercial.entity.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {
}