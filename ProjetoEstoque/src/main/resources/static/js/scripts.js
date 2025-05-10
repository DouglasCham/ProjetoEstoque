$(document).ready( function() {
        
    $("#btn_menu").on("click", function(){
        $("#menuPrincipal").toggleClass("ocultar");
    });
    
    //VALIDAÇÃO ACESSO
    $('#formularioAcesso').on('submit', function(e) {
            e.preventDefault();
            $.ajax({
                type: "POST",
                url: "/api/login",
                contentType: "application/json",
                data: JSON.stringify({
                    usuario: $('#usuario').val(),
                    senha: $('#senha').val()
                }),
                success: function(response) {
                    $('#loginError').html('');
                    window.location.href = "/cadastrar";
                },
                error: function(err) {
                    $('#loginError').html("Usuário/Senha incorretos.");
                }
            });
        });
    //VALIDAÇÃO CADASTRO
    $('#formularioCadastro').on('submit', function(e) {
        e.preventDefault();
        
        $.ajax({
            type: "POST",
            url: "/api/cadastrar",
            contentType: "application/json",
            data: JSON.stringify({
                cod: $('#codProduto').val().trim(),
                nome: $('#nomeProduto').val().trim(),
                quantidade: $('#quantidadeProduto').val().trim(),
                endereco: $('#endProduto').val().trim(),
                descricao: $('#descProduto').val().trim()
            }),
            success: function(response){
                alert("Produto cadastrado com sucesso.");
            },
            error: function(err) {
                alert("Falha ao cadastrar o produto.");
                console.log(err);
            }
        });
      });
    //VALIDAÇÃO TRANSFERIR
    $('#formularioTransferir').on('submit', function(event) {
      event.preventDefault();

      let codProduto = $('#codProduto').val().trim();
      let quantidadeProduto = $('#quantidadeProduto').val().trim();
      let endProduto = $('#endProduto').val().trim();
      let erro = '';

      if (codProduto === '' || quantidadeProduto === '' || endProduto === '') {
        erro += 'Preencha todos os campos para realizar o cadastro.';
      }
      if (erro !== '') {
        $('#mensagemErro').html(erro);
      } else {
        $('#mensagemErro').html('');
        alert('Produto transferido!');
        this.submit();
      }
    });
    //BUSCAR PRODUTOS
    $('#btn_buscar').on('click', function(e) {
//      e.preventDefault();
        buscarProdutos();
    });
    function buscarProdutos(){
        $.get("/api/listar", function(data) {
                var tabela = $("#tabelaProdutos tbody");
                tabela.empty();

                data.forEach(function(produto) {
                    
                    var linha = `
                        <tr>
                            <td>${produto.cod}</td>
                            <td>${produto.nome}</td>
                            <td>${produto.quantidade}</td>
                            <td>${produto.endereco}</td>
                            <td>${produto.descricao}</td>
                            <td>
                                <a href="#" class="excluirProduto" data-id="${produto.id}">
                                    <img src="/imgs/excluir.png" alt="excluir" class="icone-pequeno">
                                </a>
                            </td
                        </tr>
                    `;
                    tabela.append(linha);
                });
            });
    }
    $(document).on('click', '.excluirProduto', function(e) {
    e.preventDefault();
    const id = $(this).data('id');

    if (confirm("Deseja realmente excluir este produto?")) {
        $.ajax({
            url: "/api/deletar/" + id,
            type: "DELETE",
            success: function() {
                alert("Produto excluído com sucesso!");
                location.reload(); // Recarrega a página para atualizar a tabela
            },
            error: function() {
                alert("Erro ao excluir o produto id: "+ id);
            }
        });
    }
});
});