package br.com.fa7.twitter.selenium;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Teste {

	private static WebDriver driver;	
	private static Login pageLogin;
	private static String janelaPrincipal;
	
	@BeforeClass
	public static void setUp() {
		
		driver = new FirefoxDriver();		
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.get("http://127.0.0.1:9999");		
		
		janelaPrincipal = driver.getWindowHandle();
		
		pageLogin = new Login(driver);
		
	}
	
	@AfterClass
	public static void setDown(){
		
		for (String handle : driver.getWindowHandles()) {
			
		    driver.switchTo().window(handle).close();
		    
		}

	}

	@Ignore
	public void loginSucesso() throws InterruptedException{		

		// PASSO 1		
		
		Assert.assertTrue(true);
	}


}
