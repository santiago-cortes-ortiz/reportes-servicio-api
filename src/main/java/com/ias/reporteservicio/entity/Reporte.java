package com.ias.reporteservicio.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Reporte implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull(message = "El identificador del tecnico no debe ser nulo")
    @Column(nullable = false,name = "id_tecnico")
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
    @Column(nullable = false)
    private Integer numeroSemana;

    @ManyToOne
    @JoinColumn(referencedColumnName = "cedula",name = "id_tecnico",insertable = false,updatable = false)
    @JsonIgnore
    private Tecnico tecnico;


}
