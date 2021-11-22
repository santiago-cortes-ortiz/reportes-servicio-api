package com.ias.reporteservicio.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reportes_horas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReporteHoraTrabajo implements Serializable {

    @EmbeddedId
    private ReporteHoraPK reporteHoraPK;

    @JoinColumn(name = "id_reporte",referencedColumnName = "id",insertable = false,updatable = false)
    @ManyToOne
    private Reporte reporte;

    @JoinColumn(name = "id_hora_trabajo",referencedColumnName = "id",insertable = false,updatable = false)
    @ManyToOne
    private HorasTrabajo horasTrabajo;


}
