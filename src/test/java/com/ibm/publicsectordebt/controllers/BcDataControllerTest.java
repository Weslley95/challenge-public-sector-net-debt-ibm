package com.ibm.publicsectordebt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.publicsectordebt.entities.BcData;
import com.ibm.publicsectordebt.repositories.BcDataRepository;
import com.ibm.publicsectordebt.services.BcDataService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class BcDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BcDataService bcDataService;

    @MockBean
    private BcDataRepository bcDataRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private List<BcData> bcDataList;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Before
    public void setup() throws ParseException {
        this.bcDataList = new ArrayList<>();
        var b1 = new BcData();
        var b2 = new BcData();
        var b3 = new BcData();

        b1.setId(1L);
        b1.setData(sdf.parse("11/03/2003"));
        b1.setValor(0.65);

        b2.setId(2L);
        b3.setData(sdf.parse("11/03/2003"));
        b2.setValor(-1.11);

        b3.setId(3L);
        b3.setData(sdf.parse("30/09/2021"));
        b3.setValor(0.25);

        this.bcDataList.addAll(Arrays.asList(b1, b2, b3));
        this.bcDataService.insert(this.bcDataList);
        this.bcDataRepository.saveAll(this.bcDataList);
    }

    @Test
    public void deveRetornarTodosOsDados() throws Exception {
        given(this.bcDataService.findAll()).willReturn(bcDataList);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/data/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void deveRetornarDadosPorId() throws Exception {
        given(this.bcDataService.findAll()).willReturn(bcDataList);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/data/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void deveSalvarDados() throws Exception {
        MvcResult response = mockMvc
                .perform(MockMvcRequestBuilders.post("/data")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bcDataList)))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        Assert.assertEquals(response.getResponse().getStatus(), HttpStatus.CREATED.value());
    }

    @Test
    public void deveAtualizarDados() throws Exception {
        var b1 = new BcData();
        b1.setValor(0.20);
        b1.setData(sdf.parse("01/01/2000"));

        MvcResult response = mockMvc
                .perform(MockMvcRequestBuilders.put("/data/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(b1)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertEquals(response.getResponse().getStatus(), HttpStatus.OK.value());
    }

    @Test
    public void deveApagarDadoEspecifico() throws Exception {
        MvcResult response = mockMvc
                .perform(MockMvcRequestBuilders.delete("/data/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();

        Assert.assertEquals(response.getResponse().getStatus(), HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deveApagarTodosOsDados() throws Exception {
        MvcResult response = mockMvc
                .perform(MockMvcRequestBuilders.delete("/data/delete-all"))
                .andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();

        Assert.assertEquals(response.getResponse().getStatus(), HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deveBuscarInterloDeData() throws Exception {
        MvcResult response = mockMvc
                .perform(MockMvcRequestBuilders.get("/data/search-date-interval")
                        .param("startDate","01-01-2000")
                        .param("endDate", "01-01-2020"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertEquals(response.getResponse().getStatus(), HttpStatus.OK.value());
    }

    @Test
    public void deveBuscarDataPorDiaMesAno() throws Exception {
        MvcResult response = mockMvc
                .perform(MockMvcRequestBuilders.get("/data/search-date")
                        .param("year", "2020"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertEquals(response.getResponse().getStatus(), HttpStatus.OK.value());
    }

    @Test
    public void buscarSomaAcumuladaPorAno() throws Exception {
        MvcResult response = mockMvc
                .perform(MockMvcRequestBuilders.get("/data/debt/{year}", 2020))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertEquals(response.getResponse().getStatus(), HttpStatus.OK.value());
    }

    @Test
    public void buscarResumoDeDados() throws Exception {
        MvcResult response = mockMvc
                .perform(MockMvcRequestBuilders.get("/data/summary"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertEquals(response.getResponse().getStatus(), HttpStatus.OK.value());
    }
}
