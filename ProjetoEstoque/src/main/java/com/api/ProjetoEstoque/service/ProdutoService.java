package com.api.ProjetoEstoque.service;

import com.api.ProjetoEstoque.data.ProdutoEntity;
import com.api.ProjetoEstoque.data.ProdutoRepository;
import com.api.ProjetoEstoque.exception.ResourceNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    @Autowired
    ProdutoRepository produtoRepository;
    public ProdutoEntity cadastrarProduto(ProdutoEntity novoProduto){
        novoProduto.setId(null);
        produtoRepository.save(novoProduto);
        return novoProduto;
    }
    public List<ProdutoEntity> listarTodosProdutos(){
        return produtoRepository.findAll();
    }
    public ProdutoEntity transferirProduto(Integer produtoID, ProdutoEntity produtoRequest){
        ProdutoEntity produtoSelecionado = getProdutoID(produtoID);
        produtoSelecionado.setQuantidade(produtoRequest.getQuantidade());
        produtoSelecionado.setEndereco(produtoRequest.getEndereco());
        return produtoSelecionado;
    }
    public ProdutoEntity getProdutoID(Integer produtoID){
        return produtoRepository.findById(produtoID).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado ID: " + produtoID));
    }
    public ProdutoEntity getProdutoByCod(Integer cod){
        return produtoRepository.findByCod(cod);
    }
    public void deletarProduto(Integer produtoID){
        ProdutoEntity produto = getProdutoID(produtoID);
        produtoRepository.deleteById(produto.getId());
    }
}
