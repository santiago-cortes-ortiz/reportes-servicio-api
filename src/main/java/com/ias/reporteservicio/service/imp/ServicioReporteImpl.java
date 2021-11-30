package com.ias.reporteservicio.service.imp;

import com.ias.reporteservicio.dto.ReportesHorasDTO;
import com.ias.reporteservicio.entity.HorasTrabajo;
import com.ias.reporteservicio.entity.Reporte;
import com.ias.reporteservicio.entity.ReporteHoraPK;
import com.ias.reporteservicio.entity.ReporteHoraTrabajo;
import com.ias.reporteservicio.exception.ReporteException;
import com.ias.reporteservicio.repository.RepositorioHorasTrabajo;
import com.ias.reporteservicio.repository.RepositorioReporte;
import com.ias.reporteservicio.repository.RepositorioReporteHora;
import com.ias.reporteservicio.service.ServicioReporte;
import com.ias.reporteservicio.util.Constante;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicioReporteImpl implements ServicioReporte {

    private final RepositorioReporte repositorioReporte;
    private final RepositorioHorasTrabajo repositorioHorasTrabajo;
    private final RepositorioReporteHora repositorioReporteHora;

    @Override
    public Reporte hacerReporte(Reporte reporte) {
        try {
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
        Integer horaInicio = reporte.getFechaInicio().get(Calendar.HOUR_OF_DAY);
        Integer horaFin = reporte.getFechaFin().get(Calendar.HOUR_OF_DAY);
        Integer diaInicio = reporte.getFechaInicio().get(Calendar.DAY_OF_WEEK);
        Integer diaFin = reporte.getFechaFin().get(Calendar.DAY_OF_WEEK);
        List<ReporteHoraTrabajo> listaAllenar = new ArrayList<>();

        if(((horaInicio >= 8 && horaInicio <= 18 ||  horaFin >= 8 && horaFin<= 18)) && (diaInicio !=1) == true){
            HorasTrabajo horasNormales = repositorioHorasTrabajo.findByTipo("HORAS_NORMALES").get();
            ReporteHoraTrabajo reporteHoraNormales= new ReporteHoraTrabajo(new ReporteHoraPK(reporte.getId(),horasNormales.getId()));
            listaAllenar.add(reporteHoraNormales);
        }
        if ((horaInicio < 8 && horaInicio >= 7 || horaFin <=20 && horaFin >18) && (diaInicio !=1) ){
            HorasTrabajo horasNormalesExtra= repositorioHorasTrabajo.findByTipo("HORAS_NORMALES_EXTRA").get();
            ReporteHoraTrabajo reporteExtra = new ReporteHoraTrabajo(new ReporteHoraPK(reporte.getId(),horasNormalesExtra.getId()));
            listaAllenar.add(reporteExtra);
            HorasTrabajo horasNormales = repositorioHorasTrabajo.findByTipo("HORAS_NORMALES").get();
            ReporteHoraTrabajo reporteHoraNormales= new ReporteHoraTrabajo(new ReporteHoraPK(reporte.getId(),horasNormales.getId()));
            listaAllenar.add(reporteHoraNormales);
        }
        if((horaInicio >= 22 || horaInicio <= 6 && horaFin <= 6 || horaFin >= 22)==true){
            HorasTrabajo horasTrabajo2 = repositorioHorasTrabajo.findByTipo("HORAS_NOCTURNAS").get();
            ReporteHoraTrabajo reporteHoraTrabajo2 = new ReporteHoraTrabajo(new ReporteHoraPK(reporte.getId(),horasTrabajo2.getId()));
            listaAllenar.add(reporteHoraTrabajo2);
        }
        if (horaInicio < 22 && horaInicio >= 20 || horaFin <=7 && horaFin >6 ){
            HorasTrabajo horasNormalesExtra= repositorioHorasTrabajo.findByTipo("HORAS_NOCTURNAS_EXTRAS").get();
            ReporteHoraTrabajo reporteExtra = new ReporteHoraTrabajo(new ReporteHoraPK(reporte.getId(),horasNormalesExtra.getId()));
            listaAllenar.add(reporteExtra);
            HorasTrabajo horasNormales = repositorioHorasTrabajo.findByTipo("HORAS_NOCTURNAS").get();
            ReporteHoraTrabajo reporteHoraNormales= new ReporteHoraTrabajo(new ReporteHoraPK(reporte.getId(),horasNormales.getId()));
            listaAllenar.add(reporteHoraNormales);
        }
        if ((diaInicio == 1 || diaFin == 1) && (horaInicio >= 8 && horaInicio <= 18 ||  horaFin >= 8 && horaFin<= 18) ){
            HorasTrabajo horasTrabajo3 = repositorioHorasTrabajo.findByTipo("HORAS_DOMICINALES").get();
            ReporteHoraTrabajo reporteHoraTrabajo3 = new ReporteHoraTrabajo(new ReporteHoraPK(reporte.getId(),horasTrabajo3.getId()));
            listaAllenar.add(reporteHoraTrabajo3);
        }

        if ((diaInicio == 1 || diaFin == 1)  ){
            if(horaInicio < 8 && horaInicio >= 7 || horaFin <=20 && horaFin >18){
                HorasTrabajo horasDomicinalesExtra = repositorioHorasTrabajo.findById(Long.parseLong("6")).get();
                ReporteHoraTrabajo reporteHoraDomicinalesExtras = new ReporteHoraTrabajo(new ReporteHoraPK(reporte.getId(),horasDomicinalesExtra.getId()));
                listaAllenar.add(reporteHoraDomicinalesExtras);
                HorasTrabajo horasNormales = repositorioHorasTrabajo.findByTipo("HORAS_DOMICINALES").get();
                ReporteHoraTrabajo reporteHoraNormales= new ReporteHoraTrabajo(new ReporteHoraPK(reporte.getId(),horasNormales.getId()));
                listaAllenar.add(reporteHoraNormales);
            }
        }
        repositorioReporteHora.saveAll(listaAllenar);
    }

    @Override
    public List<Reporte> listarReportes() {
        log.info("Listando los reportes");
        return repositorioReporte.findAll();
    }

    @Override
    public ReportesHorasDTO calcularHorasLaborales(String idTecnico, Integer numeroSemana) {
       double horasTotales = 0;
       double horasNormales = 0;
       double horasNormalesExtras = 0;
       double horasNocturnas = 0;
       double horasNocturnasExtras = 0;
       double horasDomicinales = 0;
       double horasDomicinalesExtras = 0;
       int dias = 0;
       List<Reporte> listaDeTecnicos = repositorioReporte.findAllByIdTecnico(idTecnico);
       Reporte reporteEncontrado = reporteEncontrado(listaDeTecnicos,numeroSemana);
       if (reporteEncontrado == null){
           throw new RuntimeException();
       }
       dias = diasTrabajados(reporteEncontrado);
       List<ReporteHoraTrabajo> reporteHoraTrabajos = repositorioReporteHora.findAllByIdReporte(reporteEncontrado.getId());
        for (ReporteHoraTrabajo rh:reporteHoraTrabajos
             ) {
            if (rh.getReporteHoraPK().getIdHoraTrabajo() == 1 && rh.getReporteHoraPK().getIdHoraTrabajo() != 3 ){
                horasNormales = dias * Constante.HORAS_NORMALES;
                horasTotales += horasNormales;

            }
            if (rh.getReporteHoraPK().getIdHoraTrabajo() == 4){
                horasNormalesExtras = dias * calcularHoraExtraNormales(reporteEncontrado);
                horasTotales += horasNormalesExtras;
            }
            if (rh.getReporteHoraPK().getIdHoraTrabajo() == 2){
                horasNocturnas = dias * Constante.HORAS_NOCTURNAS;
                horasTotales += horasNocturnas;

            }
            if (rh.getReporteHoraPK().getIdHoraTrabajo() == 5){
                horasNocturnasExtras = dias * calcularHoraExtraNocturnas(reporteEncontrado);
                horasTotales += horasNocturnasExtras;

            }
            if (rh.getReporteHoraPK().getIdHoraTrabajo() == 3){
                horasDomicinales = Constante.HORAS_DOMICINALES;
                horasTotales += horasDomicinales;
            }
            if (rh.getReporteHoraPK().getIdHoraTrabajo() == 6){
                horasDomicinalesExtras = calcularHorasExtrasDomicinales(reporteEncontrado);
                horasTotales += horasDomicinales;
            }
        }

       return  ReportesHorasDTO.builder()
               .horasTotales(horasTotales)
               .horasNormales(horasNormales)
               .horasNormalesExtras(horasNormalesExtras)
               .horasNocturnas(horasNocturnas)
               .horasNocturnasExtras(horasNocturnasExtras)
               .horasDomicinales(horasDomicinales)
               .horasDomicinalesExtras(horasDomicinalesExtras)
               .build();
    }

    private double calcularHorasExtrasDomicinales(Reporte reporteEncontrado) {
        double horasExtras = 0;
        int horaInicio = reporteEncontrado.getFechaInicio().get(Calendar.HOUR_OF_DAY);
        int horasFin = reporteEncontrado.getFechaFin().get(Calendar.HOUR_OF_DAY);
        if (horaInicio < Constante.HORA_NORMAL_MANANA){
            horasExtras += Math.abs( horaInicio - Constante.HORA_NORMAL_MANANA );
        }
        if (horasFin > Constante.HORA_NORMAL_TARDE){
            horasExtras += Math.abs( horasFin - Constante.HORA_NORMAL_TARDE );
        }
        return horasExtras;
    }

    private double calcularHoraExtraNocturnas(Reporte reporteEncontrado) {
        double horasExtras = 0;
        int horaInicio = reporteEncontrado.getFechaInicio().get(Calendar.HOUR_OF_DAY);
        int horasFin = reporteEncontrado.getFechaFin().get(Calendar.HOUR_OF_DAY);

        if (horaInicio < Constante.HORA_INICIO_NOCTURNO){
            horasExtras += Math.abs( horaInicio - Constante.HORA_INICIO_NOCTURNO );
        }
        if (horasFin > Constante.HORA_FINAL_NOCTURNO){
            horasExtras += Math.abs( horasFin - Constante.HORA_FINAL_NOCTURNO );
        }
        return horasExtras;
    }

    private double calcularHoraExtraNormales(Reporte reporteEncontrado) {
        double horasExtras = 0;
        int horaInicio = reporteEncontrado.getFechaInicio().get(Calendar.HOUR_OF_DAY);
        int horasFin = reporteEncontrado.getFechaFin().get(Calendar.HOUR_OF_DAY);
        if (horaInicio < Constante.HORA_NORMAL_MANANA){
            horasExtras += Math.abs( horaInicio - Constante.HORA_NORMAL_MANANA );
        }
        if (horasFin > Constante.HORA_NORMAL_TARDE){
            horasExtras += Math.abs( horasFin - Constante.HORA_NORMAL_TARDE );
        }
        return horasExtras;
    }

    private Integer diasTrabajados(Reporte reporte) {
        Date d1 = reporte.getFechaInicio().getTime();
        Date d2 = reporte.getFechaFin().getTime();
        double dias = ( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
        if (dias % 2 != 0){
            dias +=1;
        }
        System.out.println(dias);
        return (int) dias;

    }

    private Reporte reporteEncontrado(List<Reporte> listaDeTecnicos,Integer numeroSemana) {
        Reporte reporteEncontrado = null;
        for (Reporte r:listaDeTecnicos) {
            if (r.getFechaInicio().get(Calendar.WEEK_OF_YEAR) == numeroSemana || r.getFechaFin().get(Calendar.WEEK_OF_YEAR) == numeroSemana){
                reporteEncontrado = r;
            }
        }
        return reporteEncontrado;
    }

    private void validarFechas(Reporte reporte) throws ReporteException {
        if (reporte.getFechaFin().getTimeInMillis() - reporte.getFechaInicio().getTimeInMillis()  <= 0){
            throw new ReporteException(Constante.FECHA_INICIO_MAYOR_A_FECHA_FIN);
        }
    }
}