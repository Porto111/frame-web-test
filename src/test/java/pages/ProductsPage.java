package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Página de produtos (a primeira tela após o login), com o carrinho e o menu lateral. */
public class ProductsPage extends BasePage {

    private final By titulo = By.className("title");
    private final By iconeCarrinho = By.className("shopping_cart_badge");
    private final By botaoMenu = By.id("react-burger-menu-btn");
    private final By linkLogout = By.id("logout_sidebar_link");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isVisible(titulo);
    }

    public String getTitle() {
        return getText(titulo);
    }

    public void addToCart(String produto) {
        click(By.id("add-to-cart-" + idDoProduto(produto)));
    }

    public void removeFromCart(String produto) {
        click(By.id("remove-" + idDoProduto(produto)));
    }

    public int getCartItemCount() {
        return Integer.parseInt(getText(iconeCarrinho));
    }

    public boolean isCartEmpty() {
        return isGone(iconeCarrinho);
    }

    public void logout() {
        click(botaoMenu);
        click(linkLogout);
    }

    /** O site monta o id do botão com o nome do produto: "Sauce Labs Backpack" -> "sauce-labs-backpack". */
    private String idDoProduto(String nomeDoProduto) {
        return nomeDoProduto.toLowerCase().replace(" ", "-");
    }
}
