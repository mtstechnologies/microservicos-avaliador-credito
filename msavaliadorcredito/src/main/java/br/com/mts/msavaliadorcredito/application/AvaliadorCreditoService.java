package br.com.mts.msavaliadorcredito.application;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.mts.msavaliadorcredito.domain.model.CartaoCliente;
import br.com.mts.msavaliadorcredito.domain.model.DadosCliente;
import br.com.mts.msavaliadorcredito.domain.model.SituacaoCliente;
import br.com.mts.msavaliadorcredito.infra.clients.CartaoResourceFeingClient;
import br.com.mts.msavaliadorcredito.infra.clients.ClienteResourceFeingClient;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {
	
	private final ClienteResourceFeingClient clienteResourceFeingClient;
	private final CartaoResourceFeingClient cartaoResourceFeingClient;
	
	//comunicacao direta(sincrona) com outro microservico
	public SituacaoCliente obterSituacaoCliente(String cpf) {
		
		ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceFeingClient.obterClientePorCpf(cpf);			
		ResponseEntity<List<CartaoCliente>> cartaoResponse = cartaoResourceFeingClient.obterCartoesPorCliente(cpf);
		return SituacaoCliente
					.builder()
					.cliente(dadosClienteResponse.getBody())
					.cartoes(cartaoResponse.getBody())
					.build();
	}
}
