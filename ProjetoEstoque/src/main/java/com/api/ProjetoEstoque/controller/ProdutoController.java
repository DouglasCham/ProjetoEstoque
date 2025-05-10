package com.api.ProjetoEstoque.controller;

import com.api.ProjetoEstoque.data.ProdutoEntity;
import com.api.ProjetoEstoque.data.ProdutoRepository;
import com.api.ProjetoEstoque.service.ProdutoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/pesquisar/{cod}")
    public ResponseEntity<ProdutoEntity> buscarProduto(@PathVariable Integer cod){
        ProdutoEntity produto = produtoService.getProdutoByCod(cod);
        return new ResponseEntity<>(produto, HttpStatus.OK);
    }
    @PostMapping("/cadastrar")
    public ResponseEntity<ProdutoEntity> cadastrarProduto(@Valid @RequestBody ProdutoEntity produto){
        var novoProduto = produtoService.cadastrarProduto(produto);
        return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
    }
    @PutMapping("/transferir/{cod}")
    public ResponseEntity<ProdutoEntity> transferirProduto(@PathVariable Integer cod,@Valid @RequestBody ProdutoEntity produto){
        var produtoTransferido = produtoService.transferirProduto(cod, produto);
        return new ResponseEntity<>(produtoTransferido, HttpStatus.OK);
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
