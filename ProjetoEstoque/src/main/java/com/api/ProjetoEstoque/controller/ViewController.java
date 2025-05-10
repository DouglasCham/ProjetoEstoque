package com.api.ProjetoEstoque.controller;

import com.api.ProjetoEstoque.data.ProdutoEntity;
import com.api.ProjetoEstoque.data.UsuarioEntity;
import com.api.ProjetoEstoque.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewController {
    @Autowired
    private ProdutoService produtoService;
    
    @GetMapping("/")
    public String index(){
//        UsuarioEntity usuario = new UsuarioEntity();
//        model.addAttribute("usuario", usuario);
        return "index";
    }
    @GetMapping("/cadastrar")
    public String exibirCadastroProduto(Model model){
        ProdutoEntity produto = new ProdutoEntity();
        model.addAttribute("produto", produto);
        return "cadastrar";
    }
    @GetMapping("/listar")
    public String exibirListaProdutos(Model model){
        model.addAttribute("produtos", produtoService.listarTodosProdutos());
        return "listar";
    }
    @GetMapping("/transferir")
    public String exibirTransferenciaProduto(@PathVariable(value = "cod")Integer cod, Model model){
        ProdutoEntity produto = produtoService.getProdutoByCod(cod);
        model.addAttribute("produto", produto);
        return "transferir";
    }
}
