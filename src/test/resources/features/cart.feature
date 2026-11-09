# language: pt
@carrinho
Funcionalidade: Carrinho de compras
  Como cliente da loja
  Quero adicionar e remover produtos do carrinho
  Para escolher o que vou comprar

  Contexto:
    Dado que estou logado como "standard_user" com a senha "secret_sauce"

  @smoke
  Cenário: Adicionar um produto ao carrinho
    Quando eu adiciono o produto "Sauce Labs Backpack" ao carrinho
    Então o carrinho deve exibir 1 item

  Cenário: Adicionar dois produtos ao carrinho
    Quando eu adiciono o produto "Sauce Labs Backpack" ao carrinho
    E eu adiciono o produto "Sauce Labs Bike Light" ao carrinho
    Então o carrinho deve exibir 2 itens

  Cenário: Remover o único produto do carrinho
    Quando eu adiciono o produto "Sauce Labs Backpack" ao carrinho
    E eu removo o produto "Sauce Labs Backpack" do carrinho
    Então o carrinho deve ficar vazio
