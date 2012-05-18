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
