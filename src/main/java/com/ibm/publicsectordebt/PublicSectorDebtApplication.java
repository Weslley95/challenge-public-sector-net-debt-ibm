package com.ibm.publicsectordebt;

import org.bouncycastle.util.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Anotacao para habiliar o Feign no projeto -> @EnableFeignClients
 */
@EnableFeignClients
@SpringBootApplication
public class PublicSectorDebtApplication {

	public static void main(String[] args) {
		SpringApplication.run(PublicSectorDebtApplication.class, args);
	}
}
