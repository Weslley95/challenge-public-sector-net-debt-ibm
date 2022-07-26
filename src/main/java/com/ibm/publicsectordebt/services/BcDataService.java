package com.ibm.publicsectordebt.services;

import com.ibm.publicsectordebt.entities.BcData;
import com.ibm.publicsectordebt.feignclients.BcbFeignClient;
import com.ibm.publicsectordebt.repositories.BcDataRepository;
import com.ibm.publicsectordebt.services.exceptions.ArgumentException;
import com.ibm.publicsectordebt.services.exceptions.ResourceNotFoundException;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static com.ibm.publicsectordebt.services.util.Validation.*;

@Service
public class BcDataService {

    // Injetar instancias
    @Autowired
    private BcbFeignClient bcbFeignClient;

    @Autowired
    private BcDataRepository bcDataRepository;

    /**
     * Retornar todos os dados
     * @return page
     */
    public List<BcData> findAll() {
        List<BcData> list = this.bcDataRepository.findAll();

        if (!list.isEmpty()) {
            return list;
        } else {
            throw new ResourceNotFoundException("Empty Database");
        }
    }

    /**
     * Retornar todos os dados
     * @return page
     */
    public Page<BcData> findAllPage(Pageable pageable) {
        Page<BcData> bcDataPage = this.bcDataRepository.findAll(pageable);

        if (!bcDataPage.isEmpty()) {
            return bcDataPage;
        } else {
         throw new ResourceNotFoundException("Empty Database");
        }
    }

    /**
     * Retornar dados por id
     * @param id id do obj
     * @return obj
     */
    @Transactional
    public BcData findById(Long id) {
        return this.bcDataRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
    }

    /**
     * Retornar dados filtrados por data inicial e final
     * @param startDate data minima
     * @param endDate data maxima
     * @return list date
     */
    public List<BcData> searchDateStartEnd(Date startDate, Date endDate) throws ResourceNotFoundException {
        if(startDate.before(endDate)) {
            return this.bcDataRepository.findDateStartEnd(startDate, endDate);
        } else {
            throw new ResourceNotFoundException("Date or parameter invalid");
        }
    }

    /**
     * Retornar dados filtrados por dia, mes ou ano
     * @param field campo a ser pesquisado
     * @param value valor da data
     * @return result
     */
    public List<BcData> searchDateField(String field, Integer value) {
        try {
            if (validadFieldValueDate(field, value)) {
                switch (field) {
                    case "day":
                        return this.bcDataRepository.findDay(value);
                    case "month":
                        return this.bcDataRepository.findMonth(value);
                    case "year": default:
                        return this.bcDataRepository.findYear(value);
                }
            } else {
                throw new ResourceNotFoundException("Parameter invalid");
            }
        } catch (RuntimeException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    /**
     * Inserir dados
     * @param obj obj BcData
     * @return obj
     */
    @Transactional
    public List<BcData> insert(List<BcData> obj) {
        if (!verifyNull(obj)) {
            return this.bcDataRepository.saveAll(obj);
        } else {
            throw new ArgumentException("Argument key incorrect");
        }
    }

    /**
     * Atualizar dados
     * @param obj novo obj
     * @return obj
     */
    @Transactional
    public BcData update(BcData obj) {
        try {
            if (obj.getValor() != null && obj.getData() != null) {
                var newBcData = this.findById(obj.getId());
                newBcData.setValor(obj.getValor());
                newBcData.setData(obj.getData());
                return this.bcDataRepository.save(obj);
            } else {
                throw new ArgumentException("Argument key incorrect");
            }
        } catch (RuntimeException e) {
            throw new ResourceNotFoundException("Empty Database");
        }
    }

    /**
     * Deletar dado especifico
     * @param id id obj
     */
    public void delete(Long id) {
        try {
            this.bcDataRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("ID " + id + " Not Exist");
        }
    }

    /**
     * Deletar todos os dados
     */
    public void deleteAll() {
        this.bcDataRepository.deleteAll();
    }

    /**
     * Dívida líquida do setor público por ano
     * @param year ano
     * @return String
     */
    public JSONObject debtCalc(Integer year) {
        List<Double> list = this.bcDataRepository.findValueYear(year);
        if (!list.isEmpty()) {
            return debtValid(list, year);
        } else {
            throw new ResourceNotFoundException("Year " + year + " not exist or empty Database");
        }
    }

    /**
     * Resumo dos dados
     * @return JSONObject
     */
    public JSONObject summary() {
        try {
            return summaryValid(this.bcDataRepository.findAll());
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException("Empty Database");
        }
    }
}
