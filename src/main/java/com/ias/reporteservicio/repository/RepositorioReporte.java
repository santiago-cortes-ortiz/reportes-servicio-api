package com.ias.reporteservicio.repository;

import com.ias.reporteservicio.entity.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositorioReporte extends JpaRepository<Reporte,Long> {
    List<Reporte> findAllByIdTecnico(String idTecnico);
}
