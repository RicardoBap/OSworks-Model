package com.ricbap.osworks.api.model;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OrdemServicoInputModel {
	
	@NotBlank
	private String descricao;
	
	@NotNull
	private Double preco;
	
	@Valid
	@NotNull
	private ClienteIdInput cliente;
	
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public ClienteIdInput getCliente() {
		return cliente;
	}
	public void setCliente(ClienteIdInput cliente) {
		this.cliente = cliente;
	}

}
