package br.com.fa7.twitter.selenium;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.ui.context.Theme;


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

//	@Ignore
	@Test
	public void loginSucesso() throws InterruptedException{		

		WebElement queryLogin = driver.findElement(By.name("login"));
		queryLogin.sendKeys("tassio");
		Thread.sleep(1000);
        WebElement querySenha = driver.findElement(By.name("senha"));
        querySenha.sendKeys("123");
        Thread.sleep(1000);
        querySenha.submit(); 
        Thread.sleep(1000);
		Assert.assertTrue(true);
	}
	
	
	@Test
	public void enviarMsg() throws InterruptedException{		

		WebElement queryMsg = driver.findElement(By.name("taMsg"));
		queryMsg.sendKeys("Teste");
		Thread.sleep(1000);
		queryMsg.submit();
		
		Assert.assertTrue(true);
		Thread.sleep(4000);
	}
	
//	@Test
	public void navegarNoMenu() throws InterruptedException{		

		WebElement queryHome = driver.findElement(By.name("Home"));
		queryHome.submit();
		
		Thread.sleep(3000);
		
		WebElement queryBuscar = driver.findElement(By.name("Buscar"));
		queryBuscar.submit();
		Thread.sleep(3000);
		Assert.assertTrue(true);
	}


}
