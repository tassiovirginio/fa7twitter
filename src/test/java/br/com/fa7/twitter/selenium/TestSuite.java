package br.com.fa7.twitter.selenium;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.openqa.selenium.WebDriver;

import br.com.fa7.twitter.Jetty;
import br.com.fa7.twitter.selenium.components.DriverRegister;
import br.com.fa7.twitter.selenium.pages.LoginPage;

@RunWith(value=Suite.class)
@SuiteClasses(value={
		LoginTest.class,
		Teste.class,
  }
)
public class TestSuite { 
	
	private static WebDriver driver;	
	
	@BeforeClass
	public static void setUp() {
		Jetty.start();
		driver = DriverRegister.getDriver();	
		//logar();
	}
	
	private static void logar() {
		LoginPage login = new LoginPage(driver);
		login.loadPage();
		login.setLogin("tassio");
		login.setSenha("123").submit();
	}
	
	@AfterClass
	public static void tearDown(){
		driver.close();
		Jetty.stop();
	}
}
