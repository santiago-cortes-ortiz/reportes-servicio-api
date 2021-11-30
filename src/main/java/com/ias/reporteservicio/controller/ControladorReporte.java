package com.ias.reporteservicio.controller;

import com.ias.reporteservicio.dto.Respuesta;
import com.ias.reporteservicio.entity.Reporte;
import com.ias.reporteservicio.service.ServicioReporte;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ControladorReporte {

    private final ServicioReporte servicioReporte;

    @GetMapping("/listar")
    public ResponseEntity<Respuesta> listarReportes(){
        return ResponseEntity.ok(
                Respuesta.builder()
                        .marcaTiempo(LocalDateTime.now())
                        .codigoEstado(HttpStatus.OK.value())
                        .estadoHttp(HttpStatus.OK)
                        .dato(Map.of("reportes",servicioReporte.listarReportes()))
                        .mensaje("Se listaron los reportes")
                        .build()
        );
    }
    @GetMapping("/calcularHoras/{idTecnico}/{numeroSemana}")
    public ResponseEntity<Respuesta> calcularHorasDeTrabajo(@PathVariable("idTecnico") String idTecnico,@PathVariable("numeroSemana") Integer numeroSemana){
        return ResponseEntity.ok(
                Respuesta.builder()
                        .marcaTiempo(LocalDateTime.now())
                        .codigoEstado(HttpStatus.OK.value())
                        .estadoHttp(HttpStatus.OK)
                        .dato(Map.of("horas",servicioReporte.calcularHorasLaborales(idTecnico, numeroSemana)))
                        .mensaje("Se calculo las horas de trabajo")
                        .build()
        );
    }
    @PostMapping
    public ResponseEntity<Respuesta> hacerReporte(@RequestBody @Valid Reporte reporte){
        return ResponseEntity.ok(
                Respuesta.builder().marcaTiempo(LocalDateTime.now())
                .codigoEstado(HttpStatus.CREATED.value())
                .estadoHttp(HttpStatus.CREATED)
                .dato(Map.of("reporte",servicioReporte.hacerReporte(reporte)))
                .mensaje("Reporte creado")
                .build()

        );
    }
}
