package com.ias.reporteservicio.reporte;

import com.ias.reporteservicio.entity.Reporte;
import com.ias.reporteservicio.exception.ReporteException;
import com.ias.reporteservicio.repository.RepositorioReporte;
import com.ias.reporteservicio.service.imp.ServicioReporteImpl;
import com.ias.reporteservicio.util.Constante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ServicioReporteTest {

    @Mock
    private RepositorioReporte repositorioReporte;
    private ServicioReporteImpl servicioReporte;

    @BeforeEach
    void setUp() {
        servicioReporte = new ServicioReporteImpl(repositorioReporte);
    }

    @Test
    void puedeListarReportes() {
        //when
        servicioReporte.listarReportes();
        //then
        verify(repositorioReporte).findAll();
    }

    @Test
    void puedeHacerReporte() {
        //given
        Calendar fechaIncio = Calendar.getInstance();
        fechaIncio.set(2021,07,21);
        Calendar fechaFin = Calendar.getInstance();
        fechaFin.set(2021,07,28);
        Reporte reporte = new Reporte();
        reporte.setIdTecnico("1003813503");
        reporte.setIdServicio("aaa111");
        reporte.setFechaInicio(fechaIncio);
        reporte.setFechaFin(fechaFin);
        reporte.setNumeroSemana(fechaIncio.get(Calendar.WEEK_OF_YEAR));
        //when
        servicioReporte.hacerReporte(reporte);
        //then
        ArgumentCaptor<Reporte> reporteArgumentCaptor = ArgumentCaptor.forClass(Reporte.class);
        verify(repositorioReporte).save(reporteArgumentCaptor.capture());
        Reporte capturedReporte = reporteArgumentCaptor.getValue();
        assertThat(capturedReporte).isEqualTo(reporte);
    }
    @Test
    void arrojaExceptionFechas() {
        //given
        Calendar fechaIncio = Calendar.getInstance();
        fechaIncio.set(2021,07,21);
        Calendar fechaFin = Calendar.getInstance();
        fechaFin.set(2021,07,10);
        Reporte reporte = new Reporte();
        reporte.setIdTecnico("1003813503");
        reporte.setIdServicio("aaa111");
        reporte.setFechaInicio(fechaIncio);
        reporte.setFechaFin(fechaFin);
        reporte.setNumeroSemana(fechaIncio.get(Calendar.WEEK_OF_YEAR));
        //when
        // then
        assertThatThrownBy( () -> servicioReporte.hacerReporte(reporte))
                .isInstanceOf(ReporteException.class)
                .hasMessageContaining(Constante.FECHA_INICIO_MAYOR_A_FECHA_FIN);

    }

}