package com.example.royalauto.ms_comercial.service;


import com.example.royalauto.ms_comercial.dto.ContactoDTO;
import com.example.royalauto.ms_comercial.dto.HorarioBloqueDTO;
import com.example.royalauto.ms_comercial.entity.Contacto;
import com.example.royalauto.ms_comercial.entity.DiaSemana;
import com.example.royalauto.ms_comercial.entity.HorarioBloque;
import com.example.royalauto.ms_comercial.repository.ContactoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactoService {
    
    private final ContactoRepository contactoRepository;
    
    @Transactional(readOnly = true)
    public ContactoDTO getContacto() {
        return contactoRepository.findAll().stream()
            .findFirst()
            .map(this::toDTO)
            .orElse(null);
    }
    
    @Transactional
    public ContactoDTO updateContacto(ContactoDTO dto) {
        Contacto contacto = contactoRepository.findAll().stream()
            .findFirst()
            .orElse(new Contacto());
        
        contacto.setTelefono(dto.getTelefono());
        contacto.setEmail(dto.getEmail());
        contacto.setDireccion(dto.getDireccion());
        
        // Limpiar bloques existentes
        contacto.getBloquesHorario().clear();
        
        // Agregar nuevos bloques
        if (dto.getBloquesHorario() != null) {
            dto.getBloquesHorario().forEach(bloqueDTO -> {
                HorarioBloque bloque = new HorarioBloque();
                bloque.setDiaInicio(DiaSemana.valueOf(bloqueDTO.getDiaInicio()));
                bloque.setDiaFin(DiaSemana.valueOf(bloqueDTO.getDiaFin()));
                bloque.setHoraInicio(LocalTime.parse(bloqueDTO.getHoraInicio()));
                bloque.setHoraFin(LocalTime.parse(bloqueDTO.getHoraFin()));
                bloque.setContacto(contacto);
                contacto.getBloquesHorario().add(bloque);
            });
        }
        
        Contacto saved = contactoRepository.save(contacto);
        return toDTO(saved);
    }
    
    private ContactoDTO toDTO(Contacto contacto) {
        return new ContactoDTO(
            contacto.getId(),
            contacto.getTelefono(),
            contacto.getEmail(),
            contacto.getDireccion(),
            contacto.getBloquesHorario().stream()
                .map(this::toBloqueDTO)
                .collect(Collectors.toList())
        );
    }
    
    private HorarioBloqueDTO toBloqueDTO(HorarioBloque bloque) {
        return new HorarioBloqueDTO(
            bloque.getId(),
            bloque.getDiaInicio().name(),
            bloque.getDiaFin().name(),
            bloque.getHoraInicio().toString(),
            bloque.getHoraFin().toString()
        );
    }
}