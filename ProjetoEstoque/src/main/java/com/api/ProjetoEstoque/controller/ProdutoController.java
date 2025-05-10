package com.api.ProjetoEstoque.controller;

import com.api.ProjetoEstoque.data.ProdutoEntity;
import com.api.ProjetoEstoque.service.ProdutoService;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProdutoController {
    @Autowired
    ProdutoService produtoService;
    
    @GetMapping("/listar")
    public ResponseEntity<List> buscarTodosProdutos(){
        List<ProdutoEntity> produtos = produtoService.listarTodosProdutos();
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }
    @GetMapping("/buscar/{cod}")
    public ResponseEntity<ProdutoEntity> buscarProduto(@PathVariable Integer cod){
        Optional<ProdutoEntity> produto = produtoService.buscarProdutoPorCod(cod);
        return (ResponseEntity<ProdutoEntity>) produto
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Produto com código " + cod + " não encontrado."));
    }
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarProduto(@RequestBody ProdutoEntity produto){
        try {
            var novoProduto = produtoService.cadastrarProduto(produto);
            return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            // Verifica se é erro de chave duplicada
            if (e.getRootCause() instanceof SQLIntegrityConstraintViolationException) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Erro: Já existe um produto com este código.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro de integridade de dados.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao salvar o produto.");
        }
    }
    @PutMapping("/transferir")
    public ResponseEntity<?> transferirProduto(@RequestBody Map<String, String> payload) {
        String cod = payload.get("cod");
        String novoEndereco = payload.get("endereco");

    Optional<ProdutoEntity> produtoOpt = produtoService.buscarProdutoPorCod(Integer.valueOf(cod));

    if (produtoOpt.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Produto com código " + cod + " não encontrado.");
    }

    ProdutoEntity produto = produtoOpt.get();
    produto.setEndereco(novoEndereco);
    produtoService.transferirProduto(produto);

    return ResponseEntity.ok("Produto transferido com sucesso para o endereço: " + novoEndereco);
    }
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarProduto(@PathVariable Integer id) {
        boolean excluido = produtoService.deletarProduto(id);
        if(excluido){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
