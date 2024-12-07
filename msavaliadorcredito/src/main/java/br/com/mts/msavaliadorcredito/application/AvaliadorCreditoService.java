package br.com.mts.msavaliadorcredito.application;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.mts.msavaliadorcredito.domain.model.DadosCliente;
import br.com.mts.msavaliadorcredito.domain.model.SituacaoCliente;
import br.com.mts.msavaliadorcredito.infra.clients.ClienteResourceFeingClient;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {
	
	private final ClienteResourceFeingClient clienteResourceFeingClient;
	
	//comunicacao direta(sincrona) com outro microservico
	public SituacaoCliente obterSituacaoCliente(String cpf) {
		
		ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceFeingClient.obterClientePorCpf(cpf);			
			
		return SituacaoCliente
					.builder()
					.cliente(dadosClienteResponse.getBody())
					.build();
	}
}
