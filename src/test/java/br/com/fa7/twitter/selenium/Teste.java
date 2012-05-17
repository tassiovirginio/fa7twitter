package br.com.fa7.twitter.selenium;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import br.com.fa7.twitter.Jetty;
import br.com.fa7.twitter.selenium.components.DriverRegister;
import br.com.fa7.twitter.selenium.pages.BuscarPage;
import br.com.fa7.twitter.selenium.pages.LoginPage;
import br.com.fa7.twitter.selenium.pages.PrincipalPage;
import br.com.fa7.twitter.selenium.pages.ProfilePage;

public class Teste {

	private static WebDriver driver;
	private static LoginPage login;
	private static PrincipalPage principalPage;
	private static ProfilePage profilePage;
	private static BuscarPage buscarPage;

	@BeforeClass
	public static void setUp() {
		Jetty.start();
		driver = DriverRegister.getDriver();
		login = new LoginPage(driver);
		principalPage = new PrincipalPage(driver);
		profilePage = new ProfilePage(driver);
		buscarPage = new BuscarPage(driver);
		login.logarComoTassio();
	}
	
	@AfterClass
	public static void setDown(){
		for (String handle : driver.getWindowHandles()) {
		    driver.switchTo().window(handle).close();
		}
		Jetty.stop();
	}

	@Test
	public void loginEmpty() throws InterruptedException {
		login.sair();
		login.setSenha("1234").submit();
		Thread.sleep(1000);
		String textPage = login.getTextPage();
		Assert.assertTrue("Campo login vazio.",
		textPage.contains("Campo 'login' é obrigatório."));
	}

	@Test
	public void senhaEmpty() throws InterruptedException {
		login.sair();
		login.setLogin("tassio").submit();
		Thread.sleep(1000);
		String textPage = login.getTextPage();
		Assert.assertTrue("Campo senha vazio.",
				textPage.contains("Campo 'senha' é obrigatório."));
	}

	@Test
	public void loginError() throws InterruptedException {
		login.sair();
		login.setLogin("tassio");
		login.setSenha("1234").submit();
		Thread.sleep(500);
		String textPage = login.getTextPage();
		Assert.assertTrue("Login ou senha incorretos.", textPage
				.contains("O login ou a senha inserido está incorreto."));
	}

	@Test
	public void loginSucesso() throws InterruptedException {
		login.sair();
		login.loadPage();
		login.setLogin("tassio");
		login.setSenha("123").submit();
		Thread.sleep(500);
		Assert.assertEquals("http://127.0.0.1:9999/", getCurrentUrlSemParametros());
	}

	@Test
	public void enviarMsg() {
		principalPage.loadPage();
		Assert.assertEquals(3, principalPage.quantidadeDeMensagensEncontradas());
		principalPage.sendMessage("Teste");
		Assert.assertEquals(4, principalPage.quantidadeDeMensagensEncontradas());
		Assert.assertTrue(true);
	}

	@Test
	public void navegarNoMenu() throws InterruptedException {
		profilePage.loadPage("tassio");
		profilePage.click("lkUserMessage");
		Assert.assertEquals("http://127.0.0.1:9999/user/tassio", getCurrentUrlSemParametros());
		profilePage.click("lkHome");
		Assert.assertEquals("http://127.0.0.1:9999/", getCurrentUrlSemParametros());
		profilePage.click("lkFindUser");
		Assert.assertEquals("http://127.0.0.1:9999/find", getCurrentUrlSemParametros());
	}
	
	private String getCurrentUrlSemParametros() {
		String currentUrl = driver.getCurrentUrl();
		return currentUrl.substring(0, currentUrl.indexOf('?'));
	}

	@Test
	public void buscarUmUsuario() throws InterruptedException {
		buscarPage.loadPage();
		buscarPage.click("lkFindUser");
		buscarPage.buscar("lu");
		Assert.assertEquals(1, buscarPage.quantidadeDeUsuariosEncontrados());
	}
	
	@Test
	public void buscarDoisUsuario() throws InterruptedException {
		buscarPage.loadPage();
		buscarPage.click("lkFindUser");
		buscarPage.buscar("l");
		Assert.assertEquals(2, buscarPage.quantidadeDeUsuariosEncontrados());
	}

}
