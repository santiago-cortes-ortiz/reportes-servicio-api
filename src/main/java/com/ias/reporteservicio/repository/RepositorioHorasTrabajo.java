package com.ias.reporteservicio.repository;

import com.ias.reporteservicio.entity.HorasTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositorioHorasTrabajo extends JpaRepository<HorasTrabajo,Long> {
    Optional<HorasTrabajo> findByTipo(String tipo);
}
