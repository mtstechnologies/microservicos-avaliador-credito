package br.com.mts.msavaliadorcredito.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.mts.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import br.com.mts.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import br.com.mts.msavaliadorcredito.application.ex.ErroSolicitacaoCartaoException;
import br.com.mts.msavaliadorcredito.domain.model.DadosAvaliacao;
import br.com.mts.msavaliadorcredito.domain.model.DadosSolicitacaoEmissaoCartao;
import br.com.mts.msavaliadorcredito.domain.model.ProtocoloSolicitacaoCartao;
import br.com.mts.msavaliadorcredito.domain.model.RetornoAvaliacaoCliente;
import br.com.mts.msavaliadorcredito.domain.model.SituacaoCliente;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("avaliador-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {
	
	private final AvaliadorCreditoService avaliadorCreditoService;
	
	@GetMapping
	public String status() {
		return "ok";
	}

	@GetMapping(value = "situacao-cliente", params = "cpf")
	public ResponseEntity consultarSituacaoCliente(@RequestParam("cpf") String cpf){
		 
		try {
			SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
			
			return ResponseEntity.ok(situacaoCliente);
		} catch (DadosClienteNotFoundException e) {
			
			return ResponseEntity.notFound().build();
		} catch (ErroComunicacaoMicroservicesException e) {
			
			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
		}	
	}
	
	@PostMapping
	public ResponseEntity realizarAvaliacaoCliente(@RequestBody DadosAvaliacao dados) {
		
		try {
			RetornoAvaliacaoCliente retornoAvaliacaoCliente = avaliadorCreditoService
					.realizarAvaliacaoCliente(dados.getCpf(), dados.getRenda());
			return ResponseEntity.ok(retornoAvaliacaoCliente);
		} catch (DadosClienteNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (ErroComunicacaoMicroservicesException e) {
			
			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
		}
	}
	
	@PostMapping("solicitacoes-cartao")
	public ResponseEntity solicitarCartaoCliente(@RequestBody DadosSolicitacaoEmissaoCartao dados) {
		try {
			ProtocoloSolicitacaoCartao protocoloSolicitacaoCartao = avaliadorCreditoService
					.solicitarEmissaoCartao(dados);
			return ResponseEntity.ok(protocoloSolicitacaoCartao);
		}catch(ErroSolicitacaoCartaoException e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
}
