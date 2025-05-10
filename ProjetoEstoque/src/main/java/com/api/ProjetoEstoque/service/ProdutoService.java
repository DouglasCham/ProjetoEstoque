package com.api.ProjetoEstoque.service;

import com.api.ProjetoEstoque.data.ProdutoEntity;
import com.api.ProjetoEstoque.data.ProdutoRepository;
import com.api.ProjetoEstoque.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    @Autowired
    ProdutoRepository produtoRepository;
    public ProdutoEntity cadastrarProduto(ProdutoEntity novoProduto){
        novoProduto.setId(null);
        return produtoRepository.save(novoProduto);
    }
    public List<ProdutoEntity> listarTodosProdutos(){
        return produtoRepository.findAll();
    }
    public ProdutoEntity transferirProduto(ProdutoEntity produtoAtualizado){
        return produtoRepository.save(produtoAtualizado);
    }
    public ProdutoEntity getProdutoID(Integer produtoID){
        return produtoRepository.findById(produtoID).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado ID: " + produtoID));
    }
    public Optional<ProdutoEntity> buscarProdutoPorCod(Integer cod){
        return produtoRepository.findByCod(cod);
    }
    public boolean deletarProduto(Integer id){
        if(produtoRepository.existsById(id)){
            produtoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
