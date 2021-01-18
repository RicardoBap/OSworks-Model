package com.ricbap.osworks.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ricbap.osworks.api.model.OrdemServicoInputModel;
import com.ricbap.osworks.api.model.OrdemServicoModel;
import com.ricbap.osworks.domain.model.OrdemServico;
import com.ricbap.osworks.domain.repository.OrdemServicoRepository;
import com.ricbap.osworks.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoModel criar(@Valid @RequestBody OrdemServicoInputModel ordemServicoInputModel) {
		OrdemServico ordemServico = toEntity(ordemServicoInputModel);
		return toModel(gestaoOrdemServico.criar(ordemServico));
	}
	
	@GetMapping
	public List<OrdemServicoModel> listar() {
		return toCollectionMode(ordemServicoRepository.findAll());	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long id) {
		Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(id);	
		
		if(ordemServico.isPresent()) {
			//OrdemServicoModel model = new OrdemServicoModel();
			//model.setId(ordemServico.get().getId());
			//model.setDescricao(ordemServico.get().getDescricao());
			// ....
			OrdemServicoModel ordemServicoModel = toModel(ordemServico.get());
			return ResponseEntity.ok(ordemServicoModel);
		}		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long id) {
		gestaoOrdemServico.finalizar(id);
	}
	
	private OrdemServicoModel toModel(OrdemServico ordemServico) {
		return  modelMapper.map(ordemServico, OrdemServicoModel.class);
	}
	
	private List<OrdemServicoModel> toCollectionMode(List<OrdemServico> ordensServico) {
		return ordensServico.stream()
				.map(ordemServico -> toModel(ordemServico))
				.collect(Collectors.toList());
	}
	
	private OrdemServico toEntity(OrdemServicoInputModel ordemServicoInputModel) {
		return modelMapper.map(ordemServicoInputModel, OrdemServico.class);
	}

}
