package com.ias.reporteservicio.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "horas_trabajo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HorasTrabajo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "tipo")
    private String tipo;
}
