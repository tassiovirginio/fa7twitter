package br.com.fa7.twitter.selenium.components;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverRegister {
	
	private WebDriver registredInstance;
	
	public WebDriver getDriver(){
		if (this.registredInstance == null) {
			registerNew();
		}
		return this.registredInstance;
	}
	
	public void register(WebDriver Instance) {
		this.registredInstance = Instance;
	}

	public WebDriver registerNew() {
		WebDriver driver = new FirefoxDriver();
		driver.get("http://127.0.0.1:9999");
		register(driver);
		setDriverAsDefault();
		return driver;
	}
	
	public void setDriverAsDefault(){
		this.registredInstance
			.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
}