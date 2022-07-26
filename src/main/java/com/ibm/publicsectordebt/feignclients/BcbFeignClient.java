package com.ibm.publicsectordebt.feignclients;

import com.ibm.publicsectordebt.entities.BcData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Interface para fazer a comunicao com outro projeto (spring clound)
 */
@Component
@FeignClient(name = "api", url = "${feign.client.api}")
public interface BcbFeignClient {

    /**
     * Retornar todos os dados da API externa
     * @return list obj
     */
    @GetMapping()
    List<BcData> findAll();
}
