package br.com.fa7.twitter.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.fa7.twitter.selenium.pages.LoginPage;
import br.com.fa7.twitter.selenium.pages.PrincipalPage;
import br.com.fa7.twitter.selenium.pages.ProfilePage;

public class ApplicationTest extends FuncionalTestBase {

	public LoginPage loginPage = new LoginPage(driver);
	public PrincipalPage principalPage = new PrincipalPage(driver);
	public ProfilePage profilePage = new ProfilePage(driver);
	
	@Before
	public void login(){
		loginPage.loadPage();
		loginPage.logarComoTassio();
	}
	
	@After
	public void logoff(){
		principalPage.sair();
	}
	
	@Test
	public void navegarNoMenu() throws InterruptedException {
		principalPage.loadPage();
		principalPage.click("lkUserMessage");
		Assert.assertEquals("http://127.0.0.1:9999/user/tassio", getCurrentUrlSemParametros());
		principalPage.click("lkHome");
		Assert.assertEquals("http://127.0.0.1:9999/", getCurrentUrlSemParametros());
		principalPage.click("lkFindUser");
		Assert.assertEquals("http://127.0.0.1:9999/find", getCurrentUrlSemParametros());
	}
	
	@Test
	public void navegarPelosLinkDeUsuario() throws InterruptedException {
		principalPage.loadPage();
		principalPage.findLinkText("@tiago").click();
		Thread.sleep(500);
		Assert.assertEquals("http://127.0.0.1:9999/user/tiago/", getCurrentUrlSemParametros());
		profilePage.findLinkText("@tassio").click();
		Thread.sleep(500);
		Assert.assertEquals("http://127.0.0.1:9999/user/tassio", getCurrentUrlSemParametros());
		profilePage.findLinkText("@tiago").click();
		Thread.sleep(500);
		Assert.assertEquals("http://127.0.0.1:9999/user/tiago/", getCurrentUrlSemParametros());
	}
	
	private String getCurrentUrlSemParametros() {
		String currentUrl = driver.getCurrentUrl();
		return currentUrl.substring(0, currentUrl.indexOf('?'));
	}

}
