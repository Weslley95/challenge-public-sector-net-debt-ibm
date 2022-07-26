package com.ibm.publicsectordebt.services;

import com.ibm.publicsectordebt.entities.BcData;
import com.ibm.publicsectordebt.feignclients.BcbFeignClient;
import com.ibm.publicsectordebt.repositories.BcDataRepository;
import com.ibm.publicsectordebt.services.exceptions.RequestClientApiException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RequestClientApiService {

    // Injetar instancias

    @Autowired
    private BcbFeignClient bcbFeignClient;

    @Autowired
    private BcDataRepository bcDataRepository;

    /**
     * Inserir dados da API externa no BD
     * @return list
     */
    @Transactional
    public List<BcData> insertDataApi() {
        try {
            if (this.bcDataRepository.findAll().isEmpty()) {
                return bcDataRepository.saveAll(this.bcbFeignClient.findAll());
            } else {
                throw new RequestClientApiException("Data has already been entered");
            }
        } catch (FeignException e) {
            throw new RequestClientApiException(e.getMessage());
        }
    }
}
