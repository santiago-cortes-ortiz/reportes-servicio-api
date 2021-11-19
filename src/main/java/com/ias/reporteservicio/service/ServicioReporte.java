package com.ias.reporteservicio.service;

import com.ias.reporteservicio.entity.Reporte;

import java.util.List;

public interface ServicioReporte {
    Reporte hacerReporte(Reporte reporte);
    List<Reporte> listarReportes();
    Double calcularHorasDeTrabajo(String idTecnicoo,Integer numeroSemana);
}
