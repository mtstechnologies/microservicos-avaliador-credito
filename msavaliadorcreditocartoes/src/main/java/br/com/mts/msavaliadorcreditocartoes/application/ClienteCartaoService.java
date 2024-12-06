package br.com.mts.msavaliadorcreditocartoes.application;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.mts.msavaliadorcreditocartoes.domain.ClienteCartao;
import br.com.mts.msavaliadorcreditocartoes.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {
	
	private final ClienteCartaoRepository repository;

	public List<ClienteCartao> listaCartoesPorCpf(String cpf){
		return repository.findByCpf(cpf);
	}
}
