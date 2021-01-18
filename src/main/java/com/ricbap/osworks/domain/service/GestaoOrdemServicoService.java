package com.ricbap.osworks.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricbap.osworks.api.model.Comentario;
import com.ricbap.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.ricbap.osworks.domain.exception.NegocioException;
import com.ricbap.osworks.domain.model.Cliente;
import com.ricbap.osworks.domain.model.OrdemServico;
import com.ricbap.osworks.domain.model.StatusOrdemServico;
import com.ricbap.osworks.domain.repository.ClienteRepository;
import com.ricbap.osworks.domain.repository.ComentarioRepository;
import com.ricbap.osworks.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {	
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepositopry;
	
	@Autowired
	private ComentarioRepository comentarioReposisitory;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		Cliente cliente = clienteRepositopry.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
		
		ordemServico.setCliente(cliente);		
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemServicoRepository.save(ordemServico);
	}
	
	
	public Comentario adicionarComentario(Long id, String descricao) {
		/*OrdemServico ordemServico = ordemServicoRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada")); */		
		OrdemServico ordemServico = buscar(id);
				
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		
		return comentarioReposisitory.save(comentario);
	}
	
	public void finalizar(Long id) {
		OrdemServico ordemServico = buscar(id);		
				
		ordemServico.finalizar();
		
		ordemServicoRepository.save(ordemServico);
	}


	private OrdemServico buscar(Long id) {
		OrdemServico ordemServico = ordemServicoRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
		return ordemServico;
	}
	
	

}
