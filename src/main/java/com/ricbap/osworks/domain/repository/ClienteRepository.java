package com.ricbap.osworks.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ricbap.osworks.domain.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	public List<Cliente> findByNome(String nome); // <--- REFERENCIA - nao usado
	public List<Cliente> findByNomeContaining(String nome);  // <--- REFERENCIA - nao usado

	public Cliente findByEmail(String email);
}
