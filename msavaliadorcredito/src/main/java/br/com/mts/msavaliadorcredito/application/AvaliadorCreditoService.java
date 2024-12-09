package br.com.mts.msavaliadorcredito.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.mts.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import br.com.mts.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import br.com.mts.msavaliadorcredito.domain.model.Cartao;
import br.com.mts.msavaliadorcredito.domain.model.CartaoAprovado;
import br.com.mts.msavaliadorcredito.domain.model.CartaoCliente;
import br.com.mts.msavaliadorcredito.domain.model.DadosCliente;
import br.com.mts.msavaliadorcredito.domain.model.RetornoAvaliacaoCliente;
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
	
	public RetornoAvaliacaoCliente realizarAvaliacaoCliente(String cpf, Long renda) 
			throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
		try {
		ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceFeingClient.obterClientePorCpf(cpf);
		ResponseEntity<List<Cartao>> cartoesResponse = cartaoResourceFeingClient.obterCartoesRendaAte(renda);
	 
		List<Cartao> cartoes = cartoesResponse.getBody();
		var listaCartoesAprovados = cartoes.stream().map(cartao -> {
		 
			DadosCliente dadosCliente = dadosClienteResponse.getBody();
		 
			BigDecimal limiteBasico = cartao.getLimiteBasico();
			BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
				 
			var fator = idadeBD.divide(BigDecimal.valueOf(10));
			BigDecimal limiteAprovado = fator.multiply(limiteBasico);
			
			CartaoAprovado aprovado = new CartaoAprovado();
			aprovado.setCartao(cartao.getNome());
			aprovado.setBandeira(cartao.getBandeira());
			aprovado.setLimiteAprovado(limiteAprovado);
			
			return aprovado;
		}).collect(Collectors.toList());
		
		return new RetornoAvaliacaoCliente(listaCartoesAprovados);
	 
		}catch (FeignException.FeignClientException e) {
			int status = e.status();
			if(HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}
			throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
		}
	}
}
