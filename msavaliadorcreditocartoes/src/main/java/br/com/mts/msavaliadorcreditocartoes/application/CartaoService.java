package br.com.mts.msavaliadorcreditocartoes.application;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.mts.msavaliadorcreditocartoes.domain.Cartao;
import br.com.mts.msavaliadorcreditocartoes.infra.repository.CartaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartaoService {

private final CartaoRepository repository;
	
	@Transactional
	public Cartao save(Cartao cartao) {
		return repository.save(cartao);
	}
	
	public List<Cartao> getCartaoRendaMenorIgual(Long renda){
		var rendaBigDecimal = BigDecimal.valueOf(renda);
		return repository.findByRendaLessThanEqual(rendaBigDecimal);
	}
}
