package Steps;

import config.Config;
import factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageObjects.PaginaInicial;
import pageObjects.PaginaLogin;
import utils.PropertyReader;

import static org.junit.Assert.assertEquals;

public class LoginSteps {
    private WebDriver driver;
    private PaginaLogin paginaLogin;
    private PaginaInicial paginaInicial;
    private PropertyReader propertyReader;

    public LoginSteps(){
        driver = DriverFactory.getDriver();
        paginaLogin = new PaginaLogin(driver);
        propertyReader = new PropertyReader();

    }

    @Given("que estou na página de login")
    public void que_estou_na_pagina_de_login() {
        // Verifica se está na página correta
        assertEquals("Login", driver.getTitle());
    }

    @When("eu preencho o nome de usuário e senha")
    public void eu_preencho_o_nome_de_usuario_e_senha() {
        paginaLogin.enterUsername(Config.getLoginUsername());
        paginaLogin.enterPassword(Config.getLoginPassword());
    }

    @When("clico no botão de login")
    public void clico_no_botao_de_login() {
        paginaLogin.clickLogin();
    }

    @Then("eu devo ver a mensagem de boas-vindas")
    public void eu_devo_ver_a_mensagem_de_boas_vindas() {
        paginaInicial = new PaginaInicial(driver);
        assertEquals("Bem-vindo, " + Config.getLoginUsername(), paginaInicial.getWelcomeMessage());
    }


}
