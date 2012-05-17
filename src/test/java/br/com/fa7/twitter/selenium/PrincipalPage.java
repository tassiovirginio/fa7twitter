package br.com.fa7.twitter.selenium;

import org.openqa.selenium.WebDriver;

public class PrincipalPage extends Page {

	public PrincipalPage(WebDriver driver) {
		super(driver);
	}

	public void sendMessage(String message){
		findElementAndSendKeys("taMsg", message).submit();
	}

	@Override
	public void loadPage() {
		driver.navigate().to("http://127.0.0.1:9999/");
	}
}
