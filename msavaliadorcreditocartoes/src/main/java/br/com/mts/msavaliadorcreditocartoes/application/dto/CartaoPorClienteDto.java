package br.com.mts.msavaliadorcreditocartoes.application.dto;

import java.math.BigDecimal;

import br.com.mts.msavaliadorcreditocartoes.domain.ClienteCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartaoPorClienteDto {

	private String nome;
	private String bandeira;
	private BigDecimal limiteLiberado;
	
	public static CartaoPorClienteDto modeloDto(ClienteCartao clienteCartao) {
		return new CartaoPorClienteDto(
				clienteCartao.getCartao().getNome(),
				clienteCartao.getCartao().getBandeira().toString(),
				clienteCartao.getLimite()
		);
	}
}
