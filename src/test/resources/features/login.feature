#language: pt
#encoding: utf-8


Funcionalidade: Login de usuario

Feature: Login

 Cenario: Login com sucesso
    Given que estou na página de login
    When eu preencho o nome de usuário e senha
    And clico no botão de login
    Then eu devo ver a mensagem de boas-vindas