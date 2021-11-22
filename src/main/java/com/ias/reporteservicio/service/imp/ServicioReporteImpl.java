package com.ias.reporteservicio.service.imp;

import com.ias.reporteservicio.entity.Reporte;
import com.ias.reporteservicio.exception.ReporteException;
import com.ias.reporteservicio.repository.RepositorioReporte;
import com.ias.reporteservicio.service.ServicioReporte;
import com.ias.reporteservicio.util.Constante;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicioReporteImpl implements ServicioReporte {

    private final RepositorioReporte repositorioReporte;

    @Override
    public Reporte hacerReporte(Reporte reporte) {
        try{
            log.info("Se esta creando el recurso");
            validarFechas(reporte);
            Reporte reporteGuardar = repositorioReporte.save(reporte);
            hacerReporteHoras(reporte);
            return reporteGuardar;
        }catch(ReporteException e){
            log.error(e.getMessage());
            throw new ReporteException(Constante.FECHA_INICIO_MAYOR_A_FECHA_FIN);
        }
    }

    private void hacerReporteHoras(Reporte reporte) {

    }

    @Override
    public List<Reporte> listarReportes() {
        log.info("Listando los reportes");
        return repositorioReporte.findAll();
    }

    @Override
    public Double calcularHorasDeTrabajo(String idTecnico, Integer numeroSemana) {
        List<Reporte> listaDeTecnicos = repositorioReporte.findAllByIdTecnico(idTecnico);
        Reporte reporteEncontrado;
        double horasDeTrabajo = 0;
        long dias = 0;

        for (Reporte r: listaDeTecnicos) {
            if (r.getFechaInicio().get(Calendar.WEEK_OF_YEAR) == numeroSemana){
                reporteEncontrado = r;
                long fechaInicio = reporteEncontrado.getFechaInicio().getTimeInMillis();
                long fechaFinal = reporteEncontrado.getFechaFin().getTimeInMillis();
                long value = fechaFinal - fechaInicio;
                long msDia = 1000*60*60*24;
                dias = value / msDia;
                horasDeTrabajo = dias * Constante.HORAS_NORMALES;
            }else{
                horasDeTrabajo = 0;
            }
        }
        return horasDeTrabajo;

    }

    private void validarFechas(Reporte reporte) throws ReporteException {
        if (reporte.getFechaFin().getTimeInMillis() - reporte.getFechaInicio().getTimeInMillis()  <= 0){
            throw new ReporteException(Constante.FECHA_INICIO_MAYOR_A_FECHA_FIN);
        }
    }
}
