document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('jwt_token');

    // 1. GUARDA DA PÁGINA: Se não houver token, expulsa o usuário para a página de login
    if (!token) {
        window.location.href = 'login.html';
        return;
    }

    const tabelaBeneficiarios = document.getElementById('tabela-beneficiarios');

    // 2. FUNÇÃO PARA CARREGAR OS DADOS
    function carregarBeneficiarios() {
        fetch('http://localhost:8080/api/beneficiarios', {
            method: 'GET',
            headers: {
                // Enviamos o token no cabeçalho Authorization para provar que estamos logados
                'Authorization': `Bearer ${token}`
            }
        })
        .then(response => response.json())
        .then(data => {
            tabelaBeneficiarios.innerHTML = ''; // Limpa a tabela antes de preencher
            data.forEach(beneficiario => {
                const linha = `
                    <tr>
                        <td>${beneficiario.idBeneficiario}</td>
                        <td>${beneficiario.nome}</td>
                        <td>${beneficiario.cpf}</td>
                        <td>${new Date(beneficiario.dataNascimento).toLocaleDateString()}</td>
                        <td>
                            <button class="btn btn-sm btn-warning btn-editar" data-id="${beneficiario.idBeneficiario}">Editar</button>
                            <button class="btn btn-sm btn-danger btn-excluir" data-id="${beneficiario.idBeneficiario}">Excluir</button>
                        </td>
                    </tr>
                `;
                tabelaBeneficiarios.innerHTML += linha;
            });
        })
        .catch(error => console.error('Erro ao buscar beneficiários:', error));
    }

    // 3. LÓGICA DE DELETAR
    tabelaBeneficiarios.addEventListener('click', function(event) {
        if (event.target.classList.contains('btn-excluir')) {
            const id = event.target.dataset.id;
            if (confirm(`Tem certeza que deseja excluir o beneficiário de ID ${id}?`)) {
                fetch(`http://localhost:8080/api/beneficiarios/${id}`, {
                    method: 'DELETE',
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                })
                .then(response => {
                    if (response.status === 204) {
                        alert('Beneficiário excluído com sucesso!');
                        carregarBeneficiarios(); // Recarrega a tabela
                    } else {
                        alert('Falha ao excluir beneficiário.');
                    }
                });
            }
        }
        // A lógica para o botão "Editar" seria adicionada aqui
    });

    // 4. LÓGICA DE LOGOUT
    document.getElementById('logout-btn').addEventListener('click', () => {
        localStorage.removeItem('jwt_token'); // Remove o token
        window.location.href = 'login.html'; // Volta para a tela de login
    });

    // Carrega os dados assim que a página é aberta
    carregarBeneficiarios();
});