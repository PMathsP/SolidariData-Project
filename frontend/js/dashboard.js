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

                if (response.status === 204 || response.status === 200) {
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

    // FUNÇÃO para editar um item
    async function editarItem(entidade, id) {
        try {
            // Busca os dados atuais do item
            const response = await fetch(`${apiEndpoints[entidade]}/${id}`, {
                headers: { 'Authorization': `Bearer ${token}` }
            });
            
            if (!response.ok) throw new Error('Falha ao buscar dados do item');
            
            const item = await response.json();
            
            // Gera formulário baseado na entidade
            let formHTML = '';
            switch (entidade) {
                case 'usuarios':
                    formHTML = `
                        <div class="mb-3">
                            <label class="form-label">Nome:</label>
                            <input type="text" class="form-control" id="edit-nome" value="${item.nomeUsuario || ''}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">CPF:</label>
                            <input type="text" class="form-control" id="edit-cpf" value="${item.cpfUsuario || ''}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Tipo:</label>
                            <select class="form-control" id="edit-tipo">
                                <option value="1" ${item.tipoUsuario === 1 ? 'selected' : ''}>Admin</option>
                                <option value="2" ${item.tipoUsuario === 2 ? 'selected' : ''}>Operador</option>
                            </select>
                        </div>`;
                    break;
                case 'beneficiarios':
                    formHTML = `
                        <div class="mb-3">
                            <label class="form-label">Nome:</label>
                            <input type="text" class="form-control" id="edit-nome" value="${item.nome || ''}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">CPF:</label>
                            <input type="text" class="form-control" id="edit-cpf" value="${item.cpf || ''}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Data de Nascimento:</label>
                            <input type="date" class="form-control" id="edit-data" value="${item.dataNascimento || ''}">
                        </div>`;
                    break;
                case 'instituicoes':
                    formHTML = `
                        <div class="mb-3">
                            <label class="form-label">Nome da Instituição:</label>
                            <input type="text" class="form-control" id="edit-nome" value="${item.nomeInstituicao || ''}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">CNPJ:</label>
                            <input type="text" class="form-control" id="edit-cnpj" value="${item.cnpj || ''}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Razão Social:</label>
                            <input type="text" class="form-control" id="edit-razao" value="${item.razaoSocial || ''}">
                        </div>`;
                    break;
            }
            
            // Cria modal para edição
            const modalHTML = `
                <div class="modal fade" id="editModal" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Editar ${entidade}</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                            </div>
                            <div class="modal-body">
                                ${formHTML}
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                                <button type="button" class="btn btn-primary" id="salvar-edicao">Salvar</button>
                            </div>
                        </div>
                    </div>
                </div>`;
            
            // Remove modal anterior se existir
            const modalExistente = document.getElementById('editModal');
            if (modalExistente) modalExistente.remove();
            
            // Adiciona modal ao DOM
            document.body.insertAdjacentHTML('beforeend', modalHTML);
            
            // Abre modal
            const modal = new bootstrap.Modal(document.getElementById('editModal'));
            modal.show();
            
            // Configura botão salvar
            document.getElementById('salvar-edicao').onclick = () => salvarEdicao(entidade, id, modal);
            
        } catch (error) {
            console.error(`Erro ao editar ${entidade}:`, error);
            alert('Erro ao carregar dados para edição');
        }
    }

    // FUNÇÃO para salvar edição
    async function salvarEdicao(entidade, id, modal) {
        try {
            let dadosAtualizados = {};
            
            switch (entidade) {
                case 'usuarios':
                    dadosAtualizados = {
                        nomeUsuario: document.getElementById('edit-nome').value,
                        cpfUsuario: document.getElementById('edit-cpf').value,
                        tipoUsuario: parseInt(document.getElementById('edit-tipo').value)
                    };
                    break;
                case 'beneficiarios':
                    dadosAtualizados = {
                        nome: document.getElementById('edit-nome').value,
                        cpf: document.getElementById('edit-cpf').value,
                        dataNascimento: document.getElementById('edit-data').value
                    };
                    break;
                case 'instituicoes':
                    dadosAtualizados = {
                        nomeInstituicao: document.getElementById('edit-nome').value,
                        cnpj: document.getElementById('edit-cnpj').value,
                        razaoSocial: document.getElementById('edit-razao').value
                    };
                    break;
            }
            
            const response = await fetch(`${apiEndpoints[entidade]}/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(dadosAtualizados)
            });
            
            if (response.ok) {
                alert('Item atualizado com sucesso!');
                modal.hide();
                carregarDados(entidade); // Recarrega a tabela
            } else {
                throw new Error('Falha ao atualizar item');
            }
            
        } catch (error) {
            console.error(`Erro ao salvar edição:`, error);
            alert('Erro ao salvar alterações');
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
            const id = target.dataset.id;
            const entidade = target.dataset.entidade;
            editarItem(entidade, id);
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