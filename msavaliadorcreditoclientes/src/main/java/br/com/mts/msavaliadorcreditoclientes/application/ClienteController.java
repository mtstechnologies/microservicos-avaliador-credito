package br.com.mts.msavaliadorcreditoclientes.application;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.mts.msavaliadorcreditoclientes.application.dto.ClienteDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteController {
	private final ClienteService service;
	
	@GetMapping
	public String status() {
		log.info("Obtendo o status do microserviço de clientes");
		return "ok";
	}
	
	@PostMapping
	public ResponseEntity salvarCliente(@RequestBody ClienteDto dto) {
		var cliente = dto.toModel();
		service.salvar(cliente);
		URI headerLocation = ServletUriComponentsBuilder//este objeto constroi uls dinamicas
				.fromCurrentRequest()
				.query("cpf={cpf}")//usado quando preciso passar parametros via url
				.buildAndExpand(cliente.getCpf())
				.toUri();
		return ResponseEntity.created(headerLocation).build();
	}
	
	@GetMapping(params = "cpf")
	public ResponseEntity obterClientePorCpf(@RequestParam("cpf") String cpf) {
		var cliente = service.obterClientePorCpf(cpf);
		if(cliente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cliente);
	}

}
