package br.com.fa7.twitter.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



public class BuscarPage extends Page {

	public BuscarPage(WebDriver driver) {
		super(driver);
	}

	public void buscar(String value){
		
		WebElement element = findElementById("buscar");
		
		element.sendKeys(value);
		
		element.submit();
		
	}
	
}
