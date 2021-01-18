package com.ricbap.osworks.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ricbap.osworks.api.model.Comentario;
import com.ricbap.osworks.api.model.ComentarioInput;
import com.ricbap.osworks.api.model.ComentarioModel;
import com.ricbap.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.ricbap.osworks.domain.model.OrdemServico;
import com.ricbap.osworks.domain.repository.OrdemServicoRepository;
import com.ricbap.osworks.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico/{id}/comentarios")
public class ComentarioController {
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServicoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@GetMapping
	public List<ComentarioModel> listar(@PathVariable Long id) {
		OrdemServico ordemServico = ordemServicoRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem d serviço não encontrado"));
		
		return toCollectionModel(ordemServico.getComentarios());
	}
	
	private List<ComentarioModel> toCollectionModel(List<Comentario> comentarios) {		
		return comentarios.stream()
				.map(comentario -> toModel(comentario))
				.collect(Collectors.toList());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioModel adicionar(@Valid @PathVariable Long id, @RequestBody ComentarioInput comentarioInput) {
		Comentario comentario = gestaoOrdemServicoService.adicionarComentario(id, comentarioInput.getDescricao());
		
		return toModel(comentario);
	}

	private ComentarioModel toModel(Comentario comentario) {
		return modelMapper.map(comentario, ComentarioModel.class);
	}
	
	

}
