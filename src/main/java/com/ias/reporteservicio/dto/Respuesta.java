package com.ias.reporteservicio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
@Data
public class Respuesta {
    protected LocalDateTime marcaTiempo;
    protected int codigoEstado;
    protected HttpStatus estadoHttp;
    protected String razon;
    protected String mensaje;
    protected String mensajeDesarollador;
    protected Map<?,?> dato;
}
