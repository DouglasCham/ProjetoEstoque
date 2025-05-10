package com.api.ProjetoEstoque.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/cadastrar")
    public String exibirCadastroProduto(){
        return "cadastrar";
    }
    @GetMapping("/listar")
    public String exibirListaProdutos(){
        return "listar";
    }
    @GetMapping("/transferir")
    public String exibirTransferenciaProduto(){
        return "transferir";
    }
}
