package br.com.mts.msavaliadorcredito.infra.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mts.msavaliadorcredito.domain.model.DadosSolicitacaoEmissaoCartao;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SolicitacaoEmissaoCartaoPublisher {

	private final RabbitTemplate rabbitTemplate;
	private final Queue queueEmissaoCartoes;
	
	public void solicitarCartao(DadosSolicitacaoEmissaoCartao dados) throws JsonProcessingException {
		//recebendo o objeto convertido em formato JSON para poder enviar as mensagens pela rede
		var json = convertIntoJson(dados);
		//caso rabbitmq caia tambem apresentara uma exception
		rabbitTemplate.convertAndSend(queueEmissaoCartoes.getName(), json);
	}
	
	private String convertIntoJson(DadosSolicitacaoEmissaoCartao dados) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		
		var json = mapper.writeValueAsString(dados);
		return json;
	}
}
