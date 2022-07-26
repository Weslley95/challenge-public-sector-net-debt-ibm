package com.ibm.publicsectordebt.services;

import com.ibm.publicsectordebt.entities.BcData;
import com.ibm.publicsectordebt.repositories.BcDataRepository;
import com.ibm.publicsectordebt.services.exceptions.ResourceNotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Configuration
class BcDataServiceTest {

    @InjectMocks
    private BcDataService bcDataService;

    @Mock
    private BcDataRepository bcDataRepository;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


    @Test
    public void deveRetornarListaVazia() {
        try {
            bcDataService.findAll();
        } catch (ResourceNotFoundException e) {
            Assert.assertEquals("Empty Database", e.getMessage());
        }
    }

    @Test
    public void deveRetornarDadoPorID() throws ParseException {
        BcData b1 = new BcData();
        b1.setId(1L);
        b1.setValor(-0.34);
        b1.setData(sdf.parse("11/10/2018"));

        when(this.bcDataRepository.findById(b1.getId())).thenReturn(Optional.of(b1));

        BcData response = this.bcDataService.findById(1L);

        Assert.assertEquals(b1.getId(), response.getId());
        Assert.assertEquals(sdf.format(b1.getData()), sdf.format(response.getData()));
        Assert.assertEquals(b1.getValor(), response.getValor());
    }

    @Test
    public void deveRetornarTodosDadosEmLista() throws ParseException {
        List<BcData> list = new ArrayList<>();

        var b1 = new BcData();
        var b2 = new BcData();
        var b3 = new BcData();

        b1.setId(1L);
        b1.setData(sdf.parse("11/10/2018"));
        b1.setValor(-0.34);

        b2.setId(2L);
        b2.setData(sdf.parse("02/05/2015"));
        b2.setValor(-1.91);

        b3.setId(3L);
        b3.setData(sdf.parse("14/09/2004"));
        b3.setValor(-0.43);

        list.addAll(Arrays.asList(b1, b2, b3));

        when(this.bcDataRepository.findAll()).thenReturn(list);

        List<BcData> newList = this.bcDataService.findAll();

        Assert.assertEquals(3, newList.size());
        verify(this.bcDataRepository, times(1)).findAll();
    }

    @Test
    public void deveRetornarDataInicialEDataFinal() throws ParseException {
        List<BcData> list = new ArrayList<>();
        var b1 = new BcData();
        var b2 = new BcData();

        b1.setId(1L);
        b1.setData(sdf.parse("11/03/2003"));
        b1.setValor(0.65);

        b2.setId(2L);
        b2.setData(sdf.parse("11/03/2010"));
        b2.setValor(-1.11);

        Date startDate = b1.getData();
        Date endDate = b2.getData();

        list.addAll(Arrays.asList(b1, b2));

        when(this.bcDataRepository.findDateStartEnd(startDate, endDate)).thenReturn(list);

        List<BcData> search1 = this.bcDataService.searchDateStartEnd(startDate, endDate);
        Assert.assertEquals(2, search1.size());
    }

    @Test
    public void deveInserirDados() throws ParseException {
        List<BcData> list = new ArrayList<>();
        List<BcData> newlist = new ArrayList<>();
        var b1 = new BcData();
        var b2 = new BcData();

        b1.setId(1L);
        b1.setData(sdf.parse("11/03/2003"));
        b1.setValor(0.65);

        list.add(b1);

        when(bcDataRepository.saveAll(list)).thenReturn(list);
        when(bcDataRepository.findById(b1.getId())).thenReturn(Optional.of(new BcData()));

        List<BcData> b3 = this.bcDataService.insert(list);

        Double value = b3.stream()
                        .filter(x -> x.getValor().equals(b1.getValor()))
                        .findFirst()
                        .get()
                        .getValor();

        Assert.assertEquals(b1.getValor(), value);
    }

    @Test
    public void deveAtualizarDados() throws ParseException {
        var b1 = new BcData();
        var b2 = new BcData();

        b1.setId(1L);
        b1.setData(sdf.parse("01/01/2000"));
        b1.setValor(0.65);

        b2.setId(b1.getId());
        b2.setData(sdf.parse("11/03/2003"));
        b2.setValor(-1.11);

        when(bcDataRepository.findById(anyLong())).thenReturn(Optional.of(b1));
        when(bcDataRepository.save(any(BcData.class))).thenReturn(b2);

        BcData b3 = bcDataService.update(b2);

        Assert.assertEquals(b2.getValor(), b3.getValor());
        Assert.assertEquals(b2.getData(), b3.getData());
    }
}