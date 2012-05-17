package br.com.fa7.twitter.selenium.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.fa7.twitter.selenium.components.DriverRegister;

public class BuscarPage extends Page {

	public BuscarPage(WebDriver driver) {
		super(driver);
	}

	public void buscar(String value) {
		WebElement element = findElementById("buscar");
		element.sendKeys(value);
		element.submit();
	}
	
	public int quantidadeDeUsuariosEncontrados() {
		final String XPATH_EXPRESSION = 
				"//tbody//tr[1]//td[1]//span[";
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		WebElement tbResultados = findElementById("resultado");
		int resultCount = 0;
		while (true) {
			try {
				String xpath_toline = XPATH_EXPRESSION + (resultCount + 1) + "]";
				tbResultados.findElement(By.xpath(xpath_toline));
				resultCount++;
			} catch (NoSuchElementException expected) { 
				break;
			}
		}

		DriverRegister.setDriverAsDefault();
		return resultCount;
	}
	
	@Override
	public void loadPage() {
		driver.navigate().to("http://127.0.0.1:9999/find/");
	}
	
}
