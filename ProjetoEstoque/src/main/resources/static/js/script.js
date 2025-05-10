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
                    $('#mensagemErro').html('');
                    window.location.href = "cadastrar.html";
                },
                error: function() {
                    $('#mensagemErro').html("Usuário/Senha incorretos.");
                }
            });
        });
    //VALIDAÇÃO CADASTRO
    $('#formularioCadastro').on('submit', function(event) {
        event.preventDefault();
  
        let codProduto = $('#codProduto').val().trim();
        let nomeProduto = $('#nomeProduto').val().trim();
        let quantidadeProduto = $('#quantidadeProduto').val().trim();
        let endProduto = $('#endProduto').val().trim();
        let descProduto = $('#descProduto').val().trim();
        let erro = '';
  
        if (codProduto === '' || nomeProduto === '' || quantidadeProduto === '' || endProduto === '' || descProduto === '') {
          erro += 'Preencha todos os campos para realizar o cadastro.';
        }
        if (erro !== '') {
          $('#mensagemErro').html(erro);
        } else {
          $('#mensagemErro').html('');
          alert('Produto cadastrado!');
          this.submit();
        }
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
    //VALIDAÇÃO LISTAR
    $('#formularioListar').on('submit', function(event) {
      event.preventDefault();

      let codProduto = $('#codProduto').val().trim();
      let erro = '';

      if (codProduto === '') {
        erro += 'Informe o código do produto que deseja buscar.';
      }
      if (erro !== '') {
        $('#mensagemErro').html(erro);
      } else {
        $('#mensagemErro').html('');
        this.submit();
      }
    });

});