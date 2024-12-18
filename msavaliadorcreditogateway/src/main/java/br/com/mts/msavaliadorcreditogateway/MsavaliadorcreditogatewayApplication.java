package br.com.mts.msavaliadorcreditogateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class MsavaliadorcreditogatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsavaliadorcreditogatewayApplication.class, args);
	}
	
	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder
				.routes()
					.route(r -> r.path("/clientes/**").uri("lb://clientes-ms"))
					.route(r -> r.path("/cartoes/**").uri("lb://cartoes-ms"))
					.route(r -> r.path("/avaliador-credito/**").uri("lb://avaliador-credito-ms"))
				.build();
	}

}
