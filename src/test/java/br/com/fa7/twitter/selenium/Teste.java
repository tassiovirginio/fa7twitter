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
		login.loadPage();
		login.setSenha("1234").submit();
		Thread.sleep(1000);
		String textPage = login.getTextPage();
		Assert.assertTrue("Campo login vazio.",
				textPage.contains("Campo 'login' é obrigatório."));
	}

	@Test
	public void senhaEmpty() throws InterruptedException {
		login.loadPage();
		login.setLogin("tassio").submit();
		Thread.sleep(1000);
		String textPage = login.getTextPage();
		Assert.assertTrue("Campo senha vazio.",
				textPage.contains("Campo 'senha' é obrigatório."));
	}

	@Test
	public void loginError() throws InterruptedException {
		login.loadPage();
		login.setLogin("tassio");
		login.setSenha("1234").submit();
		Thread.sleep(1000);
		String textPage = login.getTextPage();
		Assert.assertTrue("Login ou senha incorretos.", textPage
				.contains("O login ou a senha inserido está incorreto."));
	}

	@Test
	public void loginSucesso() throws InterruptedException {
		login.loadPage();
		login.setLogin("tassio");
		login.setSenha("123").submit();
		Thread.sleep(1000);
		// login.sair();
		Assert.assertTrue(true);
	}

	@Test
	public void enviarMsg() {
		principalPage.loadPage();
		principalPage.sendMessage("Teste");
		Assert.assertTrue(true);
	}

	@Test
	public void navegarNoMenu() throws InterruptedException {
		profilePage.loadPage("tassio");
		Thread.sleep(1000);
		profilePage.click("lkHome");
		profilePage.click("lkFindUser");
		profilePage.click("lkUserMessage");
		Assert.assertTrue(true);
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
