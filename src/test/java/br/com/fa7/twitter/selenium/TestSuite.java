package br.com.fa7.twitter.selenium;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.com.fa7.twitter.selenium.components.DriverRegister;

@RunWith(value=Suite.class)
@SuiteClasses(value={
		Teste.class,
  }
)
public class TestSuite { 
	
	private static WebDriver driver;	
	
	@BeforeClass
	public static void setUp() {
		driver = DriverRegister.registerNew();
		logar();
	}
	
	private static void logar() {
		driver.navigate().to("http://127.0.0.1:9999/login");
		driver.findElement(By.id("login")).sendKeys("tiago");
		driver.findElement(By.id("senha")).sendKeys("123");
		driver.findElement(By.id("senha")).submit();
	}
	
	@AfterClass
	public static void tearDown(){
//		for (String handle : driver.getWindowHandles()) {
//		    driver.switchTo().window(handle).close();
//		}
		driver.close();
	}
}
