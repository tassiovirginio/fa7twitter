package br.com.fa7.twitter.selenium.components;

import org.openqa.selenium.WebDriver;

public abstract class TestBase {

	private WebDriver driver;

	public TestBase () {
		this.driver = DriverRegister.getDriver();
	}
	
	protected WebDriver getDriver(){
		return driver;
	}

}
