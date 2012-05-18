package br.com.fa7.twitter.selenium.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.fa7.twitter.selenium.components.FuncionalTestContext;



public class LoginPage extends Page {

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public void loadPage(){
		driver.navigate().to("http://127.0.0.1:9999/login");
	}
	
	public WebElement setLogin(String login){
		return findElementAndSendKeys("login", login);
	}

	public WebElement setSenha(String senha){
		return findElementAndSendKeys("senha", senha);
	}
	
	public boolean isLogado() {
		driver.manage().timeouts().setScriptTimeout(2, TimeUnit.SECONDS);
		boolean encontroulinkSair = (findLinkText("Sair") != null);
		FuncionalTestContext.setDriverAsDefault();
		return encontroulinkSair;
	}
	
	public void logarComoTassio(){
		this.setLogin("tassio");
		this.setSenha("123").submit();
	}
	
}
