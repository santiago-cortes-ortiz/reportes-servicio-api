package com.ias.reporteservicio.repository;

import com.ias.reporteservicio.entity.HorasTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioHorasTrabajo extends JpaRepository<HorasTrabajo,Long> {
    HorasTrabajo findByTipo(String tipo);
}
