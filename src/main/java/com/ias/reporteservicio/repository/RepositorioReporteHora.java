package com.ias.reporteservicio.repository;

import com.ias.reporteservicio.entity.ReporteHoraPK;
import com.ias.reporteservicio.entity.ReporteHoraTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioReporteHora extends JpaRepository<ReporteHoraTrabajo, ReporteHoraPK> {
}
