$(document).ready( function() {
        
    $("#btn_menu").on("click", function(){
        $("#menuPrincipal").toggleClass("ocultar");
    });
    $.get("/api/check").fail(function(){
                window.location.href = "/";
    });
    $("#btn_logout").on("click", function() {
            $.get("/api/logout").done(function() {
                window.location.href = "/";
            });
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
                    window.location.href = "/cadastrar";
                },
                error: function(err) {
                    $('#loginError').html("Usuário/Senha incorretos.");
                }
            });
        });
    //CADASTRAR PRODUTO
    $('#formularioCadastro').submit(function(e) {
        e.preventDefault();
        
        const produto = {
            cod: $('#codProduto').val(),
            nome: $('#nomeProduto').val(),
            quantidade: $('#quantidadeProduto').val(),
            endereco: $('#endProduto').val(),
            descricao: $('#descProduto').val()
        };
        
        $.ajax({
            type: "POST",
            url: "/api/cadastrar",
            contentType: "application/json",
            data: JSON.stringify(produto),
            success: function(data){
                $('#mensagemCadastro').html('<p style="color:green;">Produto cadastrado com sucesso!</p>');
                $('#formularioCadastro')[0].reset();
            },
            error: function(xhr) {
                let mensagem = "Erro ao cadastrar produto.";
                if (xhr.status === 409 || xhr.status === 400) {
                    mensagem = xhr.responseText;
                }
                $('#mensagemCadastro').html('<p style="color:red">' + mensagem + '</p>');
            }
        });
      });
    //TRANSFERIR PRODUTO
    $('#formularioTransferir').submit(function (e) {
            e.preventDefault();

            const cod = $('#codProduto').val().trim();
            const endereco = $('#endProduto').val().trim();

            if (!cod || !endereco) {
                $('#mensagemTransferencia').html('<p style="color:red;">Preencha todos os campos.</p>');
                return;
            }

            $.ajax({
                url: '/api/transferir',
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify({ cod: cod, endereco: endereco }),
                success: function (resposta) {
                    $('#mensagemTransferencia').html('<p style="color:green;">' + resposta + '</p>');
                    $('#formTransferencia')[0].reset();
                },
                error: function (xhr) {
                    let msg = xhr.responseText || "Erro ao transferir produto.";
                    $('#mensagemTransferencia').html('<p style="color:red;">' + msg + '</p>');
                }
            });
        });
    //BUSCAR PRODUTOS
    $('#btn_buscar').click(function () {
            const cod = $('#codProduto').val().trim();

            if (!cod) {
                buscarProdutos();
                return;
            }

            $.ajax({
                url: '/api/buscar/' + cod,
                type: 'GET',
                success: function (produto) {
                    $('#resultadoBusca').html('');
                    $('#tabelaProdutos tbody').html(`
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
                    `);
                },
                error: function (xhr) {
                    let msg = xhr.responseText || "Erro ao buscar produto.";
                    $('#resultadoBusca').html('<p style="color:red;">' + msg + '</p>');
                }
            });
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