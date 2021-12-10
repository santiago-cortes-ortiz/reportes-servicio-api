package com.ias.reporteservicio.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
public class ReportesHorasDTO {
    private double horasTotales;
    private double horasNormales;
    private double horasNocturnas;
    private double horasNormalesExtras;
    private double horasNocturnasExtras;
    private double horasDomicinales;
    private double horasDomicinalesExtras;
}
