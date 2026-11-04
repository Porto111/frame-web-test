package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import driver.DriverFactory;
import pages.ProductsPage;

import static org.assertj.core.api.Assertions.assertThat;

/** Passos dos cenários do carrinho de compras. */
public class CartSteps {

    private final ProductsPage productsPage = new ProductsPage(DriverFactory.getDriver());

    @When("eu adiciono o produto {string} ao carrinho")
    public void euAdicionoOProdutoAoCarrinho(String produto) {
        productsPage.addToCart(produto);
    }

    @When("eu removo o produto {string} do carrinho")
    public void euRemovoOProdutoDoCarrinho(String produto) {
        productsPage.removeFromCart(produto);
    }

    @Then("o carrinho deve exibir {int} item(ns)")
    public void oCarrinhoDeveExibirItens(int quantidadeEsperada) {
        assertThat(productsPage.getCartItemCount())
                .as("Quantidade de itens no carrinho")
                .isEqualTo(quantidadeEsperada);
    }

    @Then("o carrinho deve ficar vazio")
    public void oCarrinhoDeveFicarVazio() {
        assertThat(productsPage.isCartEmpty())
                .as("O carrinho deveria estar vazio")
                .isTrue();
    }
}
