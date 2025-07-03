document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('form-cadastro');
    const tipoBeneficiarioRadio = document.getElementById('tipoBeneficiario');
    const tipoInstituicaoRadio = document.getElementById('tipoInstituicao');
    const camposBeneficiario = document.getElementById('campos-beneficiario');
    const camposInstituicao = document.getElementById('campos-instituicao');
    const mensagemRetorno = document.getElementById('mensagem-retorno');

    // Função para mostrar/esconder campos
    function toggleCampos() {
        if (tipoBeneficiarioRadio.checked) {
            camposBeneficiario.classList.remove('d-none');
            camposBeneficiario.classList.add('d-block');
            camposInstituicao.classList.add('d-none');
            camposInstituicao.classList.remove('d-block');
        } else if (tipoInstituicaoRadio.checked) {
            camposInstituicao.classList.remove('d-none');
            camposInstituicao.classList.add('d-block');
            camposBeneficiario.classList.add('d-none');
            camposBeneficiario.classList.remove('d-block');
        }
    }

    // Adiciona o evento de 'change' para os botões de rádio
    tipoBeneficiarioRadio.addEventListener('change', toggleCampos);
    tipoInstituicaoRadio.addEventListener('change', toggleCampos);

    // Adiciona o evento de 'submit' para o formulário
    form.addEventListener('submit', function (event) {
        event.preventDefault(); // Impede o envio padrão do formulário

        let url = '';
        let corpoRequisicao = {};
        
        const nome = document.getElementById('nome').value;

        // Monta a requisição baseada no tipo selecionado
        if (tipoBeneficiarioRadio.checked) {
            url = 'http://localhost:8080/api/beneficiarios';
            corpoRequisicao = {
                nome: nome,
                cpf: document.getElementById('cpf').value,
                dataNascimento: document.getElementById('dataNascimento').value
            };
        } else if (tipoInstituicaoRadio.checked) {
            url = 'http://localhost:8080/api/instituicoes';
            corpoRequisicao = {
                nomeInstituicao: nome,
                razaoSocial: document.getElementById('razaoSocial').value,
                cnpj: document.getElementById('cnpj').value,
                idUsuarioResponsavel: 2 // ID Fixo apenas para teste - idealmente viria de um usuário logado
            };
        } else {
            alert('Por favor, selecione um tipo de cadastro.');
            return;
        }

        // Faz a requisição POST para a API
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(corpoRequisicao)
        })
        .then(response => {
            if (response.status === 201) { // 201 Created
                return response.json();
            }
            throw new Error('Falha no cadastro. Status: ' + response.status);
        })
        .then(data => {
            mensagemRetorno.innerHTML = `<div class="alert alert-success">Cadastro realizado com sucesso! ID gerado: ${data.idBeneficiario || data.idInstituicao}</div>`;
            form.reset(); // Limpa o formulário
            toggleCampos(); // Esconde os campos novamente
        })
        .catch(error => {
            mensagemRetorno.innerHTML = `<div class="alert alert-danger">Erro: ${error.message}</div>`;
        });
    });
});