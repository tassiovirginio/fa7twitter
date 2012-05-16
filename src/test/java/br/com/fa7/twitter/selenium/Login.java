package br.com.fa7.twitter.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



public class Login extends Page {

	public Login(WebDriver driver) {
		super(driver);
	}
	
	public WebElement setLogin(String login){
		
		return findElementAndSendKeys("login", login);
		
	}

	public WebElement setSenha(String senha){
		
		return findElementAndSendKeys("senha", senha);
		
	}
	
	public void buscar(String value){
		
		WebElement element = findElementById("buscar");
		
		element.sendKeys(value);
		
		element.submit();
		
	}
	
	public void sair(){
		
		findLinkText("Sair").click();
		//findElementById("lkLogoff").click();
		
	}

}
