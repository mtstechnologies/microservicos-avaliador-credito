package br.com.mts.msavaliadorcredito.application;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.mts.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import br.com.mts.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import br.com.mts.msavaliadorcredito.domain.model.CartaoCliente;
import br.com.mts.msavaliadorcredito.domain.model.DadosCliente;
import br.com.mts.msavaliadorcredito.domain.model.SituacaoCliente;
import br.com.mts.msavaliadorcredito.infra.clients.CartaoResourceFeingClient;
import br.com.mts.msavaliadorcredito.infra.clients.ClienteResourceFeingClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {
	
	private final ClienteResourceFeingClient clienteResourceFeingClient;
	private final CartaoResourceFeingClient cartaoResourceFeingClient;
	
	//comunicacao direta(sincrona) com outro microservico
	public SituacaoCliente obterSituacaoCliente(String cpf) 
			throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException{
		try {
			ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceFeingClient.obterClientePorCpf(cpf);			
			ResponseEntity<List<CartaoCliente>> cartaoResponse = cartaoResourceFeingClient.obterCartoesPorCliente(cpf);
			
			return SituacaoCliente
						.builder()
						.cliente(dadosClienteResponse.getBody())
						.cartoes(cartaoResponse.getBody())
						.build();
		}catch(FeignException.FeignClientException e) {
			int status = e.status();
			if(HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}
			throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
		}
	}
}
