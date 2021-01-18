package com.ricbap.osworks.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ricbap.osworks.api.exceptionhandler.Problema.Campo;
import com.ricbap.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.ricbap.osworks.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	private ResponseEntity<Object> entidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setTitulo(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	
	@ExceptionHandler(NegocioException.class)
	private ResponseEntity<Object> handleNegocioException(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setTitulo(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Campo> campos = new ArrayList<Problema.Campo>();
		
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			// String nome = error.getObjectName(); // Nome do objeto
			String nome = ((FieldError) error).getField(); //Nome do campo
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
					
			campos.add(new Problema.Campo(nome, mensagem));
		}
		
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setTitulo("Um ou mais campos estão inválidos, faça o preenchimento corrreto e tente novamente");
		problema.setDataHora(OffsetDateTime.now());
		problema.setCampos(campos);
		
		return super.handleExceptionInternal(ex, problema, headers, status, request);
	}

}