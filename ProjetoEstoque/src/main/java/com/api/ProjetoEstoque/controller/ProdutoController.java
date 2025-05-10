package com.api.ProjetoEstoque.controller;

import com.api.ProjetoEstoque.data.ProdutoEntity;
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
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    ProdutoService produtoService;
    
    @GetMapping("/listar")
    public ResponseEntity<List> getAllFilmes(){
        List<ProdutoEntity> produtos = produtoService.listarTodosProdutos();
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }
    @GetMapping("/pesquisar/{cod}")
    public ResponseEntity<ProdutoEntity> getProdutoPorId(@PathVariable Integer cod){
        ProdutoEntity produto = produtoService.getProdutoByCod(cod);
        return new ResponseEntity<>(produto, HttpStatus.OK);
    }
    @PostMapping("/adicionar")
    public ResponseEntity<ProdutoEntity> addProduto(@Valid @RequestBody ProdutoEntity produto){
        var novoProduto = produtoService.cadastrarProduto(produto);
        return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
    }
    @PutMapping("/atualizar/{cod}")
    public ResponseEntity<ProdutoEntity> transferirProduto(@PathVariable Integer cod,@Valid @RequestBody ProdutoEntity produto){
        var produtoTransferido = produtoService.transferirProduto(cod, produto);
        return new ResponseEntity<>(produtoTransferido, HttpStatus.OK);
    }
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ProdutoEntity> deletarProduto(@PathVariable Integer id){
        produtoService.deletarProduto(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
