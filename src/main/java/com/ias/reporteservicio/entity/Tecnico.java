package com.ias.reporteservicio.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tecnicos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tecnico {
    @Id
    private String cedula;
    @Column(nullable = false)
    private String nombre;
}
