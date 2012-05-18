package br.com.fa7.twitter.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
	
	public void logarComoTassio(){
		this.setLogin("tassio");
		this.setSenha("123").submit();
	}
	
}
