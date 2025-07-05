document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('jwt_token');

    // GUARDA DA PÁGINA: Se não houver token, expulsa o usuário para a página de login
    if (!token) {
        window.location.href = 'login.html';
        return;
    }

    // Mapeamento dos corpos das tabelas
    const tabelasCorpo = {
        usuarios: document.getElementById('tabela-usuarios'),
        instituicoes: document.getElementById('tabela-instituicoes'),
        beneficiarios: document.getElementById('tabela-beneficiarios'),
        servicos: document.getElementById('tabela-servicos'),
        atendimentos: document.getElementById('tabela-atendimentos')
    };

    const apiEndpoints = {
        usuarios: 'http://localhost:8080/api/usuarios',
        instituicoes: 'http://localhost:8080/api/instituicoes',
        beneficiarios: 'http://localhost:8080/api/beneficiarios',
        servicos: 'http://localhost:8080/api/servicos',
        atendimentos: 'http://localhost:8080/api/atendimentos'
    };

    // FUNÇÃO genérica para carregar dados
    async function carregarDados(entidade) {
        try {
            const response = await fetch(apiEndpoints[entidade], {
                headers: { 'Authorization': `Bearer ${token}` }
            });
            if (!response.ok) throw new Error('Falha ao carregar ' + entidade);
            
            const data = await response.json();
            renderizarTabela(entidade, data);
        } catch (error) {
            console.error(`Erro ao carregar ${entidade}:`, error);
        }
    }

    // FUNÇÃO genérica para renderizar as linhas da tabela
    function renderizarTabela(entidade, data) {
        const corpoTabela = tabelasCorpo[entidade];
        corpoTabela.innerHTML = ''; // Limpa a tabela

        data.forEach(item => {
            let linhaHtml = '<tr>';
            switch (entidade) {
                case 'usuarios':
                    linhaHtml += `<td>${item.idUsuario}</td><td>${item.nomeUsuario}</td><td>${item.cpfUsuario}</td><td>${item.tipoUsuario === 1 ? 'Admin' : 'Operador'}</td>`;
                    break;
                case 'instituicoes':
                    linhaHtml += `<td>${item.idInstituicao}</td><td>${item.nomeInstituicao}</td><td>${item.cnpj}</td>`;
                    break;
                case 'beneficiarios':
                    linhaHtml += `<td>${item.idBeneficiario}</td><td>${item.nome}</td><td>${item.cpf}</td><td>${new Date(item.dataNascimento).toLocaleDateString()}</td>`;
                    break;
                case 'servicos':
                    linhaHtml += `<td>${item.idServico}</td><td>${item.descricaoServico}</td>`;
                    break;
                case 'atendimentos':
                    linhaHtml += `<td>${item.idAtendimento}</td><td>${new Date(item.dataAtendimento).toLocaleString()}</td><td>${item.nivelUrgencia}</td><td>${item.idInstituicao}</td><td>${item.idBeneficiario}</td>`;
                    break;
            }
            // Adiciona botões de ação
            const id = item.idUsuario || item.idInstituicao || item.idBeneficiario || item.idServico || item.idAtendimento;
            linhaHtml += `
                <td>
                    <button class="btn btn-sm btn-warning btn-editar" data-id="${id}" data-entidade="${entidade}">Editar</button>
                    <button class="btn btn-sm btn-danger btn-excluir" data-id="${id}" data-entidade="${entidade}">Excluir</button>
                </td>
            </tr>`;
            corpoTabela.innerHTML += linhaHtml;
        });
    }

    // FUNÇÃO genérica para deletar um item
    async function deletarItem(entidade, id) {
        if (confirm(`Tem certeza que deseja excluir o item de ID ${id} da tabela de ${entidade}?`)) {
            try {
                const response = await fetch(`${apiEndpoints[entidade]}/${id}`, {
                    method: 'DELETE',
                    headers: { 'Authorization': `Bearer ${token}` }
                });

                if (response.status === 204) {
                    alert('Item excluído com sucesso!');
                    carregarDados(entidade); // Recarrega a tabela específica
                } else {
                    throw new Error('Falha ao excluir.');
                }
            } catch (error) {
                console.error(`Erro ao deletar ${entidade}:`, error);
                alert(error.message);
            }
        }
    }

    // Event listener único para todas as tabelas
    document.querySelector('main').addEventListener('click', function(event) {
        const target = event.target;
        if (target.classList.contains('btn-excluir')) {
            const id = target.dataset.id;
            const entidade = target.dataset.entidade;
            deletarItem(entidade, id);
        }
        if (target.classList.contains('btn-editar')) {
            alert('A funcionalidade de "Editar" pode ser implementada aqui, abrindo um formulário em um pop-up (modal) e fazendo uma requisição PUT.');
        }
    });

    // LÓGICA DE LOGOUT
    document.getElementById('logout-btn').addEventListener('click', () => {
        localStorage.removeItem('jwt_token');
        window.location.href = 'login.html';
    });

    // Carrega todos os dados quando a página é aberta
    carregarDados('usuarios');
    carregarDados('instituicoes');
    carregarDados('beneficiarios');
    carregarDados('servicos');
    carregarDados('atendimentos');
});