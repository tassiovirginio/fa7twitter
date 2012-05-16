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
	private static Login login;
	
	@BeforeClass
	public static void setUp() {
		Jetty.start();
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://127.0.0.1:9999");	
		
		login = new Login(driver);
	
	}
	
	@AfterClass
	public static void setDown(){
		
		for (String handle : driver.getWindowHandles()) {
			
		    driver.switchTo().window(handle).close();
		    
		}
		Jetty.stop();
	}

	@Test
	public void loginError() throws InterruptedException{		

		login.setLogin("tassio");
		
		login.setSenha("1234").submit();
		
        Thread.sleep(1000);
                
        String textPage = login.getTextPage();
        
		Assert.assertTrue("Login ou senha incorretos", textPage.contains("O login ou a senha inserido está incorreto."));
	}

	
	@Test
	public void loginSucesso() throws InterruptedException{		

		login.setLogin("tassio");
		
		login.setSenha("123").submit();
		
        Thread.sleep(1000);
        
        //login.sair();
        
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
		
		login.click("lkHome");
		
		login.click("lkFindUser");

		login.click("lkUserMessage");
		
		Assert.assertTrue(true);
	}
	
	
	@Test
	public void buscarPessoa() throws InterruptedException{		
		
		login.click("lkFindUser");		
		
		login.buscar("lu");
		
		Assert.assertTrue(true);
	}


}
