package com.ias.reporteservicio.service.imp;

import com.ias.reporteservicio.dto.ReportesHorasDTO;
import com.ias.reporteservicio.entity.Reporte;
import com.ias.reporteservicio.exception.ReporteException;
import com.ias.reporteservicio.repository.RepositorioReporte;
import com.ias.reporteservicio.service.ServicioReporte;
import com.ias.reporteservicio.util.Constante;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicioReporteImpl implements ServicioReporte {
    private final RepositorioReporte repositorioReporte;
    HashMap<Integer,Integer> hasHorasNormales = new HashMap<Integer,Integer>();
    HashMap<Integer,Integer> hasHorasNocturnas = new HashMap<Integer,Integer>();
    @Override
    public Reporte hacerReporte(Reporte reporte) {
        try {
            log.info("Se esta creando el recurso");
            validarFechas(reporte);
            reporte.setNumeroSemana(reporte.getFechaInicio().get(Calendar.WEEK_OF_YEAR));
            Reporte reporteGuardar = repositorioReporte.save(reporte);
            return reporteGuardar;
        }catch(ReporteException e){
            log.error(e.getMessage());
            throw new ReporteException(Constante.FECHA_INICIO_MAYOR_A_FECHA_FIN);
        }
    }
    @Override
    public List<Reporte> listarReportes() {
        log.info("Listando los reportes");
        return repositorioReporte.findAll();
    }
    @Override
    public ReportesHorasDTO calcularHorasLaborales(String idTecnico, Integer numeroSemana) {
        try{
            List<Reporte> reportesEncontrados = encontrarReportes(idTecnico,numeroSemana);
            ReportesHorasDTO reportesHorasDTO = calculoHoras(reportesEncontrados);
            return ReportesHorasDTO.builder()
                    .horasDomicinalesExtras(reportesHorasDTO.getHorasDomicinalesExtras())
                    .horasNocturnasExtras(reportesHorasDTO.getHorasNocturnasExtras())
                    .horasNocturnas(reportesHorasDTO.getHorasNocturnas())
                    .horasNormales(reportesHorasDTO.getHorasNormales())
                    .horasTotales(reportesHorasDTO.getHorasTotales())
                    .horasNormalesExtras(reportesHorasDTO.getHorasNormalesExtras())
                    .horasDomicinales(reportesHorasDTO.getHorasDomicinales())
                    .build();

        }catch(ReporteException e){
            log.error("Error: ",e.getMessage());
            return ReportesHorasDTO.builder()
                    .horasDomicinalesExtras(0)
                    .horasNocturnasExtras(0)
                    .horasNocturnas(0)
                    .horasNormales(0)
                    .horasTotales(0)
                    .horasNormalesExtras(0)
                    .horasDomicinales(0)
                    .build();
        }
    }
    private ReportesHorasDTO calculoHoras(List<Reporte> reportesEncontrados) {
        double horasTotales = 0;
        double horasNormales = 0;
        double horasNocturnas = 0;
        double horasDomicinales = 0;
        double horasNormalesExtras = 0;
        double horasNocturnasExtras = 0;
        double horasDomicinalesExtras = 0;
        for (Reporte reporte : reportesEncontrados) {
            horasTotales += horasTotales(reporte);
            if (horasTotales <= 48){
                horasNormales += horasNormales(reporte);
                horasNocturnas += horasNocturnas(reporte);
                horasDomicinales += horasDomicinales(reporte);
            }
            if (horasTotales > 48){
                horasNormalesExtras += horasNormales(reporte);
                horasNocturnasExtras += horasNocturnas(reporte);
                horasDomicinalesExtras += horasDomicinales(reporte);
            }

        }
        if (horasTotales > 48){
            double residuoTotal = horasTotales - 48;

            if (residuoTotal < ( horasNormalesExtras + horasNocturnasExtras + horasDomicinales )){

                double residuoHoras = horasNormalesExtras + horasNocturnasExtras + horasDomicinalesExtras;
                residuoHoras = residuoHoras - residuoTotal;

                horasNormales = horasNormales + residuoHoras;
                horasNormalesExtras = horasNormalesExtras - residuoHoras;

            }
        }

        return ReportesHorasDTO.builder()
                .horasTotales(horasTotales)
                .horasNormales(horasNormales)
                .horasNocturnas(horasNocturnas)
                .horasNormalesExtras(horasNormalesExtras)
                .horasNocturnasExtras(horasNocturnasExtras)
                .horasDomicinales(horasDomicinales)
                .horasDomicinalesExtras(horasDomicinalesExtras)
                .build();
    }

    private ReportesHorasDTO calculoHorasTest(List<Reporte> reportesEncontrados) {
        double horasTotales = 0;
        double horasNormales = 0;
        double horasNocturnas = 0;
        double horasDomicinales = 0;
        double horasNormalesExtras = 0;
        double horasNocturnasExtras = 0;
        double horasDomicinalesExtras = 0;
        for (Reporte reporte : reportesEncontrados) {
            horasNormales += horasNormales(reporte);
            horasNocturnas += horasNocturnas(reporte);
            horasDomicinales += horasDomicinales(reporte);
            horasTotales += horasTotales(reporte);
            horasNormalesExtras += horasNormalesExtras(reporte);
            horasNocturnasExtras += horasNocturnasExtras(reporte);
            horasDomicinalesExtras += horasDomicinalesExtras(reporte);
            }
        return ReportesHorasDTO.builder()
                .horasTotales(horasTotales)
                .horasNormales(horasNormales)
                .horasNocturnas(horasNocturnas)
                .horasNormalesExtras(horasNormalesExtras)
                .horasNocturnasExtras(horasNocturnasExtras)
                .horasDomicinales(horasDomicinales)
                .horasDomicinalesExtras(horasDomicinalesExtras)
                .build();
    }

    private double horasDomicinalesExtras(Reporte reporte) {
        return 0;
    }

    private double horasNocturnasExtras(Reporte reporte) {
        return 0;
    }

    private double horasNormalesExtras(Reporte reporte) {
        return 0;
    }

    private double horasDomicinales(Reporte reporte) {
        Calendar fechaInicio = reporte.getFechaInicio();
        double horasDomicinales = 0;
        if (fechaInicio.get(Calendar.DAY_OF_WEEK) == 1){
            horasDomicinales = horasTotales(reporte);
        }
        return horasDomicinales;
    }
    private double horasNocturnas(Reporte reporte) {
        Calendar fechaInicio = reporte.getFechaInicio();
        Calendar fechaFin = reporte.getFechaFin();
        double horasNocturnas = 0;
        double horasTotales = horasTotales(reporte);
        hasHorasNocturnas.put(21,1);hasHorasNocturnas.put(22,2);hasHorasNocturnas.put(23,3);hasHorasNocturnas.put(0,4);hasHorasNocturnas.put(1,5);hasHorasNocturnas.put(2,6);hasHorasNocturnas.put(3,7);hasHorasNocturnas.put(4,8);hasHorasNocturnas.put(5,9);hasHorasNocturnas.put(6,10);hasHorasNocturnas.put(7,11);
        if ((fechaInicio.get(Calendar.DAY_OF_WEEK) == fechaFin.get(Calendar.DAY_OF_WEEK)) && fechaInicio.get(Calendar.DAY_OF_WEEK)!= 1) {

            if (fechaInicio.get(Calendar.HOUR_OF_DAY) >= 20 && fechaFin.get(Calendar.HOUR_OF_DAY) <= 7){
                horasNocturnas = 24 - horasTotales;
                horasNocturnas = 24 -horasNocturnas;
            }else
            if (fechaInicio.get(Calendar.HOUR_OF_DAY) <=7 && fechaFin.get(Calendar.HOUR_OF_DAY) >= 20){
                horasNocturnas = horasTotales - 13;
            }else if(fechaInicio.get(Calendar.HOUR_OF_DAY) > 7 && fechaFin.get(Calendar.HOUR_OF_DAY) > 20){
                horasNocturnas = horasTotales - horasNormales(reporte);
            }
                else if(fechaInicio.get(Calendar.HOUR_OF_DAY) <=7){
                horasNocturnas = horasTotales - horasNormales(reporte);
            }

        }if (fechaInicio.get(Calendar.DAY_OF_WEEK) != fechaFin.get(Calendar.DAY_OF_WEEK) && fechaInicio.get(Calendar.DAY_OF_WEEK)!= 1){
            if (fechaFin.get(Calendar.HOUR_OF_DAY) >=7 && fechaInicio.get(Calendar.HOUR_OF_DAY) <= 20){
                horasNocturnas = 11;
            }
        }
        return horasNocturnas;
    }

    private double horasNormales(Reporte reporte) {
       Calendar fechaInicio = reporte.getFechaInicio();
       Calendar fechaFin = reporte.getFechaFin();
       double horasTotales = horasTotales(reporte);
       double horasNormales = 0;
        hasHorasNormales.put(8,1);hasHorasNormales.put(9,2);hasHorasNormales.put(10,3);hasHorasNormales.put(11,4);hasHorasNormales.put(12,5);hasHorasNormales.put(13,6);hasHorasNormales.put(14,7);hasHorasNormales.put(15,8);hasHorasNormales.put(16,9);hasHorasNormales.put(17,10);hasHorasNormales.put(18,11);hasHorasNormales.put(19,12);hasHorasNormales.put(20,13);
        if ((fechaInicio.get(Calendar.DAY_OF_WEEK) == fechaFin.get(Calendar.DAY_OF_WEEK)) && fechaInicio.get(Calendar.DAY_OF_WEEK)!= 1){
           if (fechaInicio.get(Calendar.HOUR_OF_DAY) >= 7 && (fechaFin.get(Calendar.HOUR_OF_DAY) <= 20)){
               horasNormales = 24 - horasTotales;
               horasNormales = 24 - horasNormales;
           }else
           if (fechaFin.get(Calendar.HOUR_OF_DAY) > 7 && fechaFin.get(Calendar.HOUR_OF_DAY) <= 20){
               horasNormales = hasHorasNormales.get(fechaFin.get(Calendar.HOUR_OF_DAY));
           }else if(fechaInicio.get(Calendar.HOUR_OF_DAY) > 7 && fechaFin.get(Calendar.HOUR_OF_DAY) > 20){
                horasNormales = 13 - hasHorasNormales.get(fechaInicio.get(Calendar.HOUR_OF_DAY));
           }else
           if ((fechaInicio.get(Calendar.HOUR_OF_DAY) <=7 && fechaFin.get(Calendar.HOUR_OF_DAY) >= 20)){
               horasNormales = 13;
           }
       }
       if (fechaInicio.get(Calendar.DAY_OF_WEEK) != fechaFin.get(Calendar.DAY_OF_WEEK) && fechaInicio.get(Calendar.DAY_OF_WEEK)!= 1){
           if (fechaInicio.get(Calendar.HOUR_OF_DAY) > 7 && fechaInicio.get(Calendar.HOUR_OF_DAY) < 20){
               if (hasHorasNormales.containsKey(fechaInicio.get(Calendar.HOUR_OF_DAY))){
                   horasNormales += 13 - hasHorasNormales.get(fechaInicio.get(Calendar.HOUR_OF_DAY));
               }
           }
           if (fechaFin.get(Calendar.HOUR_OF_DAY) < 20){
               if (hasHorasNormales.containsKey(fechaInicio.get(Calendar.HOUR_OF_DAY))){
                   horasNormales +=hasHorasNormales.get(fechaInicio.get(Calendar.HOUR_OF_DAY));
               }
           }

       }
       return horasNormales;
    }

    private double horasTotales(Reporte reporte){
        return ( ( (reporte.getFechaFin().getTimeInMillis()) - (reporte.getFechaInicio().getTimeInMillis())) / ( 60 * 60 * 1000 ) );
    }
    private List<Reporte> encontrarReportes(String idTecnico, Integer numeroSemana) throws ReporteException{
        List<Reporte> reportesEncontrados = repositorioReporte.findAllByIdTecnicoAndNumeroSemana(idTecnico, numeroSemana);
        if (reportesEncontrados.isEmpty()){
            throw new ReporteException("La lista esta vacia");
        }
        return reportesEncontrados;
    }
    private void validarFechas(Reporte reporte) throws ReporteException {
        if (reporte.getFechaFin().getTimeInMillis() - reporte.getFechaInicio().getTimeInMillis()  <= 0){
            throw new ReporteException(Constante.FECHA_INICIO_MAYOR_A_FECHA_FIN);
        }
    }
}