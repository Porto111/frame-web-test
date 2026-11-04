# language: pt
@login
Funcionalidade: Login e logout
  Como cliente da loja
  Quero entrar e sair da minha conta
  Para acessar os produtos com segurança

  Contexto:
    Dado que estou na página de login

  @smoke @positivo
  Cenário: Login com credenciais válidas
    Quando eu faço login com o usuário "standard_user" e a senha "secret_sauce"
    Então devo ver a página de produtos

  @negativo
  Esquema do Cenário: Login com dados inválidos
    Quando eu faço login com o usuário "<usuario>" e a senha "<senha>"
    Então devo ver a mensagem de erro "<mensagem>"

    Exemplos:
      | motivo              | usuario          | senha        | mensagem                             |
      | usuário inexistente | usuario_invalido | secret_sauce | Username and password do not match   |
      | senha incorreta     | standard_user    | senha_errada | Username and password do not match   |
      | usuário em branco   |                  | secret_sauce | Username is required                 |
      | senha em branco     | standard_user    |              | Password is required                 |
      | usuário bloqueado   | locked_out_user  | secret_sauce | this user has been locked out        |

  @positivo
  Cenário: Logout leva de volta à página de login
    Quando eu faço login com o usuário "standard_user" e a senha "secret_sauce"
    E eu faço logout
    Então devo estar na página de login
