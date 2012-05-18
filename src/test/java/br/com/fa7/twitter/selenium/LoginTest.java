package br.com.fa7.twitter.selenium;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;

import br.com.fa7.twitter.selenium.components.FuncionalTestContext;
import br.com.fa7.twitter.selenium.pages.LoginPage;
import br.com.fa7.twitter.selenium.pages.PrincipalPage;

public class LoginTest extends TestBase{
	
	private static LoginPage login;
	private static PrincipalPage principalPage;
	
	@BeforeClass
	public static void initialize() {
		login = new LoginPage(driver);
		principalPage = new PrincipalPage(driver);
		tryLogoff();
	}
	
	@AfterClass
	public static void tearDonw() {
		FuncionalTestContext.done();
	}
	
	@Before
	public void setUp() {
		login.loadPage();
	}
	
	public static void tryLogoff() {
		try {
			principalPage.sair();
		} catch (NoSuchElementException expected) { }
	}
	
	@Test
	public void loginVazio() throws InterruptedException {
		login.setSenha("1234").submit();
		Thread.sleep(1000);
		String textPage = login.getTextPage();
		Assert.assertTrue("Campo login vazio.",
		textPage.contains("Campo 'login' é obrigatório."));
	}

	@Test
	public void senhaVazio() throws InterruptedException {
		login.setLogin("tassio").submit();
		Thread.sleep(1000);
		String textPage = login.getTextPage();
		Assert.assertTrue("Campo senha vazio.",
				textPage.contains("Campo 'senha' é obrigatório."));
	}

	@Test
	public void loginESenhaIncorretos() throws InterruptedException {
		login.setLogin("tassio");
		login.setSenha("1234").submit();
		Thread.sleep(500);
		String textPage = login.getTextPage();
		Assert.assertTrue("Login ou senha incorretos.", textPage
				.contains("O login ou a senha inserido está incorreto."));
	}

	@Test
	public void loginSucesso() throws InterruptedException {
		login.setLogin("tassio");
		login.setSenha("123").submit();
		Thread.sleep(500);
		Assert.assertEquals("http://127.0.0.1:9999/", getCurrentUrlSemParametros());
		tryLogoff();
	}
	
	private String getCurrentUrlSemParametros() {
		String currentUrl = driver.getCurrentUrl();
		return currentUrl.substring(0, currentUrl.indexOf('?'));
	}
}
