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

import br.com.fa7.twitter.Jetty;

public class Teste {

	private static WebDriver driver;	
	
	@BeforeClass
	public static void setUp() {
		Jetty.start();
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://127.0.0.1:9999");		
	
	}
	
	@AfterClass
	public static void setDown(){
		for (String handle : driver.getWindowHandles()) {
		    driver.switchTo().window(handle).close();
		}
		Jetty.stop();
	}

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
	}
	
	@Test
	public void navegarNoMenu() throws InterruptedException{		

		driver.get("http://127.0.0.1:9999/user/tassio");
		Thread.sleep(1000);
		
		driver.findElement(By.id("lkHome")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.id("lkFindUser")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.id("lkUserMessage")).click();
		Thread.sleep(1000);
		
		Assert.assertTrue(true);
	}
	
	
	@Test
	public void buscarPessoa() throws InterruptedException{		
		
		driver.findElement(By.id("lkFindUser")).click();
		Thread.sleep(1000);
		
		WebElement queryBuscar = driver.findElement(By.id("buscar"));
        queryBuscar.sendKeys("lu");
        queryBuscar.submit();
		
		Assert.assertTrue(true);
	}


}
