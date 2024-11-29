package br.com.mts.msavaliadorcreditoclientes.application.dto;

import br.com.mts.msavaliadorcreditoclientes.domain.Cliente;
import lombok.Data;

@Data
public class ClienteDto {

	private String cpf;
	private String nome;
	private Integer idade;
	
	public Cliente toModel() {
		return  new Cliente(cpf, nome, idade);
	}
}
