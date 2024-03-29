package br.com.isidrocorp.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.isidrocorp.ecommerce.dao.ProdutoDAO;
import br.com.isidrocorp.ecommerce.model.Departamento;
import br.com.isidrocorp.ecommerce.model.Produto;
import br.com.isidrocorp.ecommerce.util.Mensagem;

@RestController
public class ProdutoController {
	
	@Autowired
	private ProdutoDAO dao;
	
	@GetMapping("/produtos")
	public List<Produto> recuperarTodos(){
		// qual o objetivo aqui? Recuperar todos os produtos do banco
		return (List<Produto>)dao.recuperarTudaoComUmaUnicaQuery();
	}

	
	// e se eu quiser um produto sendo buscado pelo seu id??
	@GetMapping("/produtos/{id}")
	public ResponseEntity<?> recuperarPeloId(@PathVariable Integer id) {
		Produto res = dao.findById(id).orElse(null);
		if (res != null) {
			return ResponseEntity.ok(res);
		}
		return ResponseEntity.status(404).body(new Mensagem(12345,"Produto nao encontrado"));
	}
	
	@GetMapping("/produtos/busca")
	public ResponseEntity<List<Produto>> buscarPorPalavraChave(@RequestParam(name="chave") String chave){
		List<Produto> res = dao.findByNomeContainingOrDescricaoContaining(chave, chave);
		if (res.size() != 0) {
			return ResponseEntity.ok(res);
		}
		return ResponseEntity.noContent().build();		
	}
	
	@GetMapping("/produtos/departamento/{id}")
	public List<Produto> recuperarProdutosPorDepartamento(@PathVariable Integer id){
		Departamento d = new Departamento();
		d.setNumero(id);
		return dao.findByDepto(d);
	}
	

}
