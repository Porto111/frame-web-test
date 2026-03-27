package Steps;

import config.Config;
import contexto.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageObjects.PaginaInicial;
import pageObjects.PaginaLogin;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step Definitions para cenários de Login.
 * Utiliza injeção de dependência via PicoContainer (TestContext).
 */
public class LoginSteps {

    private static final Logger logger = LoggerFactory.getLogger(LoginSteps.class);
    private final TestContext context;
    private final PaginaLogin paginaLogin;

    /**
     * Construtor com injeção de dependência do PicoContainer
     */
    public LoginSteps(TestContext context) {
        this.context = context;
        this.paginaLogin = context.getPaginaLogin();
    }

    @Given("que estou na página de login")
    public void que_estou_na_pagina_de_login() {
        logger.info("Acessando página de login");
        paginaLogin.navigateTo(Config.getBaseUrl());

        assertThat(paginaLogin.isPaginaLogin())
                .as("Deveria estar na página de login")
                .isTrue();
    }

    @When("eu preencho o nome de usuário e senha")
    public void eu_preencho_o_nome_de_usuario_e_senha() {
        logger.info("Preenchendo credenciais de login");
        paginaLogin.preencherUsuario(Config.getLoginUsername())
                   .preencherSenha(Config.getLoginPassword());
    }

    @When("eu preencho o usuário {string} e senha {string}")
    public void eu_preencho_o_usuario_e_senha(String username, String password) {
        logger.info("Preenchendo credenciais: usuário={}", username);
        paginaLogin.preencherUsuario(username)
                   .preencherSenha(password);
    }

    @When("clico no botão de login")
    public void clico_no_botao_de_login() {
        logger.info("Clicando no botão de login");
        PaginaInicial paginaInicial = paginaLogin.clicarBotaoLogin();
        context.setPaginaInicial(paginaInicial);
    }

    @Then("eu devo ver a mensagem de boas-vindas")
    public void eu_devo_ver_a_mensagem_de_boas_vindas() {
        logger.info("Verificando mensagem de boas-vindas");
        PaginaInicial paginaInicial = context.getPaginaInicial();

        assertThat(paginaInicial.isPaginaInicial())
                .as("Deveria estar na página inicial")
                .isTrue();

        String mensagem = paginaInicial.getMensagemBoasVindas();
        logger.info("Mensagem obtida: {}", mensagem);

        assertThat(mensagem)
                .as("A mensagem de boas-vindas deveria conter o nome do usuário")
                .contains(Config.getLoginUsername());
    }

    @Then("eu devo ver a mensagem {string}")
    public void eu_devo_ver_a_mensagem(String mensagemEsperada) {
        logger.info("Verificando mensagem: {}", mensagemEsperada);
        PaginaInicial paginaInicial = context.getPaginaInicial();

        assertThat(paginaInicial.getMensagemBoasVindas())
                .as("A mensagem deveria ser: " + mensagemEsperada)
                .isEqualTo(mensagemEsperada);
    }

    @Then("devo ver mensagem de erro {string}")
    public void devo_ver_mensagem_de_erro(String mensagemErro) {
        logger.info("Verificando mensagem de erro: {}", mensagemErro);

        assertThat(paginaLogin.isMensagemErroVisivel())
                .as("Deveria aparecer mensagem de erro")
                .isTrue();

        assertThat(paginaLogin.getMensagemErro())
                .as("A mensagem de erro deveria ser: " + mensagemErro)
                .contains(mensagemErro);
    }
}