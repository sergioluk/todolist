package br.com.danieleleao.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/primeiraRota")
//http://localhost:8080/
public class MinhaPrimeiraController {
	
	/**
	 * GET buscar uma informação
	 * POST adicionar um dado
	 * PUT alterar um dado
	 * DELETE remover um dado
	 * PATCH alterar somente uma parte da informacao/dado
	 */
	
	@GetMapping("/segundoMetodo")
	public String primeiraMensagem() {
		return "<h1>segundo</h1>";
	}
	@GetMapping("/primeiroMetodo")
	public String primeiraMensagem2() {
		return "<h1>primeiro</h1>";
	}
	@GetMapping
	public String primeiraMensagem3() {
		return "<h1>defalt</h1>";
	}
	
	@PostMapping
	public String primeiraMensagemasdsa() {
		return "Poste";
	}
	
}
