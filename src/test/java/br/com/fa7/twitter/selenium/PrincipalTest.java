package br.com.fa7.twitter.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.fa7.twitter.selenium.pages.LoginPage;
import br.com.fa7.twitter.selenium.pages.PrincipalPage;

public class PrincipalTest extends FuncionalTestBase {
	
	@Before
	public void login(){
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loadPage();
		loginPage.logarComoTassio();
	}
	
	@After
	public void logoff(){
		PrincipalPage principalPage = new PrincipalPage(driver);
		principalPage.sair();
	}
	
	@Test
	public void enviarMsg() {
		PrincipalPage principalPage = 
				new PrincipalPage(driver);
		principalPage.loadPage();
		Assert.assertEquals(3, principalPage.quantidadeDeMensagensEncontradas());
		principalPage.sendMessage("Teste");
		Assert.assertEquals(4, principalPage.quantidadeDeMensagensEncontradas());
		Assert.assertTrue(true);
	}
	
}
