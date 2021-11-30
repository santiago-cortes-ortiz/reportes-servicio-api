package com.ias.reporteservicio.repository;

import com.ias.reporteservicio.entity.ReporteHoraPK;
import com.ias.reporteservicio.entity.ReporteHoraTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositorioReporteHora extends JpaRepository<ReporteHoraTrabajo, ReporteHoraPK> {
    List<ReporteHoraTrabajo> findAllByIdReporte(Long idReporte);
}
