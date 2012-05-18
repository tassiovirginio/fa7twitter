package br.com.fa7.twitter.selenium;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

import br.com.fa7.twitter.selenium.components.FuncionalTestContext;

public class TestBase {

	protected static WebDriver driver;
	
	@BeforeClass
	public static void doInitialize() {
		FuncionalTestContext.inititialize();
		driver = FuncionalTestContext.getDriver();
	}
	
	@AfterClass
	public static void doTerminate(){
		FuncionalTestContext.done();
	}
}
