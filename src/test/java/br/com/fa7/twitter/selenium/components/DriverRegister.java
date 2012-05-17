package br.com.fa7.twitter.selenium.components;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverRegister {
	
	private static DriverRegister instance;
	
	private WebDriver registredInstance;
	
	public static DriverRegister getInstance(){
		if (instance == null) {
			build();
		}
		return instance;
	}
	
	private synchronized static void build() {
		if (instance == null) {
			instance = new DriverRegister();
		}
	}
	
	public static WebDriver getDriver(){
		if (getInstance().registredInstance == null) {
			registerNew();
		}
		return getInstance().registredInstance;
	}
	
	public static void register(WebDriver Instance) {
		getInstance().registredInstance = Instance;
	}

	public static WebDriver registerNew() {
		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://127.0.0.1:9999");
		register(driver);
		return driver;
	}
}