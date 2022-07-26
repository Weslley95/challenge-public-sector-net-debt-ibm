package com.ibm.publicsectordebt.controllers;

import com.ibm.publicsectordebt.entities.BcData;
import com.ibm.publicsectordebt.services.RequestClientApiService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestClientApiController {

    @Autowired
    private RequestClientApiService requestClientApiService;

    /**
     * POST - Inserir dados da API externa no BD
     * @return obj
     */
    @PostMapping(value = "/initializer-api", produces="application/json")
    @ApiOperation(value = "Inserir dados da API externa na base de dados - Onboarding")
    public ResponseEntity<List<BcData>> insertDataApi() {
        List<BcData> obj = this.requestClientApiService.insertDataApi();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(obj).toUri();

        return ResponseEntity.created(uri).body(obj);
    }
}
