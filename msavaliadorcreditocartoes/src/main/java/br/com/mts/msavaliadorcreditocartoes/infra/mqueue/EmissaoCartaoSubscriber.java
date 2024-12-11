package br.com.mts.msavaliadorcreditocartoes.infra.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mts.msavaliadorcreditocartoes.domain.Cartao;
import br.com.mts.msavaliadorcreditocartoes.domain.ClienteCartao;
import br.com.mts.msavaliadorcreditocartoes.domain.DadosSolicitacaoEmissaoCartao;
import br.com.mts.msavaliadorcreditocartoes.infra.repository.CartaoRepository;
import br.com.mts.msavaliadorcreditocartoes.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmissaoCartaoSubscriber {

	private final CartaoRepository cartaoRepository;
	private final ClienteCartaoRepository clienteCartaoRepository;
	
	@RabbitListener(queues = "${mq.queues.emissao-cartoes}")
	public void receberSolicitacaoEmissao(@Payload String payload) {
		try {
			var mapper = new ObjectMapper();
			DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
	        Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();
	        ClienteCartao clienteCartao = new ClienteCartao();
	        clienteCartao.setCartao(cartao);
	        clienteCartao.setCpf(dados.getCpf());
	        clienteCartao.setLimite(dados.getLimiteLiberado());
	        
	        clienteCartaoRepository.save(clienteCartao);
	        
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
