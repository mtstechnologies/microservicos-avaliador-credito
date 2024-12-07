package br.com.mts.msavaliadorcredito.infra.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.mts.msavaliadorcredito.domain.model.DadosCliente;
/**
 * interface responsavel pela comunicacao com outros microservicoes
 * atraves do meu gateway, que estara redirecionando para a devida URL
 * e sera consumida pela minha classe: AvaliadorCreditoService.
 */

@FeignClient(value = "clientes-ms", path = "/clientes")
public interface ClienteResourceFeingClient {

	/**
	 * obtendo os dados do cliente-ms  atraves do endpoint que foi 
	 * disponibilizado no controlador
	 */
	@GetMapping(params = "cpf")
	ResponseEntity<DadosCliente> obterClientePorCpf(@RequestParam("cpf") String cpf);	
}
