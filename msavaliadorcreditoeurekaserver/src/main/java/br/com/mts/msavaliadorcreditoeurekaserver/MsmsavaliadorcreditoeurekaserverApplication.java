package br.com.mts.msavaliadorcreditoeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MsmsavaliadorcreditoeurekaserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsmsavaliadorcreditoeurekaserverApplication.class, args);
	}

}
