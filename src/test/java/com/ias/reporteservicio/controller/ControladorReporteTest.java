package com.ias.reporteservicio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ias.reporteservicio.dto.ReportesHorasDTO;
import com.ias.reporteservicio.entity.Reporte;
import com.ias.reporteservicio.service.ServicioReporte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties
@AutoConfigureMockMvc
public class ControladorReporteTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    @Autowired
    private ServicioReporte service;
    @Autowired
    private ObjectMapper objectMapper;
    private String readJsonResponse(String url)throws IOException, URISyntaxException {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        return Files.lines(Paths.get(Objects.requireNonNull(loader.getResource(url)).toURI())).parallel()
                .collect(Collectors.joining());
    }
    @BeforeEach
    void setUp() {
        Mockito.when(service.calcularHorasLaborales("1111",41))
                .thenReturn(
                        ReportesHorasDTO.builder()
                                .horasTotales(56)
                                .horasNormales(28)
                                .horasNormalesExtras(3)
                                .horasNocturnas(13)
                                .horasNocturnasExtras(3)
                                .horasDomicinales(7)
                                .horasDomicinalesExtras(2)
                                .build()
                );
    }
    @Test
    void puedeHacerReporte()throws Exception {
        mockMvc.perform(post("/reportes/")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

    }

    /*private static Stream<Arguments> requestPersistence(){
        return Stream.of(Arguments.arguments("payload/request-reporte.json","payload/response-reporte.json"));
    }*/
    @Test
    @DisplayName("puede devolver una respuesta de horas de trabajo")
    void puedeCalcularHorasDeTrabajo() throws Exception {
        mockMvc.perform(get("/reportes/calcularHoras/1111/41")
                .contentType(MediaType.APPLICATION_JSON)
                        .param("idTecnico","1111")
                                .param("numeroSemana","41")
                )
                .andExpect(status().is(200))
                .andExpect(content().json(readJsonResponse("payload/reporte-horas.json")))
                ;
    }
}