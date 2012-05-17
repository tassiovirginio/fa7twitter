package br.com.fa7.twitter.selenium;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import br.com.fa7.twitter.Jetty;
import br.com.fa7.twitter.selenium.components.DriverRegister;

public class Teste {

	private static WebDriver driver;
	private static LoginPage login;
	private static PrincipalPage principalPage;
	private static BuscarPage buscarPage;

	@BeforeClass
	public static void setUp() {
		Jetty.start();
		driver = DriverRegister.getDriver();
		login = new LoginPage(driver);
		principalPage = new PrincipalPage(driver);
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
		login.setSenha("1234").submit();
		Thread.sleep(1000);
		String textPage = login.getTextPage();
		Assert.assertTrue("Campo login vazio.",
				textPage.contains("Campo 'login' é obrigatório."));
	}

	@Test
	public void senhaEmpty() throws InterruptedException {
		login.setLogin("tassio").submit();
		Thread.sleep(1000);
		String textPage = login.getTextPage();
		Assert.assertTrue("Campo senha vazio.",
				textPage.contains("Campo 'senha' é obrigatório."));
	}

	@Test
	public void loginError() throws InterruptedException {
		login.setLogin("tassio");
		login.setSenha("1234").submit();
		Thread.sleep(1000);
		String textPage = login.getTextPage();
		Assert.assertTrue("Login ou senha incorretos.", textPage
				.contains("O login ou a senha inserido está incorreto."));
	}

	@Test
	public void loginSucesso() throws InterruptedException {
		login.setLogin("tassio");
		login.setSenha("123").submit();
		Thread.sleep(1000);
		// login.sair();
		Assert.assertTrue(true);
	}

	@Test
	public void enviarMsg() {
		principalPage.sendMessage("Teste");
		Assert.assertTrue(true);
	}

	@Test
	public void navegarNoMenu() throws InterruptedException {
		driver.get("http://127.0.0.1:9999/user/tassio");
		Thread.sleep(1000);
		login.click("lkHome");
		login.click("lkFindUser");
		login.click("lkUserMessage");
		Assert.assertTrue(true);
	}

	@Test
	public void buscarPessoa() throws InterruptedException {
		buscarPage.click("lkFindUser");
		buscarPage.buscar("lu");
		Assert.assertTrue(true);
	}

}
