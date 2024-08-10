# language: pt

Funcionalidade: Login de usuario

Feature: Login

 Cenario: Login com sucesso
    Dado que estou na página de login
    Quando eu preencho o nome de usuário e senha
    E clico no botão de login
    Então eu devo ver a mensagem de boas-vindas