package com.ias.reporteservicio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "reportes")
@Getter
@Setter
@NoArgsConstructor
public class Reporte implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "El identificador del tecnico no debe ser nulo")
    @Column(nullable = false)
    private String idTecnico;
    @NotNull(message = "El identificador del servicio no debe ser nulo")
    @Column(nullable = false)
    private String idServicio;
    @NotNull(message = "La fecha de inicio no debe ser nula")
    @Column(nullable = false)
    private Calendar fechaInicio;
    @NotNull(message = "La fecha de fin no debe ser nula")
    @Column(nullable = false)
    private Calendar fechaFin;


    public Reporte(Long id, @NotNull(message = "El identificador del tecnico no debe ser nulo") String idTecnico, @NotNull(message = "El identificador del servicio no debe ser nulo") String idServicio, @NotNull(message = "La fecha de inicio no debe ser nula") Calendar fechaInicio, @NotNull(message = "La fecha de fin no debe ser nula") Calendar fechaFin) {
        this.id = id;
        this.idTecnico = idTecnico;
        this.idServicio = idServicio;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

}
