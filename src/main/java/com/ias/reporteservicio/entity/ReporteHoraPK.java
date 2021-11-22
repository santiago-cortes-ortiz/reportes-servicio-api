package com.ias.reporteservicio.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@Getter
@Setter
public class ReporteHoraPK implements Serializable {
    @Column(name = "id_reporte")
    private Long idReporte;

    @Column(name = "id_hora_trabajo")
    private Long idHoraTrabajo;
}
