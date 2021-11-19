package com.ias.reporteservicio.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

@Entity
@Table(name = "reportes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reporte {
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
}
