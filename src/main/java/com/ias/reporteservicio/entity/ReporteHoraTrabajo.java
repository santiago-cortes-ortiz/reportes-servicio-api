package com.ias.reporteservicio.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reportes_horas")
@Getter
@Setter
public class ReporteHoraTrabajo implements Serializable {

    @EmbeddedId
    private ReporteHoraPK reporteHoraPK;

    @Column(name = "id_reporte",updatable = false,insertable = false)
    private Long idReporte;

    @JoinColumn(name = "id_reporte", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private Reporte reporte;



    @JoinColumn(name = "id_hora_trabajo",referencedColumnName = "id",insertable = false,updatable = false)
    @ManyToOne
    private HorasTrabajo horasTrabajo;


    public ReporteHoraTrabajo(ReporteHoraPK reporteHoraPK) {
        this.reporteHoraPK = reporteHoraPK;

    }

    public ReporteHoraTrabajo() {
    }
}
