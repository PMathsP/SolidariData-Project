document.addEventListener('DOMContentLoaded', function() {
    const formLogin = document.getElementById('form-login');
    const mensagemErro = document.getElementById('mensagem-erro');

    formLogin.addEventListener('submit', function(event) {
        event.preventDefault();

        const cpf = document.getElementById('cpf').value;
        const senha = document.getElementById('senha').value;

        const dados = {
            login: cpf,
            senha: senha
        };

        fetch('http://localhost:8080/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dados)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Usuário ou senha inválidos.');
            }
            return response.json();
        })
        .then(data => {
            // Se o login for um sucesso, guardamos o token no navegador
            localStorage.setItem('jwt_token', data.token);
            // E redirecionamos o usuário para a página do dashboard
            window.location.href = 'dashboard.html';
        })
        .catch(error => {
            mensagemErro.innerHTML = `<div class="alert alert-danger">${error.message}</div>`;
        });
    });
});