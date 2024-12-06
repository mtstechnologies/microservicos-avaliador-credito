package br.com.mts.msavaliadorcreditocartoes.application;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.mts.msavaliadorcreditocartoes.application.dto.CartaoDto;
import br.com.mts.msavaliadorcreditocartoes.domain.Cartao;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartaoController {

	private final CartaoService cartaoService;
	//private final ClienteCartaoService clienteCartaoService;
	
	@GetMapping
	public String status() {
		return "ok";
	}

	@PostMapping
	public ResponseEntity cadastrar(@RequestBody CartaoDto dto) {
		Cartao cartao = dto.toModel();
		cartaoService.save(cartao);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping(params = "renda")
	public ResponseEntity<List<Cartao>> obterCartoesRendaAte(@RequestParam("renda") Long renda){
		List<Cartao> list = cartaoService.getCartaoRendaMenorIgual(renda);
		return ResponseEntity.ok(list);
	}
}
