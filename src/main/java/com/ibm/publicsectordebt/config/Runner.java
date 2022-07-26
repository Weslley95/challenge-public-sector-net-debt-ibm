package com.ibm.publicsectordebt.config;

import com.ibm.publicsectordebt.services.RequestClientApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Classe para iniciar processo de onboarding
 */
@Component
public class Runner implements ApplicationRunner {

    @Autowired
    private RequestClientApiService requestClientApiService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.requestClientApiService.insertDataApi();
    }
}
