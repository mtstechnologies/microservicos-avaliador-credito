package br.com.mts.msavaliadorcreditocartoes.application.dto;

import java.math.BigDecimal;

import br.com.mts.msavaliadorcreditocartoes.domain.BandeiraCartao;
import br.com.mts.msavaliadorcreditocartoes.domain.Cartao;
import lombok.Data;

@Data
public class CartaoDto {

	private String nome;
	private BandeiraCartao bandeira;
	private BigDecimal renda;
	private BigDecimal limite;
	
	//transformando em um objeto do tipo cartao
	public Cartao toModel() {
		return new Cartao(nome, bandeira, renda, limite);
	}
}
