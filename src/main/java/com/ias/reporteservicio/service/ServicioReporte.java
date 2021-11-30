package com.ias.reporteservicio.service;

import com.ias.reporteservicio.dto.ReportesHorasDTO;
import com.ias.reporteservicio.entity.Reporte;

import java.util.List;

public interface ServicioReporte {
    Reporte hacerReporte(Reporte reporte);
    List<Reporte> listarReportes();
    ReportesHorasDTO calcularHorasLaborales(String idTecnico,Integer numeroSemana);
}
