package br.com.mts.msavaliadorcreditoclientes.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mts.msavaliadorcreditoclientes.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	//query em tempo de runtime
		Optional<Cliente> findByCpf(String cpf);
}
