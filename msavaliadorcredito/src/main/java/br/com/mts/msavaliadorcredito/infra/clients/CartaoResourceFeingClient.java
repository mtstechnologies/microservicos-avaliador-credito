package br.com.mts.msavaliadorcredito.infra.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.mts.msavaliadorcredito.domain.model.Cartao;
import br.com.mts.msavaliadorcredito.domain.model.CartaoCliente;

@FeignClient(value = "cartoes-ms", path = "/cartoes")
public interface CartaoResourceFeingClient {
	
	@GetMapping(params = "cpf")
	ResponseEntity<List<CartaoCliente>> obterCartoesPorCliente(@RequestParam("cpf") String cpf);

	
	@GetMapping(params = "renda")
	public ResponseEntity<List<Cartao>> obterCartoesRendaAte(@RequestParam("renda") Long renda);
}
