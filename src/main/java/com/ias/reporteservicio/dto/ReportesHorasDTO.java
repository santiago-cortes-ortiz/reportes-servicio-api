package com.ias.reporteservicio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

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
