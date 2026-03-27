#language: pt
#encoding: utf-8

@login @regressao
Funcionalidade: Login de usuário
  Como um usuário do sistema
  Quero realizar login
  Para acessar as funcionalidades disponíveis

  Contexto:
    Dado que estou na página de login

  @smoke @positivo
  Cenário: Login com sucesso
    Quando eu preencho o nome de usuário e senha
    E clico no botão de login
    Então eu devo ver a mensagem de boas-vindas

  @negativo
  Esquema do Cenário: Login com credenciais inválidas
    Quando eu preencho o usuário "<usuario>" e senha "<senha>"
    E clico no botão de login
    Então devo ver mensagem de erro "<mensagem>"

    Exemplos:
      | usuario          | senha           | mensagem                      |
      | usuario_invalido | senha123        | Usuário ou senha inválidos    |
      | meuUsuario       | senha_invalida  | Usuário ou senha inválidos    |
      |                  | senha123        | Campo usuário é obrigatório   |
      | meuUsuario       |                 | Campo senha é obrigatório     |