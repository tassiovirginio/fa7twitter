package br.com.fa7.twitter.selenium;

import org.openqa.selenium.WebDriver;

public class ProfilePage extends Page {

	public ProfilePage(WebDriver driver) {
		super(driver);
	}

	public void sendMessage(String message){
		findElementAndSendKeys("taMsg", message).submit();
	}

	@Override
	public void loadPage() {
		driver.navigate().to("http://127.0.0.1:9999/user/");
	}
	
	public void loadPage(String user) {
		driver.navigate().to("http://127.0.0.1:9999/user/"+ user);
	}
}
